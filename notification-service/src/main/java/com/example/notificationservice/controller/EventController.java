package com.example.notificationservice.controller;

import com.example.notificationservice.dto.BookingNotification;
import com.example.notificationservice.dto.PaymentNotification;
import com.example.notificationservice.producer.NotificationProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {
    @Autowired
    private NotificationProducer producer;

    @PostMapping("/payment")
    public String sendMessageFromPayment(@RequestBody PaymentNotification paymentNotification) {
        producer.sendMessageFromPayment(paymentNotification);
        return "message published!";
    }

    @PostMapping("/booking")
    public String sendMessageFromBooking(@RequestBody BookingNotification bookingNotification) {
        producer.sendMessageFromBooking(bookingNotification);
        return "message published!";
    }
}
