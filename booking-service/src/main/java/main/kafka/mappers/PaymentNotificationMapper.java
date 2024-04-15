package main.kafka.mappers;

import avro.PaymentNotification;
import main.model.Booking;
import main.model.FlightDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class PaymentNotificationMapper {
    public PaymentNotification toPaymentNotification(Booking booking, String message) {
        FlightDetails flightDetails = booking.getFlightDetails();
        return PaymentNotification.newBuilder()
                .setBookingId(booking.getId())
                .setPaymentId(booking.getPaymentDetails().getPaymentId())
                .setPrice(booking.getFlightDetails().getStandardPrice())
                .setCurrency(booking.getPaymentDetails().getCurrency())
                .setUserEmail("test-email" + Math.random() + "@gmail.com")
                .setMessage(message)
                .setOperatorId(flightDetails.getOperatorId())
                .setFlightId(flightDetails.getFlightId())
                .setDeparture(flightDetails.getDeparture())
                .setDestination(flightDetails.getDestination())
                .setNumberOfSeats(flightDetails.getNumberOfSeats())
                .setStandardPrice(flightDetails.getStandardPrice())
                .setDate(String.valueOf(LocalDate.now()))
                .build();
    }
}
