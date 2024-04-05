package org.example.paymentservice.producer;

import org.example.dto.PaymentRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * KafkaProducer class responsible for sending messages to Kafka topics.
 */
@Service
public class KafkaProducer {
    private static final Logger log = LoggerFactory.getLogger(KafkaProducer.class);
    private final KafkaTemplate<String, Object> bookingKafkaTemplate;
    private final KafkaTemplate<String, Object> notificationKafkaTemplate;

    /**
     * Constructor for KafkaProducer.
     *
     * @param kafkaTemplate             KafkaTemplate for sending messages to payment-response-topic.
     * @param notificationKafkaTemplate KafkaTemplate for sending notification messages to notification-topic.
     */
    public KafkaProducer(KafkaTemplate<String, Object> kafkaTemplate, KafkaTemplate<String, Object> notificationKafkaTemplate) {
        this.bookingKafkaTemplate = kafkaTemplate;
        this.notificationKafkaTemplate = notificationKafkaTemplate;
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
            bookingKafkaTemplate.send("payment-response-topic", key, paymentRequest);
            log.info("Sending message to notification-topic:: {}, {}", key, paymentRequest);
            notificationKafkaTemplate.send("notification-topic", key, paymentRequest);
            return null;
        }).whenComplete((r, e) -> {
            if (e == null) {
                log.info("Payment for booking: " + paymentRequest.getBookingId() + " processed! Please check the status.");
            } else {
                log.error(e.getMessage());
            }
        });

    }
}
