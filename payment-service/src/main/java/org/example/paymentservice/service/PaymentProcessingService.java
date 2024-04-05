package org.example.paymentservice.service;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentIntentCollection;
import org.example.dto.PaymentRequest;
import org.example.paymentservice.mapper.PaymentMapper;
import org.example.paymentservice.model.Payment;
import org.example.paymentservice.producer.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Service class responsible for processing payments and displaying payment details.
 */
@Service
public class PaymentProcessingService {
    private static final Logger log = LoggerFactory.getLogger(PaymentProcessingService.class);
    private final PaymentServiceImpl paymentService;
    private final KafkaProducer kafkaProducer;

    /**
     * Constructor for PaymentProcessingService.
     *
     * @param paymentService An instance of PaymentServiceImpl for handling payment operations.
     * @param kafkaProducer  An instance of KafkaProducer for sending payment details.
     */
    public PaymentProcessingService(PaymentServiceImpl paymentService, KafkaProducer kafkaProducer) {
        this.paymentService = paymentService;
        this.kafkaProducer = kafkaProducer;
    }

    /**
     * Process payments and display payment details.
     */
    public void processAndDisplayPayment() {
        // Configure pagination to retrieve all payments
        Map<String, Object> params = new HashMap<>();
        params.put("limit", 1); // Maximum number of payments per page

        try {
            // Query payments using the Stripe API
            PaymentIntentCollection payments = PaymentIntent.list(params);

            for (PaymentIntent intent : payments.getData()) {
                Map<String, String> metadata = intent.getMetadata();
                String bookingId = metadata.get("bookingId");
                String paymentId = metadata.get("paymentId");
                log.info("*** Payment : {}", paymentId);
                log.info("*** for booking: {} ", bookingId);
                log.info("*** status: {}", intent.getStatus());

                // Update status in the database
                if (!intent.getStatus().equalsIgnoreCase("succeeded")) {
                    paymentService.updatePaymentStatus(paymentId, "failed");
                } else {
                    paymentService.updatePaymentStatus(paymentId, "succeeded");
                }

                // Retrieve payment details
                Payment p = paymentService.findById(paymentId);
                PaymentRequest paymentRequest = PaymentMapper.paymentToPaymentRequest(p);

                // Send payment details via Kafka
                kafkaProducer.sendMessage(bookingId, paymentRequest);
                break;
            }
        } catch (StripeException e) {
            e.printStackTrace();
        }
    }
}
