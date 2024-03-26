package org.example.adminservice.flight.service;
import org.example.adminservice.flight.dto.FlightDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface FlightService {
    List<FlightDTO> getAllFlights();

    FlightDTO getFlightById(String id);

    FlightDTO createFlight(FlightDTO flightDTO);

    FlightDTO updateFlight(FlightDTO flightDTO);

    void deleteFlight(String id);
}
