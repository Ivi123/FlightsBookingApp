package org.example.adminservice.operator.service;

import org.example.adminservice.operator.dto.OperatorDto;
import org.example.adminservice.operator.model.Operator;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;

public interface OperatorService {
    Operator addOperator(OperatorDto dto, Jwt jwt);

    Operator modifyIban(String id, OperatorDto dto);

    List<Operator> getAll();

    Operator getById(String Id);

    Operator getByName(String name);

    void deleteOperatorById(String id);
}
