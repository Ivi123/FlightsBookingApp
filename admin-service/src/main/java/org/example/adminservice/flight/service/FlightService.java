package org.example.adminservice.flight.service;
import org.example.adminservice.flight.dto.FlightDto;

import java.util.Date;
import java.util.List;

public interface FlightService {
    List<FlightDto> getAllFlights();

    FlightDto getFlightById(String id);

    FlightDto createFlight(FlightDto flightDTO);

    FlightDto updateFlight(FlightDto flightDTO);

    void deleteFlight(String id);

    List<FlightDto> getByDepDestAndDate(String departure, String destination, String date);
}
