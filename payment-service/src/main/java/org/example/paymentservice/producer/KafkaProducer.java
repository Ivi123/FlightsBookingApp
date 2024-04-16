package org.example.paymentservice.producer;


import avro.PaymentRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaProducer {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaProducer.class);
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, Object> kafkaTemplate) {

        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String key, PaymentRequest paymentRequest) {
        CompletableFuture.supplyAsync(() -> {
            try {
                // Simulate a 1-second delay
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                // Throw an exception in case of thread interruption
                throw new RuntimeException(e);
            }
            LOG.info("Sending message to payment-response-topic:: {}, {}", key, paymentRequest);
            // Send the message to the Kafka topic using KafkaTemplate
            kafkaTemplate.send("payment-response-topic", key, paymentRequest);
            return null;
        }).whenComplete((r, e) -> {
            // When the operation is completed (either successfully or with failure), this block of code is executed
            if (e == null) {
                // If there's no exception, it means the operation completed successfully
                LOG.info("Payment for booking: {} processed! Please check the status.", paymentRequest.getBookingId());
            } else {
                // If an exception occurred, log the error message
                LOG.error(e.getMessage());
            }
        });
    }

}
