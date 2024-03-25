package org.example.adminservice.operator.controller;

import jakarta.validation.Valid;
import org.example.adminservice.operator.dto.OperatorDto;
import org.example.adminservice.operator.model.Operator;
import org.example.adminservice.operator.service.OperatorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Operator> addOperator(@Valid @RequestBody OperatorDto dto){
        Operator operator = operatorService.addOperator(dto);
        return new ResponseEntity<>(operator, HttpStatus.CREATED);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Operator>> getAllOperator(){
        List<Operator> operatorList = operatorService.getAll();
        return  new ResponseEntity<>(operatorList,HttpStatus.OK);
    }
    @PutMapping("/modify-iban/{id}")
    public ResponseEntity<Operator> modifyOperator(@PathVariable String id, @Valid @RequestBody OperatorDto dto){
        Operator operator = operatorService.modifyIban(id,dto);
        return new ResponseEntity<>(operator, HttpStatus.ACCEPTED);
    }
}
