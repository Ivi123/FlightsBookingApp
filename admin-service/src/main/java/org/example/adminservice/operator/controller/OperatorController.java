package org.example.adminservice.operator.controller;

import jakarta.validation.Valid;
import org.example.adminservice.operator.dto.OperatorDto;
import org.example.adminservice.operator.exception.OperatorNotFoundException;
import org.example.adminservice.operator.model.Operator;
import org.example.adminservice.operator.service.OperatorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/operator")
public class OperatorController {
    private final OperatorService operatorService;

    public OperatorController(OperatorService operatorService) {
        this.operatorService = operatorService;
    }

    @PostMapping("/add")
    public ResponseEntity<Operator> addOperator(@Valid @RequestBody OperatorDto dto, @AuthenticationPrincipal Jwt jwt) {
        Operator operator = operatorService.addOperator(dto,jwt);
        return new ResponseEntity<>(operator, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Operator>> getAllOperator() {
        List<Operator> operatorList = operatorService.getAll();
        return new ResponseEntity<>(operatorList, HttpStatus.OK);
    }

    @PutMapping("/modify-iban/{id}")
    public ResponseEntity<Operator> modifyOperator(@PathVariable String id, @RequestBody OperatorDto dto) {
        Operator operator = operatorService.modifyIban(id, dto);
        return new ResponseEntity<>(operator, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Operator> getOperatorById(@PathVariable String id) {
        try {
            Operator operator = operatorService.getById(id);
            return new ResponseEntity<>(operator, HttpStatus.OK);
        } catch (OperatorNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Operator> getOperatorByName(@PathVariable String name) {
        try {
            Operator operator = operatorService.getByName(name);
            return new ResponseEntity<>(operator, HttpStatus.OK);
        } catch (OperatorNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOperatorById(@PathVariable String id) {
        try {
            operatorService.deleteOperatorById(id);
            return new ResponseEntity<>(null,HttpStatus.OK);
        } catch (OperatorNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
