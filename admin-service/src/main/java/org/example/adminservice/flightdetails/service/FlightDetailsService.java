package org.example.adminservice.flightdetails.service;

import org.example.adminservice.flightdetails.dto.FlightDetailsDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FlightDetailsService {
    List<FlightDetailsDTO> getAllFlightDetails();

    FlightDetailsDTO getFlightDetailsById(String id);

    FlightDetailsDTO createFlightDetails(FlightDetailsDTO flightDetailsDTO);

    FlightDetailsDTO updateFlightDetails(String id, FlightDetailsDTO flightDetailsDTO);

    void deleteFlightDetails(String id);
}

