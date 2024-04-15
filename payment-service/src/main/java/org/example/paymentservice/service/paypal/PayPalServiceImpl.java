package org.example.paymentservice.service.paypal;

import avro.PaymentRequest;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import org.example.paymentservice.consumer.dlt.DltConsumerService;
import org.example.paymentservice.mapper.PaymentMapper;
import org.example.paymentservice.model.Payment;
import org.example.paymentservice.model.paypal.CompletedOrder;
import org.example.paymentservice.model.paypal.PaymentOrder;
import org.example.paymentservice.producer.KafkaProducer;
import org.example.paymentservice.service.stripe.StripeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PayPalServiceImpl implements PayPalService {
    private static final Logger log = LoggerFactory.getLogger(PayPalServiceImpl.class);

    private final PayPalHttpClient payPalHttpClient;
    private final StripeServiceImpl paymentService;
    private final KafkaProducer kafkaProducer;
    private final DltConsumerService dltConsumerService;

    @Value("${paypal.returnUrl}")
    private String returnUrl;

    @Value("${paypal.cancelUrl}")
    private String cancelUrl;

    public PayPalServiceImpl(PayPalHttpClient payPalHttpClient, StripeServiceImpl paymentService, KafkaProducer kafkaProducer, DltConsumerService dltConsumerService) {
        this.payPalHttpClient = payPalHttpClient;
        this.paymentService = paymentService;
        this.kafkaProducer = kafkaProducer;
        this.dltConsumerService = dltConsumerService;
    }

    // Method to create a payment order
    @Override
    public Mono<PaymentOrder> createPayment(Double totalAmount, String paymentId, String bookingId) {


        return paymentService.findById(paymentId)
                .flatMap(payment -> {
                    LocalDateTime expirationTime = payment.getExpirationTime();
                    LocalDateTime currentDateTime = LocalDateTime.now(ZoneId.systemDefault());

                    if (currentDateTime.isAfter(expirationTime)) {
                        log.error("Payment expiration time has passed. Payment cannot be processed.");
                        return Mono.just(new PaymentOrder("error", "", ""));
                    } else {


                        // Create an OrderRequest object with payment details
                        OrderRequest orderRequest = new OrderRequest();
                        orderRequest.checkoutPaymentIntent("CAPTURE");

                        // Creating amount breakdown for the payment
                        AmountWithBreakdown amountWithBreakdown = new AmountWithBreakdown().currencyCode("EUR")
                                .value(totalAmount.toString());

                        // Creating purchase unit request for the payment
                        PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest();
                        purchaseUnitRequest.amountWithBreakdown(amountWithBreakdown);

                        // Creating money object for the payment
                        Money money = new Money().currencyCode(amountWithBreakdown.currencyCode())
                                .value(amountWithBreakdown.value());

                        // Creating amount breakdown object for the payment
                        AmountBreakdown amountBreakdown = new AmountBreakdown().itemTotal(money);
                        amountWithBreakdown.amountBreakdown(amountBreakdown);

                        // Creating an item object for the payment
                        Item item = new Item().category("DIGITAL_GOODS").quantity("1").name(paymentId)
                                .description(bookingId)
                                .unitAmount(money);

                        // Adding item to the purchase unit request
                        purchaseUnitRequest.items(List.of(item));
                        // Adding the purchase unit request to the order request
                        orderRequest.purchaseUnits(List.of(purchaseUnitRequest));

                        // Creating application context for the payment
                        ApplicationContext applicationContext = new ApplicationContext()
                                .returnUrl(returnUrl)
                                .cancelUrl(cancelUrl)
                                .userAction("PAY_NOW");

                        // Adding application context to the order request
                        orderRequest.applicationContext(applicationContext);

                        // Create a request to create the order
                        OrdersCreateRequest ordersCreateRequest = new OrdersCreateRequest().requestBody(orderRequest);

                        // Create a Mono with the order request
                        return Mono.just(ordersCreateRequest)
                                .map(request -> {
                                    try {
                                        // Execute the order creation request
                                        HttpResponse<Order> orderHttpResponse = payPalHttpClient.execute(request);
                                        Order order = orderHttpResponse.result();

                                        // Get the redirect URL for payment approval
                                        String redirectUrl = order.links().stream()
                                                .filter(link -> "approve".equals(link.rel()))
                                                .findFirst()
                                                .orElseThrow(NoSuchElementException::new)
                                                .href();

                                        // Create a PaymentOrder object with success status and redirect URL
                                        return new PaymentOrder("success", order.id(), redirectUrl);
                                    } catch (IOException e) {
                                        // Handle error if order creation fails
                                        log.error(e.getMessage());
                                        // Return a PaymentOrder object with error status
                                        return new PaymentOrder("error", "", "");
                                    }
                                });
                    }
                });
    }



        @Override
        public Mono<CompletedOrder> completePayment (String token){
            OrdersCaptureRequest ordersCaptureRequest = new OrdersCaptureRequest(token);

            return Mono.fromCallable(() -> {
                try {
                    HttpResponse<Order> httpResponse = payPalHttpClient.execute(ordersCaptureRequest);
                    Order order = httpResponse.result();
                    return getOrderDetailsAndUpdatePayment(order.id())
                            .flatMap(payment -> {
                                if (payment == null) {
                                    log.error("Payment not found for order ID: {}", order.id());
                                    return Mono.just(new CompletedOrder("error"));
                                }
                                LocalDateTime expirationTime = payment.getExpirationTime();
                                LocalDateTime currentDateTime = LocalDateTime.now(ZoneId.systemDefault());
                                if (currentDateTime.isAfter(expirationTime)) {
                                    log.error("Payment has expired: {}", order.id());
                                    payment.setStatus("canceled");
                                    PaymentRequest paymentRequest = PaymentMapper.paymentToPaymentRequest(payment);
                                    dltConsumerService.sendPaymentToDLT(paymentRequest);
                                    kafkaProducer.sendMessage(payment.getBookingId(), paymentRequest);
                                    return Mono.just(new CompletedOrder("error", token));
                                } else {
                                    PaymentRequest paymentRequest = PaymentMapper.paymentToPaymentRequest(payment);
                                    kafkaProducer.sendMessage(payment.getBookingId(), paymentRequest);
                                    return Mono.just(new CompletedOrder("success", token));

                                }
                            });
                } catch (IOException e) {
                    log.error("Error capturing order: {}", e.getMessage());
                    return Mono.just(new CompletedOrder("error"));
                }
            }).flatMap(mono -> mono);
        }


        public Mono<Payment> getOrderDetailsAndUpdatePayment (String orderId){
            OrdersGetRequest ordersGetRequest = new OrdersGetRequest(orderId);
            try {
                HttpResponse<Order> httpResponse = payPalHttpClient.execute(ordersGetRequest);
                Order order = httpResponse.result();
                String paymentId = order.purchaseUnits().get(0).items().get(0).name();
                String bookingId = order.purchaseUnits().get(0).items().get(0).description();
                String paymentStatus = order.status();
                Mono<Void> updateStatusMono;
                if (!paymentStatus.equalsIgnoreCase("COMPLETED")) {
                    updateStatusMono = paymentService.updatePaymentStatus(paymentId, "failed");
                } else {
                    updateStatusMono = paymentService.updatePaymentStatus(paymentId, "succeeded");
                }
                return updateStatusMono
                        .then(paymentService.findById(paymentId))
                        .flatMap(payment -> {
                            return Mono.just(payment);
                        })
                        .doOnNext(payment -> {
                            log.info("*** Payment id: {}", paymentId);
                            log.info("*** BookingId: {}", bookingId);
                            log.info("*** Amount: {}", order.purchaseUnits().get(0).amountWithBreakdown().value());
                            log.info("**** {}", paymentStatus);
                        });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
