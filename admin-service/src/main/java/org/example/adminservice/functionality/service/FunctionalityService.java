package org.example.adminservice.functionality.service;

import org.example.adminservice.functionality.dto.FunctionalityDto;
import org.example.adminservice.functionality.model.Functionality;
import org.springframework.stereotype.Service;

@Service
public interface FunctionalityService {
    Functionality add(FunctionalityDto dto);
}
