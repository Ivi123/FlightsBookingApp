package flightsearch.service;

import flightsearch.dtos.FlightDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class FlightSearchService {

    private final WebClient webClient;

    public FlightSearchService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
    }

    public Flux<FlightDto> searchFlights(String departure, String destination) {
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/flights/filter")
                        .queryParam("departure", departure)
                        .queryParam("destination", destination)
                        .build())
                .retrieve()
                .bodyToFlux(FlightDto.class);
    }
}
