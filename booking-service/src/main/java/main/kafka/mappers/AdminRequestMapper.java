package main.kafka.mappers;

import avro.AdminRequest;
import main.model.Booking;
import org.springframework.stereotype.Component;

@Component
public class AdminRequestMapper {

    public AdminRequest toAdminRequest(Booking booking) {
        AdminRequest adminRequest = new AdminRequest();
        adminRequest.setId(null);
        adminRequest.setBookingId(booking.getBookingId());

        adminRequest.setOperatorId(booking.getFlightDetails().getOperatorId());

        adminRequest.setFlightId(booking.getFlightDetails().getFlightId());
        adminRequest.setDeparture(booking.getFlightDetails().getDeparture());
        adminRequest.setDestination(booking.getFlightDetails().getDestination());

        int numberOfSeats = booking.getPassengerDetails().size();
        adminRequest.setNumberOfSeats(numberOfSeats);

        adminRequest.setStandardPrice(booking.getFlightDetails().getStandardPrice());

        String date = booking.getFlightDetails().getDeparture();
        adminRequest.setDate(date);

        adminRequest.setStatus(booking.getStatus());

        return adminRequest;
    }
}
