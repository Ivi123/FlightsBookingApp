package main.kafka.mappers;

import avro.BookingNotification;
import main.model.Booking;
import org.springframework.stereotype.Component;

@Component
public class NotificationRequestMapper {

    public BookingNotification toBookingNotification(Booking booking, String message) {
        return BookingNotification.newBuilder()
                .setBookingId(booking.getBookingId())
                .setMessage(message)
                .build();
    }
}
