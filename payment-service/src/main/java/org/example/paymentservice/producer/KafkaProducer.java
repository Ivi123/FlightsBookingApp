package org.example.paymentservice.producer;


import avro.PaymentRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaProducer {
    private static final Logger log = LoggerFactory.getLogger(KafkaProducer.class);
    private final KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * Constructor for KafkaProducer.
     *
     * @param kafkaTemplate             KafkaTemplate for sending messages to payment-response-topic.

     */
    public KafkaProducer( KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Method to send a payment request message to Kafka topics.
     *
     * @param key            The key associated with the message.
     * @param paymentRequest The payment request object to be sent.
     */
    public void sendMessage(String key, PaymentRequest paymentRequest) {
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("Sending message to payment-response-topic:: {}, {}", key, paymentRequest);
            kafkaTemplate.send("payment-response-topic", key, paymentRequest);
            return null;
        }).whenComplete((r, e) -> {
            if (e == null) {
                log.info("Payment for booking: {} processed! Please check the status.",paymentRequest.getBookingId());
            } else {
                log.error(e.getMessage());
            }
        });

    }
}
