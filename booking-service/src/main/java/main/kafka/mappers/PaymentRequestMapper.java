package main.kafka.mappers;

import avro.PaymentRequest;
import main.dto.BookingDTO;
import main.dto.FlightDetailsDTO;
import main.dto.PaymentDetailsDTO;
import org.springframework.stereotype.Component;


@Component
public class PaymentRequestMapper {

    public PaymentRequest toPaymentRequest(BookingDTO bookingDto) {
        FlightDetailsDTO flightDetailsDto = bookingDto.getFlightDetails();
        PaymentDetailsDTO paymentDetailsDto = bookingDto.getPaymentDetails();
        return PaymentRequest.newBuilder()
                .setId(null)
                .setBookingId(bookingDto.getBookingId())
                .setPrice(flightDetailsDto.getStandardPrice())
                .setCardNumber(paymentDetailsDto.getCardNumber())
                .setCardHolderName(paymentDetailsDto.getCardHolderName())
                .setExpirationMonth(paymentDetailsDto.getExpirationMonth())
                .setExpirationYear(paymentDetailsDto.getExpirationYear())
                .setCvv(paymentDetailsDto.getCvv())
                .setCurrency(paymentDetailsDto.getCurrency())
                .setStatus(paymentDetailsDto.getStatus())
                .build();

    }
}

