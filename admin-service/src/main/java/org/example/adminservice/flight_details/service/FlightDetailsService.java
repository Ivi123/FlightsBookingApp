package org.example.adminservice.flight_details.service;

import org.example.adminservice.flight_details.dto.FlightDetailsDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface FlightDetailsService {
    List<FlightDetailsDTO> getAllFlightDetails();

    FlightDetailsDTO getFlightDetailsById(String id);

    FlightDetailsDTO createFlightDetails(FlightDetailsDTO flightDetailsDTO);

    FlightDetailsDTO updateFlightDetails(FlightDetailsDTO flightDetailsDTO);

    void deleteFlightDetails(String id);
}

