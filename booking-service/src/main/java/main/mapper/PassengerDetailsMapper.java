package main.mapper;

import main.dto.PassengerDetailsDTO;
import main.model.PassengerDetails;
import org.springframework.stereotype.Component;

@Component
public class PassengerDetailsMapper {
    public PassengerDetails toEntity(PassengerDetailsDTO dto) {
        PassengerDetails entity = new PassengerDetails();
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setHasLuggage(dto.isHasLuggage());
        return entity;
    }

    public PassengerDetailsDTO toDTO(PassengerDetails entity) {
        PassengerDetailsDTO dto = new PassengerDetailsDTO();
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setEmail(entity.getEmail());
        dto.setHasLuggage(entity.isHasLuggage());
        return dto;
    }
}
