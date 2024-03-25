package org.example.adminservice.flight.mapper;
import org.example.adminservice.flight.dto.FlightDTO;
import org.example.adminservice.flight.model.Flight;
import org.springframework.stereotype.Component;

@Component
public class FlightMapper {

    public Flight dtoToEntity(FlightDTO dto) {
        if (dto == null) {
            return null;
        }
        Flight entity = new Flight();

        entity.setOperatorId(dto.getOperatorId());
        entity.setDeparture(dto.getDeparture());
        entity.setDestination(dto.getDestination());

        return entity;
    }

    public FlightDTO entityToDto(Flight entity) {
        if (entity == null) {
            return null;
        }
        FlightDTO dto = new FlightDTO();

        dto.setId(entity.getId());
        dto.setOperatorId(entity.getOperatorId());
        dto.setDeparture(entity.getDeparture());
        dto.setDestination(entity.getDestination());

        return dto;
    }
}

