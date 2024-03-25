package org.example.adminservice.functionality.controller;

import jakarta.validation.Valid;
import org.example.adminservice.functionality.dto.FunctionalityDto;
import org.example.adminservice.functionality.dto.UpdateFunctionalityDto;
import org.example.adminservice.functionality.exception.FunctionalityNotFoundException;
import org.example.adminservice.functionality.model.Functionality;
import org.example.adminservice.functionality.service.FunctionalityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/functionalities")
public class FunctionalityController {
    private final FunctionalityService functionalityService;

    public FunctionalityController(FunctionalityService functionalityService) {
        this.functionalityService = functionalityService;
    }

    @PostMapping("/add")
    public ResponseEntity<Functionality> addFunctionality(@Valid @RequestBody FunctionalityDto dto) {
        Functionality functionality =  functionalityService.add(dto);
        return new ResponseEntity<>(functionality, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Functionality>> getAll() {
        List<Functionality> functionalities = functionalityService.getAll();
        return new ResponseEntity<>(functionalities, HttpStatus.OK);
    }

    @GetMapping("/operator/{operatorId}")
    public ResponseEntity<List<Functionality>> getAllByOperatorId(@PathVariable String operatorId) {
        List<Functionality> functionalities = functionalityService.getAllByOperatorId(operatorId);
        return new ResponseEntity<>(functionalities, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Functionality> getById(@PathVariable String id) {
        Functionality functionality = functionalityService.getById(id)
                .orElseThrow(() -> new FunctionalityNotFoundException(id));
        return new ResponseEntity<>(functionality, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Functionality> updateFunctionality(@Valid @RequestBody UpdateFunctionalityDto updateDto,
                                                             @PathVariable String id) {
        Functionality functionality = functionalityService.updateFunctionality(id, updateDto);
        return new ResponseEntity<>(functionality, HttpStatus.ACCEPTED);
    }
}
