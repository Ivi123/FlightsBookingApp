package org.example.adminservice.flight.service.impl;
import org.example.adminservice.flight.dto.FlightDTO;
import org.example.adminservice.flight.exception.FlightNotFoundException;
import org.example.adminservice.flight.mapper.FlightMapper;
import org.example.adminservice.flight.model.Flight;
import org.example.adminservice.flight.repository.FlightRepository;
import org.example.adminservice.flight.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FlightServiceImpl implements FlightService {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private FlightMapper flightMapper;

    @Override
    public List<FlightDTO> getAllFlights() {
        return  flightRepository.findAll()
                .stream()
                .map(flightMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public FlightDTO getFlightById(String id) {
        return flightRepository.findById(id)
                .map(flightMapper::entityToDto)
                .orElseThrow(() -> new FlightNotFoundException(id));
    }

    @Override
    public FlightDTO createFlight(FlightDTO flightDTO) {
        Flight flight = flightMapper.dtoToEntity(flightDTO);
        return flightMapper.entityToDto(flightRepository.save(flight));
    }

    @Override
    public FlightDTO updateFlight(String id, FlightDTO flightDTO) {
        return flightRepository.findById(id)
                .map(existingFlight -> {
                    existingFlight.setDeparture(flightDTO.getDeparture());
                    existingFlight.setDestination(flightDTO.getDestination());
                    existingFlight.setOperatorId(flightDTO.getOperatorId());
                    return flightMapper.entityToDto(flightRepository.save(existingFlight));
                })
                .orElseThrow(() -> new FlightNotFoundException(id));
    }

    @Override
    public void deleteFlight(String id) {
        if (flightRepository.existsById(id)) {
            flightRepository.deleteById(id);
        } else {
            throw new FlightNotFoundException(id);
        }
    }
}

