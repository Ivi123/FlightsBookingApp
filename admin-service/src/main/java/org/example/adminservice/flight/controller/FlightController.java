package org.example.adminservice.flight.controller;
import org.example.adminservice.flight.dto.FlightDTO;
import org.example.adminservice.flight.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flights")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @GetMapping
    public List<FlightDTO> getAllFlights() {
        return flightService.getAllFlights();
    }

    @GetMapping("/{id}")
    public FlightDTO getFlightById(@PathVariable String id) {
        return flightService.getFlightById(id);
    }

    @PostMapping
    public FlightDTO saveFlight(@RequestBody FlightDTO flightDTO) {
        return flightService.createFlight(flightDTO);
    }

    @PutMapping("/{id}")
    public FlightDTO updateFlight(@PathVariable String id, @RequestBody FlightDTO flightDTO) {
        return flightService.updateFlight(id, flightDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteFlight(@PathVariable String id) {
        flightService.deleteFlight(id);
    }

    // You can add more endpoints for specific operations as needed
}
