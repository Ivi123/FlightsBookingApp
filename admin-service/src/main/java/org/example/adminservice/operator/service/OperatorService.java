package org.example.adminservice.operator.service;

import org.example.adminservice.operator.dto.OperatorDto;
import org.example.adminservice.operator.model.Operator;

import java.util.List;

public interface OperatorService {
    Operator addOperator(OperatorDto dto);

    Operator modifyIban(String id, OperatorDto dto);

    List<Operator> getAll();

    Operator getById(String Id);

}
