package com.example.notificationservice.producer;

import com.example.notificationservice.dto.BookingNotification;
import com.example.notificationservice.dto.PaymentNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class NotificationProducer {
    private static final String PAYMENT_NOTIFICATION_TOPIC = "payment-notification-topic";
    private static final String BOOKING_NOTIFICATION_TOPIC = "booking-notification-topic";


    @Autowired
    private KafkaTemplate<String, PaymentNotification> kafkaPaymentTemplate;

    @Autowired
    private KafkaTemplate<String, BookingNotification> kafkaBookingTemplate;

    public void sendMessageFromPayment(PaymentNotification paymentNotification) {
        CompletableFuture<SendResult<String, PaymentNotification>> future = kafkaPaymentTemplate.send(PAYMENT_NOTIFICATION_TOPIC, paymentNotification);
        future.whenComplete((result, ex) -> {
            if(ex == null) {
                System.out.println("Sent message=[" + result + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                System.out.println("Unable to send message = [" + result + "] due to: " + ex.getMessage());
            }
        });
    }

    public void sendMessageFromBooking(BookingNotification bookingNotification) {
        CompletableFuture<SendResult<String, BookingNotification>> future = kafkaBookingTemplate.send(BOOKING_NOTIFICATION_TOPIC, bookingNotification);
        future.whenComplete((result, ex) -> {
            if(ex == null) {
                System.out.println("Sent message=[" + result + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                System.out.println("Unable to send message = [" + result + "] due to: " + ex.getMessage());
            }
        });
    }
}
