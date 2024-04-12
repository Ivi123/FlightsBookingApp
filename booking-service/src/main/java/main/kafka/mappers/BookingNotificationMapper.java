package main.kafka.mappers;

import avro.BookingNotification;
import main.model.Booking;
import org.springframework.stereotype.Component;

@Component
public class BookingNotificationMapper {

    public BookingNotification toBookingNotification(Booking booking, String message) {
        return BookingNotification.newBuilder()
                .setBookingId(booking.getId())
                .setUserEmail("test-email" + Math.random() + "@gmail.com")
                .setMessage(message)
                .build();
    }
}
