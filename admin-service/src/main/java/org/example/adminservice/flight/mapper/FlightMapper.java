package org.example.adminservice.flight.mapper;
import org.example.adminservice.flight.dto.FlightDto;
import org.example.adminservice.flight.model.Flight;
import org.springframework.stereotype.Component;

@Component
public class FlightMapper {

    public Flight dtoToEntity(FlightDto dto) {
        if (dto == null) {
            return null;
        }
        Flight entity = new Flight();

        entity.setOperatorId(dto.getOperatorId());
        entity.setDeparture(dto.getDeparture());
        entity.setDestination(dto.getDestination());

        return entity;
    }

    public FlightDto entityToDto(Flight entity) {
        if (entity == null) {
            return null;
        }
        FlightDto dto = new FlightDto();

        dto.setId(entity.getId());
        dto.setOperatorId(entity.getOperatorId());
        dto.setDeparture(entity.getDeparture());
        dto.setDestination(entity.getDestination());

        return dto;
    }
}

