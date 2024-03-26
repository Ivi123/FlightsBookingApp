package org.example.adminservice.flight.controller;
import org.example.adminservice.flight.dto.FlightDto;
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
    public List<FlightDto> getAllFlights() {
        return flightService.getAllFlights();
    }

    @GetMapping("/filter")
    public List<FlightDto> getByDepAndDest(@RequestParam String departure,
                                           @RequestParam String destination) {
        return flightService.getByDepAndDest(departure, destination);
    }

    @GetMapping("/{id}")
    public FlightDto getFlightById(@PathVariable String id) {
        return flightService.getFlightById(id);
    }

    @PostMapping("/add")
    public FlightDto saveFlight(@RequestBody FlightDto flightDTO) {
        return flightService.createFlight(flightDTO);
    }

    @PutMapping("/update")
    public FlightDto updateFlight(@RequestBody FlightDto flightDTO) {
        return flightService.updateFlight(flightDTO);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteFlight(@PathVariable String id) {
        flightService.deleteFlight(id);
    }
}
