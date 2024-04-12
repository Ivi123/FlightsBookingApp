package main.kafka.mappers;

import avro.PaymentRequest;
import main.model.Booking;
import main.model.FlightDetails;
import main.model.PaymentDetails;
import org.springframework.stereotype.Component;


@Component
public class PaymentRequestMapper {

    public PaymentRequest toPaymentRequest(Booking booking) {
        FlightDetails flightDetails = booking.getFlightDetails();
        PaymentDetails paymentDetails = booking.getPaymentDetails();
        return PaymentRequest.newBuilder()
                .setId(null)
                .setBookingId(booking.getId())
                .setPrice(flightDetails.getStandardPrice())
                .setCardNumber(paymentDetails.getCardNumber())
                .setCardHolderName(paymentDetails.getCardHolderName())
                .setExpirationMonth(paymentDetails.getExpirationMonth())
                .setExpirationYear(paymentDetails.getExpirationYear())
                .setCvv(paymentDetails.getCvv())
                .setCurrency(paymentDetails.getCurrency())
                .setStatus(booking.getStatus())
                .build();

    }
}

