package main.mapper;

import main.dto.BookingDTO;
import main.model.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class BookingMapper {

    @Autowired
    private FlightDetailsMapper flightDetailsMapper;
    @Autowired
    private PassengerDetailsMapper passengerDetailsMapper;
    @Autowired
    private PaymentDetailsMapper paymentDetailsMapper;

    public Booking toEntity(BookingDTO bookingDTO) {
        Booking booking = new Booking();
        booking.setId(bookingDTO.getId());
        booking.setUserId(bookingDTO.getUserId());
        booking.setCreatedAt(bookingDTO.getCreatedAt());

        if (bookingDTO.getPaymentDetails() != null) {
            booking.setPaymentDetails(paymentDetailsMapper.toEntity(bookingDTO.getPaymentDetails()));
        }

        if (bookingDTO.getPassengerDetails() != null) {
            booking.setPassengerDetails(bookingDTO.getPassengerDetails().stream().map(passengerDetailsMapper::toEntity).collect(Collectors.toList()));
        }

        if (bookingDTO.getFlightDetails() != null) {
            booking.setFlightDetails(flightDetailsMapper.toEntity(bookingDTO.getFlightDetails()));
        }

        booking.setStatus(bookingDTO.getStatus());

        return booking;
    }

    public BookingDTO toDTO(Booking booking) {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setId(booking.getId());
        bookingDTO.setUserId(booking.getUserId());
        bookingDTO.setCreatedAt(booking.getCreatedAt());

        if (booking.getPaymentDetails() != null) {
            bookingDTO.setPaymentDetails(paymentDetailsMapper.toDTO(booking.getPaymentDetails()));
        }

        if (booking.getPassengerDetails() != null) {
            bookingDTO.setPassengerDetails(booking.getPassengerDetails().stream().map(passengerDetailsMapper::toDTO).collect(Collectors.toList()));
        }

        if (booking.getFlightDetails() != null) {
            bookingDTO.setFlightDetails(flightDetailsMapper.toDTO(booking.getFlightDetails()));
        }

        bookingDTO.setStatus(booking.getStatus());

        return bookingDTO;
    }
}
