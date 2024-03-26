package org.example.adminservice.operator.mapper;

import org.example.adminservice.operator.dto.OperatorDto;
import org.example.adminservice.operator.model.Operator;
import org.springframework.stereotype.Component;

@Component
public class OperatorMapper {
    public OperatorDto entityToDto(Operator entity){
        if(entity == null){
            return null;
        }
        OperatorDto dto = new OperatorDto();
        dto.setId(entity.getId());
        dto.setCode(entity.getCode());
        dto.setIBAN(entity.getIBAN());
        dto.setName(entity.getName());
        return  dto;

    }
    public Operator dtoToEntity(OperatorDto dto){
        if(dto==null){
            return null;
        }
        Operator entity = new Operator();
        entity.setCode(dto.getCode());
        entity.setIBAN(dto.getIBAN());
        entity.setName(dto.getName());
        return entity;

    }
}
