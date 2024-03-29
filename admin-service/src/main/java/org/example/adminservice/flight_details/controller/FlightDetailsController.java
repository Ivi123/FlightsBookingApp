package org.example.adminservice.flight_details.controller;
import org.example.adminservice.flight_details.dto.FlightDetailsDto;
import org.example.adminservice.flight_details.service.FlightDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flight-details")
public class FlightDetailsController {

    @Autowired
    private FlightDetailsService flightDetailsService;

    @GetMapping("/all")
    public List<FlightDetailsDto> getAllFlightDetails() {
        return flightDetailsService.getAllFlightDetails();
    }

    @GetMapping("/{id}")
    public FlightDetailsDto getFlightDetailsById(@PathVariable String id) {
        return flightDetailsService.getFlightDetailsById(id);
    }

    @PostMapping("/add")
    public FlightDetailsDto saveFlightDetails(@RequestBody FlightDetailsDto flightDetailsDTO) {
        return flightDetailsService.createFlightDetails(flightDetailsDTO);
    }

    @PutMapping("/update")
    public FlightDetailsDto updateFlightDetails(@RequestBody FlightDetailsDto flightDetailsDTO) {
        return flightDetailsService.updateFlightDetails(flightDetailsDTO);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteFlightDetails(@PathVariable String id) {
        flightDetailsService.deleteFlightDetails(id);
    }
}


