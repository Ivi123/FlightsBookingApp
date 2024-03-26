package org.example.adminservice.flight_details.service.impl;

import org.example.adminservice.flight_details.dto.FlightDetailsDTO;
import org.example.adminservice.flight_details.exception.FlightDetailsNotFoundException;
import org.example.adminservice.flight_details.mapper.FlightDetailsMapper;
import org.example.adminservice.flight_details.model.FlightDetails;
import org.example.adminservice.flight_details.repository.FlightDetailsRepository;
import org.example.adminservice.flight_details.service.FlightDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightDetailsServiceImpl implements FlightDetailsService {

    @Autowired
    private FlightDetailsRepository flightDetailsRepository;

    @Autowired
    private FlightDetailsMapper flightDetailsMapper;

    @Override
    public List<FlightDetailsDTO> getAllFlightDetails() {
        return flightDetailsRepository.findAll().stream()
                .map(flightDetailsMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public FlightDetailsDTO getFlightDetailsById(String id) {
        return flightDetailsRepository.findById(id)
                .map(flightDetailsMapper::entityToDto)
                .orElseThrow(() -> new FlightDetailsNotFoundException(id));
    }

    @Override
    public FlightDetailsDTO createFlightDetails(FlightDetailsDTO flightDetailsDTO) {
        FlightDetails flightDetails = flightDetailsMapper.dtoToEntity(flightDetailsDTO);
        return flightDetailsMapper.entityToDto(flightDetailsRepository.save(flightDetails));
    }

    @Override
    public FlightDetailsDTO updateFlightDetails(String id, FlightDetailsDTO flightDetailsDTO) {
        return flightDetailsRepository.findById(id)
                .map(existingFlightDetails -> {
                    existingFlightDetails.setNumberOfSeats(flightDetailsDTO.getNumberOfSeats());
                    existingFlightDetails.setDate(flightDetailsDTO.getDate());
                    existingFlightDetails.setFlightId(flightDetailsDTO.getFlightId());
                    return flightDetailsMapper.entityToDto(flightDetailsRepository.save(existingFlightDetails));
                })
                .orElseThrow(() -> new FlightDetailsNotFoundException(id));
    }

    @Override
    public void deleteFlightDetails(String id) {
        if (flightDetailsRepository.existsById(id)) {
            flightDetailsRepository.deleteById(id);
        } else {
            throw new FlightDetailsNotFoundException(id);
        }
    }
}
