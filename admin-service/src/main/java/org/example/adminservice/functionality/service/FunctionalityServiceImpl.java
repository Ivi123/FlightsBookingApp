package org.example.adminservice.functionality.service;

import org.example.adminservice.functionality.dto.FunctionalityDto;
import org.example.adminservice.functionality.dto.UpdateFunctionalityDto;
import org.example.adminservice.functionality.exception.FunctionalityNotFoundException;
import org.example.adminservice.functionality.mapper.FunctionalityMapper;
import org.example.adminservice.functionality.model.Functionality;
import org.example.adminservice.functionality.repository.FunctionalityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FunctionalityServiceImpl implements FunctionalityService {
    private final FunctionalityMapper mapper;
    private final FunctionalityRepository functionalityRepository;
    private final ModelMapper modelMapper;

    public FunctionalityServiceImpl(FunctionalityMapper mapper,
                                    FunctionalityRepository functionalityRepository,
                                    ModelMapper modelMapper) {
        this.mapper = mapper;
        this.functionalityRepository = functionalityRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Functionality add(FunctionalityDto dto) {
        Functionality functionality = mapper.dtoToEntity(dto);

        return functionalityRepository.save(functionality);
    }

    @Override
    public List<Functionality> getAll() {
        return functionalityRepository.findAll();
    }

    @Override
    public List<Functionality> getAllByOperatorId(String operatorId) {
        return functionalityRepository.findAllByOperatorId(operatorId);
    }

    @Override
    public Optional<Functionality> getById(String id) {
        return functionalityRepository.findById(id);
    }

    @Override
    public Functionality updateFunctionality(String id, UpdateFunctionalityDto updateDto) {
        Optional<Functionality> targetFunctionality = functionalityRepository.findById(id);
        if(targetFunctionality.isPresent()) {
            var functionality = targetFunctionality.get();
            modelMapper.map(updateDto, functionality);
            functionalityRepository.save(functionality);
            return functionality;
        } else {
            throw new FunctionalityNotFoundException(id);
        }
    }
}
