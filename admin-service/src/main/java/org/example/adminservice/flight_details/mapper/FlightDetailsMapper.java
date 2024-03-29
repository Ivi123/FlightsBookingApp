package org.example.adminservice.flight_details.mapper;
import org.example.adminservice.flight_details.dto.FlightDetailsDto;
import org.example.adminservice.flight_details.model.FlightDetails;
import org.springframework.stereotype.Component;

@Component
public class FlightDetailsMapper {

    public FlightDetails dtoToEntity(FlightDetailsDto dto) {
        if (dto == null) {
            return null;
        }
        FlightDetails entity = new FlightDetails();

        entity.setFlightId(dto.getFlightId());
        entity.setNumberOfSeats(dto.getNumberOfSeats());
        entity.setDate(dto.getDate());

        return entity;
    }

    public FlightDetailsDto entityToDto(FlightDetails entity) {
        if (entity == null) {
            return null;
        }
        FlightDetailsDto dto = new FlightDetailsDto();

        dto.setId(entity.getId());
        dto.setFlightId(entity.getFlightId());
        dto.setNumberOfSeats(entity.getNumberOfSeats());
        dto.setDate(entity.getDate());

        return dto;
    }
}
