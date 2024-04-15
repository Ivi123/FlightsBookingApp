package org.example.paymentservice.service.stripe;

import avro.PaymentRequest;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.example.paymentservice.consumer.dlt.DltConsumerService;
import org.example.paymentservice.mapper.PaymentMapper;
import org.example.paymentservice.model.Payment;
import org.example.paymentservice.model.stripe.WebRequest;
import org.example.paymentservice.model.stripe.WebResponse;
import org.example.paymentservice.producer.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;


@Service
public class StripeServiceImpl implements StripeService {
    private final ReactiveMongoTemplate mongoTemplate;
    private static final Logger log = LoggerFactory.getLogger(StripeServiceImpl.class);
    private static final String BOOKING_ID = "bookingId";
    private final DltConsumerService dltConsumerService;
    private final KafkaProducer kafkaProducer;

    public StripeServiceImpl(ReactiveMongoTemplate mongoTemplate, DltConsumerService dltConsumerService, KafkaProducer kafkaProducer) {
        this.mongoTemplate = mongoTemplate;
        this.dltConsumerService = dltConsumerService;
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public Mono<WebResponse> processPaymentRequest(WebRequest request) {
        try {
            PaymentIntentCreateParams params =
                    PaymentIntentCreateParams.builder()
                            .setAmount(request.getAmount() * 100L)
                            .putMetadata(BOOKING_ID, request.getBookingId())
                            .putMetadata("paymentId", request.getPaymentId())
                            .setCurrency("eur")
                            .setAutomaticPaymentMethods(
                                    PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                            .setEnabled(true)
                                            .build()
                            )
                            .build();

            PaymentIntent intent = PaymentIntent.create(params);
            Map<String, String> metadata = intent.getMetadata();
            String bookingId = metadata.get(BOOKING_ID);
            String paymentId = metadata.get("paymentId");

            // Get the payment status
            String paymentStatus = intent.getStatus();
            WebResponse response = new WebResponse(intent.getId(), intent.getClientSecret());
            response.setBookingId(bookingId);
            response.setPaymentId(paymentId);
            response.setStatus(paymentStatus); // Set the payment status in the response
            // Verifică dacă a expirat plata
            return findById(paymentId)
                    .flatMap(payment -> {
                        LocalDateTime expirationTime = payment.getExpirationTime();
                        LocalDateTime currentDateTime = LocalDateTime.now(ZoneId.systemDefault());

                        if (currentDateTime.isAfter(expirationTime)) {
                            log.error("Payment has expired: {}", paymentId);
                            WebResponse errorResponse = new WebResponse("error", "Payment Payment expiration time has passed. Payment cannot be processed.");
                            payment.setStatus("failed");
                            PaymentRequest paymentRequest = PaymentMapper.paymentToPaymentRequest(payment);
                            //send to dlt
                            dltConsumerService.sendPaymentToDLT(paymentRequest);
                            //send to Payment-response-topic
                            kafkaProducer.sendMessage(payment.getBookingId(), paymentRequest);

                            return Mono.just(errorResponse);
                        }

                        return Mono.just(response);
                    });
        } catch (StripeException e) {
            e.printStackTrace();
            WebResponse response = new WebResponse("error", "");
            response.setBookingId(request.getBookingId());
            return Mono.just(response);
        }
    }



    public Mono<Payment> findById(String id) {
        return mongoTemplate.findById(id, Payment.class);
    }


    public Mono<Payment> savePayment(Payment payment) {
        return mongoTemplate.save(payment)
                .onErrorMap(e -> new RuntimeException("Failed to save payment: " + e.getMessage(), e));
    }

    public Mono<Void> updatePaymentStatus(String paymentId, String newStatus) {
        return findById(paymentId)
                .flatMap(payment -> {
                    if (payment != null) {
                        payment.setStatus(newStatus);
                        return mongoTemplate.save(payment).then();
                    } else {
                        return Mono.error(new RuntimeException("Payment with ID " + paymentId + " not found."));
                    }
                });
    }


}
