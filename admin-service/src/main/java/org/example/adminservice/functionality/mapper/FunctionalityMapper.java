package org.example.adminservice.functionality.mapper;

import org.example.adminservice.functionality.dto.FunctionalityDto;
import org.example.adminservice.functionality.model.Functionality;
import org.springframework.stereotype.Component;

@Component
public class FunctionalityMapper {
    public Functionality dtoToEntity(FunctionalityDto dto) {
        if (dto == null) {
            return null;
        }
        Functionality entity = new Functionality();

        entity.setOperatorId(dto.getOperatorId());
        entity.setType(dto.getType());
        entity.setURL(dto.getURL());

        return entity;
    }
}
