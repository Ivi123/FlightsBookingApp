package org.example.adminservice.functionality.service;

import org.example.adminservice.functionality.dto.FunctionalityDto;
import org.example.adminservice.functionality.dto.UpdateFunctionalityDto;
import org.example.adminservice.functionality.exception.FunctionalityNotFoundException;
import org.example.adminservice.functionality.mapper.FunctionalityMapper;
import org.example.adminservice.functionality.model.Functionality;
import org.example.adminservice.functionality.repository.FunctionalityRepository;
import org.example.adminservice.operator.exception.OperatorNotFoundException;
import org.example.adminservice.operator.model.Operator;
import org.example.adminservice.operator.repository.OperatorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FunctionalityServiceImpl implements FunctionalityService {
    private final FunctionalityMapper mapper;
    private final FunctionalityRepository functionalityRepository;
    private final OperatorRepository operatorRepository;
    private final ModelMapper modelMapper;

    public FunctionalityServiceImpl(FunctionalityMapper mapper,
                                    FunctionalityRepository functionalityRepository,
                                    OperatorRepository operatorRepository,
                                    ModelMapper modelMapper) {
        this.mapper = mapper;
        this.functionalityRepository = functionalityRepository;
        this.operatorRepository = operatorRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Functionality add(FunctionalityDto dto) {
        Functionality functionality = mapper.dtoToEntity(dto);
        Optional<Operator> targetOperator = operatorRepository.findById(dto.getOperatorId());
        if (targetOperator.isPresent()) {
            return functionalityRepository.save(functionality);
        } else {
            throw new OperatorNotFoundException(dto.getOperatorId());
        }
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
