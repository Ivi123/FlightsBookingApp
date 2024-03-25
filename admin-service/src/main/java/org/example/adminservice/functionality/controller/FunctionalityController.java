package org.example.adminservice.functionality.controller;

import jakarta.validation.Valid;
import org.example.adminservice.functionality.dto.FunctionalityDto;
import org.example.adminservice.functionality.model.Functionality;
import org.example.adminservice.functionality.service.FunctionalityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/functionality")
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
}
