package com.example.notificationservice.consumer;


import avro.BookingNotification;
import avro.PaymentNotification;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

    @KafkaListener(topics = "payment-notification-topic", groupId = "notification-group")
    public void listenerPayment(ConsumerRecord<String, PaymentNotification> consumerRecord) {
        PaymentNotification value = consumerRecord.value();
        System.out.println("Avro message received value : " + value.toString());
    }

    @KafkaListener(topics = "booking-notification-topic", groupId = "notification-group")
    public void listenerBooking(ConsumerRecord<String, BookingNotification> consumerRecord) {
        BookingNotification value = consumerRecord.value();
        System.out.println("Avro message received value : " + value.toString());
    }
}

