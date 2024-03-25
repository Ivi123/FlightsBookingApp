package org.example.adminservice.functionality.service;

import org.example.adminservice.functionality.dto.FunctionalityDto;
import org.example.adminservice.functionality.dto.UpdateFunctionalityDto;
import org.example.adminservice.functionality.model.Functionality;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface FunctionalityService {
    Functionality add(FunctionalityDto dto);

    List<Functionality> getAll();

    List<Functionality> getAllByOperatorId(String operatorId);

    Optional<Functionality> getById(String id);

    Functionality updateFunctionality(String id, UpdateFunctionalityDto updateDto);
}
