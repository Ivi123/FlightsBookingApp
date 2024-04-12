package main.kafka.producer;


import avro.AdminRequest;
import avro.BookingNotification;
import avro.PaymentRequest;
import org.apache.avro.specific.SpecificRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class BookingProducerService {

    private static final Logger log = LoggerFactory.getLogger(BookingProducerService.class);
    private final KafkaTemplate<String, SpecificRecord> avroKafkaTemplate;

    @Autowired
    public BookingProducerService(KafkaTemplate<String, SpecificRecord> avroKafkaTemplate) {
        this.avroKafkaTemplate = avroKafkaTemplate;
    }

    public void sendAdminRequest(String key, AdminRequest adminRequest) {
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("Sending message to admin-request-topic:: {}, {}", key, adminRequest);
            avroKafkaTemplate.send("admin-request-topic", key, adminRequest);
            return null;
        }).whenComplete((r, e) -> {
            if (e == null) {
                log.info("Sent message to admin service for booking with id " + adminRequest.getBookingId());
            } else {
                log.error(e.getMessage());
            }
        });
    }

    public void sendPaymentRequest(String key, PaymentRequest paymentRequest) {
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("Sending message to payment-request-topic:: {}, {}", key, paymentRequest);
            avroKafkaTemplate.send("payment-request-topic", key, paymentRequest);
            return null;
        }).whenComplete((r, e) -> {
            if (e == null) {
                log.info("Sent message to payment service for booking with id " + paymentRequest.getBookingId());
            } else {
                log.error(e.getMessage());
            }
        });
    }

    public void sendBookingNotificationRequest(String key, BookingNotification notificationRequest) {
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("Sending message to booking-notification-topic:: {}, {}", key, notificationRequest);
            avroKafkaTemplate.send("booking-notification-topic", key, notificationRequest);
            return null;
        }).whenComplete((r, e) -> {
            if (e == null) {
                log.info("Sent message to notification service for booking with id " + notificationRequest.getBookingId());
            } else {
                log.error(e.getMessage());
            }
        });
    }

    public void sendPaymentNotificationRequest(String key, BookingNotification notificationRequest) {
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("Sending message to payment-notification-topic:: {}, {}", key, notificationRequest);
            avroKafkaTemplate.send("payment-notification-topic", key, notificationRequest);
            return null;
        }).whenComplete((r, e) -> {
            if (e == null) {
                log.info("Sent message to notification service for payment for booking with id " + notificationRequest.getBookingId());
            } else {
                log.error(e.getMessage());
            }
        });
    }
}

