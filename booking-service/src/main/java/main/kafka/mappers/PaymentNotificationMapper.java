package main.kafka.mappers;

import avro.PaymentNotification;
import main.model.Booking;
import org.springframework.stereotype.Component;

@Component
public class PaymentNotificationMapper {
    public PaymentNotification toPaymentNotification(Booking booking, String message) {
        return PaymentNotification.newBuilder()
                .setBookingId(booking.getId())
                .setPaymentId(booking.getPaymentDetails().getPaymentId())
                .setPrice(booking.getFlightDetails().getStandardPrice())
                .setCurrency(booking.getPaymentDetails().getCurrency())
                .setUserEmail("test-email" + Math.random() + "@gmail.com")
                .setMessage(message)
                .build();
    }
}
