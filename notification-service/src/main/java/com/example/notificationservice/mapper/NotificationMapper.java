package com.example.notificationservice.mapper;

import avro.BookingNotification;
import avro.PaymentNotification;
import com.example.notificationservice.model.Notification;
import com.example.notificationservice.model.NotificationType;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class NotificationMapper {
    public Notification paymentRecordToEntity(PaymentNotification paymentNotification) {
        Notification notification = new Notification();
        
        notification.setId(paymentNotification.getPaymentId().toString());
        notification.setMessage(paymentNotification.getMessage().toString());
        //notification.setUserEmail(paymentNotification.getUserEmail().toString());
        notification.setUserEmail("ivonamaria14@gmail.com");
        notification.setPaymentId(paymentNotification.getPaymentId().toString());
        notification.setPrice(paymentNotification.getPrice());
        notification.setCurrency(paymentNotification.getCurrency().toString());
        notification.setDate(paymentNotification.getDate().toString());
        notification.setDeparture(paymentNotification.getDeparture().toString());
        notification.setDestination(paymentNotification.getDestination().toString());
        notification.setPrice(paymentNotification.getStandardPrice()*paymentNotification.getNumberOfSeats());
        notification.setNumberOfSeats(paymentNotification.getNumberOfSeats());
        notification.setType(NotificationType.PAYMENT);
        
        return notification;
    }

    public Notification bookingRecordToEntity(BookingNotification bookingNotification) {
        Notification notification = new Notification();

        notification.setMessage(bookingNotification.getMessage().toString());
        notification.setUserEmail(bookingNotification.getUserEmail().toString());
        notification.setBookingId(bookingNotification.getBookingId().toString());
        notification.setType(NotificationType.BOOKING);
        notification.setDate(LocalDate.now().toString());

        return notification;
    }
}
