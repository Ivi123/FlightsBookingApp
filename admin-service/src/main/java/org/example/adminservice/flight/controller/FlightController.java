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

    @GetMapping("/all")
    public List<FlightDTO> getAllFlights() {
        return flightService.getAllFlights();
    }

    @GetMapping("/{id}")
    public FlightDTO getFlightById(@PathVariable String id) {
        return flightService.getFlightById(id);
    }

    @PostMapping("/add")
    public FlightDTO saveFlight(@RequestBody FlightDTO flightDTO) {
        return flightService.createFlight(flightDTO);
    }

    @PutMapping("/update")
    public FlightDTO updateFlight(@RequestBody FlightDTO flightDTO) {
        return flightService.updateFlight(flightDTO);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteFlight(@PathVariable String id) {
        flightService.deleteFlight(id);
    }
}
