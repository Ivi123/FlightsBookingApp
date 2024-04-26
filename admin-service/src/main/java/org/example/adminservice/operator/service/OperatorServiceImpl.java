package org.example.adminservice.operator.service;

import org.example.adminservice.operator.dto.OperatorDto;
import org.example.adminservice.operator.exception.OperatorNotFoundException;
import org.example.adminservice.operator.mapper.OperatorMapper;
import org.example.adminservice.operator.model.Operator;
import org.example.adminservice.operator.repository.OperatorRepository;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OperatorServiceImpl implements  OperatorService{
    private final OperatorRepository operatorRepository;
    private final OperatorMapper operatorMapper;

    public OperatorServiceImpl(OperatorRepository operatorRepository, OperatorMapper operatorMapper) {
        this.operatorRepository = operatorRepository;
        this.operatorMapper = operatorMapper;
    }

    @Override
    public Operator addOperator(OperatorDto dto, Jwt jwt) {
        Operator operator = operatorMapper.dtoToEntity(dto);
        System.out.println("Operator added by "+ jwt.getClaim("email"));
        return operatorRepository.save(operator);
    }

    @Override
    public Operator modifyIban(String id, OperatorDto dto) {
        Optional<Operator> targetOperator = Optional.ofNullable
                (operatorRepository.findById(id).orElseThrow(() -> new OperatorNotFoundException(id)));
        if(targetOperator.isPresent()){
            targetOperator.get().setIBAN(dto.getIBAN());
            return operatorRepository.save(targetOperator.get());
        }else{
            throw new OperatorNotFoundException(id);
        }
    }

    @Override
    public List<Operator> getAll() {
        return operatorRepository.findAll();
    }

    @Override
    public Operator getById(String id) {
        return  operatorRepository.findById(id)
                .orElseThrow(() -> new OperatorNotFoundException(id));
    }

    @Override
    public Operator getByName(String name) {
        return operatorRepository.findByName(name)
                .orElseThrow(() -> new OperatorNotFoundException("Operator not found with name: " + name));
    }

    @Override
    public void deleteOperatorById(String id) {
        Optional<Operator> optionalOperator = operatorRepository.findById(id);
        if (optionalOperator.isPresent()) {
            Operator operator = optionalOperator.get();
            operatorRepository.delete(operator);
        } else {
            throw new OperatorNotFoundException("Operator with id: " + id+" not found");
        }

    }
}
