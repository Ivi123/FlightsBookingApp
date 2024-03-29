package org.example.adminservice.flight_details.mapper;
import org.example.adminservice.flight_details.dto.FlightDetailsDTO;
import org.example.adminservice.flight_details.model.FlightDetails;
import org.springframework.stereotype.Component;

@Component
public class FlightDetailsMapper {

    public FlightDetails dtoToEntity(FlightDetailsDTO dto) {
        if (dto == null) {
            return null;
        }
        FlightDetails entity = new FlightDetails();

        entity.setFlightId(dto.getFlightId());
        entity.setNumberOfSeats(dto.getNumberOfSeats());
        entity.setDate(dto.getDate());

        return entity;
    }

    public FlightDetailsDTO entityToDto(FlightDetails entity) {
        if (entity == null) {
            return null;
        }
        FlightDetailsDTO dto = new FlightDetailsDTO();

        dto.setId(entity.getId());
        dto.setFlightId(entity.getFlightId());
        dto.setNumberOfSeats(entity.getNumberOfSeats());
        dto.setDate(entity.getDate());

        return dto;
    }
}
