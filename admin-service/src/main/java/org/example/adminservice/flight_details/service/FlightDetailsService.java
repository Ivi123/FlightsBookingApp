package org.example.adminservice.flight_details.service;

import org.example.adminservice.flight_details.dto.FlightDetailsDto;

import java.util.List;

public interface FlightDetailsService {
    List<FlightDetailsDto> getAllFlightDetails();

    FlightDetailsDto getFlightDetailsById(String id);

    FlightDetailsDto createFlightDetails(FlightDetailsDto flightDetailsDTO);

    FlightDetailsDto updateFlightDetails(FlightDetailsDto flightDetailsDTO);

    void deleteFlightDetails(String id);
}

