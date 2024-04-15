package org.example.paymentservice.service.stripe;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.example.paymentservice.model.Payment;
import org.example.paymentservice.model.stripe.WebRequest;
import org.example.paymentservice.model.stripe.WebResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Service class for managing payment operations.
 */
@Service
public class StripeServiceImpl implements StripeService {
    private final ReactiveMongoTemplate mongoTemplate;
    private static final Logger log = LoggerFactory.getLogger(StripeServiceImpl.class);
    private static final String BOOKING_ID = "bookingId";


    /**
     * Constructor for PaymentService.
     *
     * @param mongoTemplate The MongoDB template used for interacting with the database.
     */
    public StripeServiceImpl(ReactiveMongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * Process a payment request.
     *
     * @param request The payment request object containing payment details.
     * @return A Mono containing the response to the payment request.
     */
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
            log.info("Payment:" + paymentId + " for booking id: " + bookingId + " initial status: " + paymentStatus);
            return Mono.just(response);
        } catch (StripeException e) {
            e.printStackTrace();
            WebResponse response = new WebResponse("error", "");
            response.setBookingId(request.getBookingId());
            return Mono.just(response);
        }
    }


    /**
     * Find a payment by its ID.
     *
     * @param id The ID of the payment.
     * @return The Payment object if found, otherwise null.
     */
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
