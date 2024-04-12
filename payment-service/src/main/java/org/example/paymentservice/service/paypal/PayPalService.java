package org.example.paymentservice.service.paypal;

import avro.PaymentRequest;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import org.example.paymentservice.mapper.stripe.PaymentMapper;
import org.example.paymentservice.model.paypal.CompletedOrder;
import org.example.paymentservice.model.paypal.PaymentOrder;
import org.example.paymentservice.producer.KafkaProducer;
import org.example.paymentservice.service.stripe.PaymentServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PayPalService {
    private final static Logger log = LoggerFactory.getLogger(PayPalService.class);

    private final PayPalHttpClient payPalHttpClient;
   private final PaymentServiceImpl paymentService;
   private final KafkaProducer kafkaProducer;

    @Value("${paypal.returnUrl}")
    private String returnUrl;

    @Value("${paypal.cancelUrl}")
    private String cancelUrl;

    public PayPalService(PayPalHttpClient payPalHttpClient, PaymentServiceImpl paymentService, KafkaProducer kafkaProducer) {
        this.payPalHttpClient = payPalHttpClient;
        this.paymentService = paymentService;
        this.kafkaProducer = kafkaProducer;
    }

    // Method to create a payment order
    public Mono<PaymentOrder> createPayment(Double totalAmount, String paymentId, String bookingId, String email) {
        return Mono.create(fluxSink -> {
            // Creating an order request
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
            // Set the expiration time for the order


            OrdersCreateRequest ordersCreateRequest = new OrdersCreateRequest().requestBody(orderRequest);

            try {
                // Executing the order creation request
                HttpResponse<Order> orderHttpResponse = payPalHttpClient.execute(ordersCreateRequest);
                Order order = orderHttpResponse.result();
                // Retrieving the redirect URL for the payment approval
                String redirectUrl = order.links().stream()
                        .filter(link -> "approve".equals(link.rel()))
                        .findFirst()
                        .orElseThrow(NoSuchElementException::new)
                        .href();

                // Creating a payment order object with success status and redirect URL
                PaymentOrder paymentOrder = new PaymentOrder("success", order.id(), redirectUrl);
                fluxSink.success(paymentOrder);
            } catch (IOException e) {
                // Handling error if order creation fails
                log.error(e.getMessage());
                PaymentOrder paymentOrder = new PaymentOrder();
                paymentOrder.setStatus("error");
                fluxSink.success(paymentOrder);
            }
        });
    }
    public Mono<CompletedOrder> completePayment(String token) {
        return Mono.create(fluxSink -> {
            OrdersCaptureRequest ordersCaptureRequest = new OrdersCaptureRequest(token);
            Order order = new Order();
            try {
                HttpResponse<Order> httpResponse = payPalHttpClient.execute(ordersCaptureRequest);
                order = httpResponse.result();
                getOrderDetails(order.id());
                log.info("*** Order expired at:"+ order.expirationTime());

                if (httpResponse.result().status() != null) {
                    CompletedOrder completedOrder = new CompletedOrder("success", token);
                    fluxSink.success(completedOrder);
                }
            } catch (IOException e) {
                log.error(e.getMessage());
                CompletedOrder completedOrder = new CompletedOrder("error");
                fluxSink.success(completedOrder);
            }
        });
    }
    public void getOrderDetails(String orderId){
        OrdersGetRequest ordersGetRequest = new OrdersGetRequest(orderId);
        try {
            HttpResponse<Order> httpResponse = payPalHttpClient.execute(ordersGetRequest);
            Order order = httpResponse.result();
            //update payment in bd
            String paymentId = order.purchaseUnits().get(0).items().get(0).name();
            String bookingId = order.purchaseUnits().get(0).items().get(0).description();
            // Update status in the database
            Mono<Void> updateStatusMono;
            if (!order.status().equalsIgnoreCase("COMPLETED")) {
                updateStatusMono = paymentService.updatePaymentStatus(paymentId, "failed");
            } else {
                updateStatusMono = paymentService.updatePaymentStatus(paymentId, "succeeded");
            }

            updateStatusMono
                    .then(paymentService.findById(paymentId))
                    .flatMap(payment -> {
                        PaymentRequest paymentRequest = PaymentMapper.paymentToPaymentRequest(payment);
                        return Mono.fromRunnable(() -> kafkaProducer.sendMessage(bookingId, paymentRequest))
                                .thenReturn(payment); // Return the payment after sending the message
                    })
                    .subscribe(

                    );


            log.info("*** Payment id: {}",paymentId);
            log.info("*** BookingId:"+order.purchaseUnits().get(0).items().get(0).description());
            log.info("*** Amount:"+order.purchaseUnits().get(0).amountWithBreakdown().value());
            log.info("****"+ order.status());



        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
