package main.mapper;

import main.dto.FlightDetailsDTO;
import main.model.FlightDetails;
import org.springframework.stereotype.Component;

@Component
public class FlightDetailsMapper {
    public FlightDetails toEntity(FlightDetailsDTO dto) {
        FlightDetails entity = new FlightDetails();
        entity.setOperatorId(dto.getOperatorId());
        entity.setFlightId(dto.getFlightId());
        entity.setDeparture(dto.getDeparture());
        entity.setDestination(dto.getDestination());
        entity.setNumberOfSeats(dto.getNumberOfSeats());
        entity.setStandardPrice(dto.getStandardPrice());
        return entity;
    }

    public FlightDetailsDTO toDTO(FlightDetails entity) {
        FlightDetailsDTO dto = new FlightDetailsDTO();
        dto.setOperatorId(entity.getOperatorId());
        dto.setFlightId(entity.getFlightId());
        dto.setDeparture(entity.getDeparture());
        dto.setDestination(entity.getDestination());
        dto.setNumberOfSeats(entity.getNumberOfSeats());
        dto.setStandardPrice(entity.getStandardPrice());
        return dto;
    }
}
