package org.example.adminservice.flight_details.service.impl;

import org.example.adminservice.flight.exception.FlightNotFoundException;
import org.example.adminservice.flight.repository.FlightRepository;
import org.example.adminservice.flight_details.dto.FlightDetailsDto;
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
    private FlightRepository flightRepository;
    @Autowired
    private FlightDetailsMapper flightDetailsMapper;

    @Override
    public List<FlightDetailsDto> getAllFlightDetails() {
        return flightDetailsRepository.findAll().stream()
                .map(flightDetailsMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public FlightDetailsDto getFlightDetailsById(String id) {
        return flightDetailsRepository.findById(id)
                .map(flightDetailsMapper::entityToDto)
                .orElseThrow(() -> new FlightDetailsNotFoundException(id));
    }

    @Override
    public FlightDetailsDto createFlightDetails(FlightDetailsDto flightDetailsDTO) {
        FlightDetails flightDetails = flightDetailsMapper.dtoToEntity(flightDetailsDTO);
        if (flightRepository.existsById(flightDetails.getFlightId())) {
            return flightDetailsMapper.entityToDto(flightDetailsRepository.save(flightDetails));
        } else {
            throw new FlightNotFoundException(flightDetails.getFlightId());
        }
    }

    @Override
    public FlightDetailsDto updateFlightDetails(FlightDetailsDto flightDetailsDTO) {
        if (flightRepository.existsById(flightDetailsDTO.getFlightId())) {
            return flightDetailsRepository.findById(flightDetailsDTO.getId())
                    .map(existingFlightDetails -> {
                        existingFlightDetails.setNumberOfSeats(flightDetailsDTO.getNumberOfSeats());
                        existingFlightDetails.setDate(flightDetailsDTO.getDate());
                        existingFlightDetails.setFlightId(flightDetailsDTO.getFlightId());
                        return flightDetailsMapper.entityToDto(flightDetailsRepository.save(existingFlightDetails));
                    })
                    .orElseThrow(() -> new FlightDetailsNotFoundException(flightDetailsDTO.getId()));
        } else {
            throw new FlightNotFoundException(flightDetailsDTO.getFlightId());
        }
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
