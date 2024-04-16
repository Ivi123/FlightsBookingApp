package main.kafka.mappers;

import avro.BookingNotification;
import main.model.Booking;
import main.model.FlightDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class BookingNotificationMapper {

    public BookingNotification toBookingNotification(Booking booking, String message) {
        return BookingNotification.newBuilder()
                .setBookingId(booking.getId())
                //TODO: replace with actual email of current authenticated user
                .setUserEmail("test-email" + Math.random() + "@gmail.com")
                .setMessage(message)
                .build();
    }
}
