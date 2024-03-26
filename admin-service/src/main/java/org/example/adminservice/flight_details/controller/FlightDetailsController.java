package org.example.adminservice.flight_details.controller;
import org.example.adminservice.flight_details.dto.FlightDetailsDTO;
import org.example.adminservice.flight_details.service.FlightDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flight-details")
public class FlightDetailsController {

    @Autowired
    private FlightDetailsService flightDetailsService;

    @GetMapping
    public List<FlightDetailsDTO> getAllFlightDetails() {
        return flightDetailsService.getAllFlightDetails();
    }

    @GetMapping("/{id}")
    public FlightDetailsDTO getFlightDetailsById(@PathVariable String id) {
        return flightDetailsService.getFlightDetailsById(id);
    }

    @PostMapping
    public FlightDetailsDTO saveFlightDetails(@RequestBody FlightDetailsDTO flightDetailsDTO) {
        return flightDetailsService.createFlightDetails(flightDetailsDTO);
    }

    @PutMapping("/{id}")
    public FlightDetailsDTO updateFlightDetails(@PathVariable String id, @RequestBody FlightDetailsDTO flightDetailsDTO) {
        return flightDetailsService.updateFlightDetails(id, flightDetailsDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteFlightDetails(@PathVariable String id) {
        flightDetailsService.deleteFlightDetails(id);
    }
}


