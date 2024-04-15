package org.example.paymentservice.service.stripe;

import avro.PaymentRequest;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentIntentCollection;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.paymentservice.consumer.dlt.DltConsumerService;
import org.example.paymentservice.mapper.PaymentMapper;
import org.example.paymentservice.producer.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * Service class responsible for processing payments and displaying payment details.
 */
@Service
public class StripePaymentProcessingService {
    private static final Logger log = LoggerFactory.getLogger(StripePaymentProcessingService.class);
    private final StripeServiceImpl paymentService;
    private final KafkaProducer kafkaProducer;
    private final DltConsumerService dltConsumerService;

    public StripePaymentProcessingService(StripeServiceImpl paymentService, KafkaProducer kafkaProducer, DltConsumerService dltConsumerService) {
        this.paymentService = paymentService;
        this.kafkaProducer = kafkaProducer;
        this.dltConsumerService = dltConsumerService;
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
                Mono<Void> updateStatusMono;
                if (!intent.getStatus().equalsIgnoreCase("succeeded")) {
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
                                payment -> {
                                    // Handle successful processing
                                    if(payment.getStatus().equalsIgnoreCase("failed")){
                                        //send to dlt
                                        PaymentRequest paymentRequest = PaymentMapper.paymentToPaymentRequest(payment);
                                        dltConsumerService.sendPaymentToDLT(paymentRequest);


                                    }
                                },
                                error -> {
                                    // Handle errors
                                }
                        );
            }
        } catch (StripeException e) {
            e.printStackTrace();
        }
    }

}
