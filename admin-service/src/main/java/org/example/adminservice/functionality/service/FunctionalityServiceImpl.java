package org.example.adminservice.functionality.service;

import org.example.adminservice.functionality.dto.FunctionalityDto;
import org.example.adminservice.functionality.mapper.FunctionalityMapper;
import org.example.adminservice.functionality.model.Functionality;
import org.example.adminservice.functionality.repository.FunctionalityRepository;
import org.springframework.stereotype.Service;

@Service
public class FunctionalityServiceImpl implements FunctionalityService {
    private final FunctionalityMapper mapper;
    private final FunctionalityRepository functionalityRepository;

    public FunctionalityServiceImpl(FunctionalityMapper mapper,
                                    FunctionalityRepository functionalityRepository) {
        this.mapper = mapper;
        this.functionalityRepository = functionalityRepository;
    }

    @Override
    public Functionality add(FunctionalityDto dto) {
        Functionality functionality = mapper.dtoToEntity(dto);

        return functionalityRepository.save(functionality);
    }
}
