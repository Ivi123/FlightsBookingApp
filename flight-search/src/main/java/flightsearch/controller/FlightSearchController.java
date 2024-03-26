package flightsearch.controller;

import flightsearch.dtos.FlightDto;
import flightsearch.service.FlightSearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/search-flights")
public class FlightSearchController {

    private final FlightSearchService flightSearchService;

    public FlightSearchController(FlightSearchService flightSearchService) {
        this.flightSearchService = flightSearchService;
    }

    @GetMapping
    public Flux<FlightDto> getFlights(@RequestParam String departure,
                                      @RequestParam String destination) {
        return flightSearchService.searchFlights(departure, destination);
    }
}
