package com.example.notificationservice.consumer;

import avro.BookingNotification;
import avro.PaymentNotification;
import com.example.notificationservice.mapper.NotificationMapper;
import com.example.notificationservice.model.Notification;
import com.example.notificationservice.service.EmailSender;
import com.example.notificationservice.service.NotificationService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {
    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private NotificationService notificationService;

    @KafkaListener(topics = "payment-notification-topic", groupId = "notification-group", containerFactory = "kafkaListenerContainerFactoryPayment", errorHandler = "errorHandler")
    public void listenerPayment(ConsumerRecord<String, PaymentNotification> consumerRecord) {
        try {
            PaymentNotification paymentNotification = consumerRecord.value();
            Notification notification = notificationMapper.paymentRecordToEntity(paymentNotification);
            notificationService.insertNotification(notification);
            EmailSender.sendEmail(notification);

            System.out.println("Notification received: " + paymentNotification);
        } catch (Exception e) {
            throw new ListenerExecutionFailedException("Failed to process payment notification", e);
        }
    }

    @KafkaListener(topics = "booking-notification-topic", groupId = "notification-group", containerFactory = "kafkaListenerContainerFactoryBooking", errorHandler = "errorHandler")
    public void listenerBooking(ConsumerRecord<String, BookingNotification> consumerRecord) {
        try {
            BookingNotification bookingNotification = consumerRecord.value();
            Notification notification = notificationMapper.bookingRecordToEntity(bookingNotification);
            notificationService.insertNotification(notification);

            System.out.println("Avro message received value : " + bookingNotification);
        } catch (Exception e) {
            throw new ListenerExecutionFailedException("Failed to process booking notification", e);
        }
    }

    @KafkaListener(topics = {"payment-notification-topic-dlt", "booking-notification-topic-dlt"}, groupId = "notification-group")
    public void processDltMessage(String message) {
        System.out.println("Processing DLT message: " + message);
    }
}

