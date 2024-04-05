package org.example.paymentservice.service;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.example.paymentservice.model.Payment;
import org.example.paymentservice.model.WebRequest;
import org.example.paymentservice.model.WebResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Service class for managing payment operations.
 */
@Service
public class PaymentServiceImpl implements PaymentService {
    private final MongoTemplate mongoTemplate;
    private static final Logger log = LoggerFactory.getLogger(PaymentServiceImpl.class);
    private static final String BOOKING_ID = "bookingId";


    /**
     * Constructor for PaymentService.
     *
     * @param mongoTemplate The MongoDB template used for interacting with the database.
     */
    public PaymentServiceImpl(MongoTemplate mongoTemplate) {
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
                            .setCurrency("ron")
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
     * Find a payment by its custom ID and booking ID.
     *
     * @param id        The custom ID of the payment.
     * @param bookingId The booking ID associated with the payment.
     * @return The Payment object if found, otherwise null.
     */
    public Payment findByCustomIdAndBookingId(String id, String bookingId) {
        // Use the findOne method to search MongoDB by ID and bookingId
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id).and(BOOKING_ID).is(bookingId));
        return mongoTemplate.findOne(query, Payment.class);
    }

    /**
     * Find a payment by its ID.
     *
     * @param id The ID of the payment.
     * @return The Payment object if found, otherwise null.
     */
    public Payment findById(String id) {
        return mongoTemplate.findById(id, Payment.class);
    }

    /**
     * Save a payment to the database.
     *
     * @param payment The payment object to be saved.
     */
    public void savePayment(Payment payment) {
        mongoTemplate.save(payment);
    }

    /**
     * Update the status of a payment.
     *
     * @param paymentId The ID of the payment.
     * @param newStatus The new status of the payment.
     */
    @Transactional
    public void updatePaymentStatus(String paymentId, String newStatus) {
        Payment payment = findById(paymentId);
        if (payment != null) {
            payment.setStatus(newStatus); // Update the payment status
            mongoTemplate.save(payment); // Save the changes to the database
        } else {
            throw new RuntimeException("Payment with ID " + paymentId + " not found.");
        }
    }


}
