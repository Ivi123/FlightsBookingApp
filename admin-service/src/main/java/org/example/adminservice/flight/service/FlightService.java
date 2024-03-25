package org.example.adminservice.flight.service;
import org.example.adminservice.flight.dto.FlightDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FlightService {
    List<FlightDTO> getAllFlights();

    FlightDTO getFlightById(String id);

    FlightDTO createFlight(FlightDTO flightDTO);

    FlightDTO updateFlight(String id, FlightDTO flightDTO);

    void deleteFlight(String id);
}
