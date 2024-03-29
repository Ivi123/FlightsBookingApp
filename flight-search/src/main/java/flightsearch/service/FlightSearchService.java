package flightsearch.service;

import flightsearch.dtos.FunctionalityDto;
import flightsearch.dtos.FlightDto;
import flightsearch.dtos.FlightResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;


@Service
public class FlightSearchService {

    private final WebClient webClient;

    @Value("${external.api.lufthansa.baseurl}")
    private String lufthansaApiBaseUrl;

    @Value("${external.api.tarom.baseurl}")
    private String taromApiBaseUrl;

    public FlightSearchService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
    }

    public Flux<FlightResponseDto> searchFlights(String departure, String destination, String date) {
        Flux<FlightDto> localFlights = retrieveLocalFlights(departure, destination, date);
        Flux<String> distinctOperatorIds = extractDistinctOperatorIds(localFlights);
        Flux<String> urls = extractFlightSearchUrls(distinctOperatorIds);
        urls.subscribe(System.out::println);

        return requestFlightsFromOperators(urls, departure, destination, date);
    }

    //aici extrag toate zborurile din baza de date locala, in functie de departure, destination si date
    private Flux<FlightDto> retrieveLocalFlights(String departure, String destination, String date) {
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/flights/filter")
                        .queryParam("departure", departure)
                        .queryParam("destination", destination)
                        .queryParam("date", date)
                        .build())
                .retrieve()
                .bodyToFlux(FlightDto.class);
    }

    //aici extrag un flux de elemente unice cu toti operatorii(id-urile lor) ce au zboruri disponibile
    // in functie de filtrele date
    private Flux<String> extractDistinctOperatorIds(Flux<FlightDto> localFlights) {
        return localFlights
                .map(FlightDto::getOperatorId)
                .distinct();
    }

    // Pentru fiecare operatorId, apelăm endpointul de getFunctionalityByOperatorId și extragem funcționalitatea
    // de tip FLIGHT_SEARCH pentru fiecare operator
    private Flux<String> extractFlightSearchUrls(Flux<String> distinctOperatorIds) {
        return distinctOperatorIds
                .flatMap(this::retrieveFunctionalityByOperatorId)
                .map(FunctionalityDto::getURL)
                .distinct();
    }

    private Flux<FunctionalityDto> retrieveFunctionalityByOperatorId(String operatorId) {
        return this.webClient.get()
                .uri("/functionalities/operator/{operatorId}", operatorId)
                .retrieve()
                .bodyToFlux(FunctionalityDto.class)
                .filter(functionality -> "FLIGHT_SEARCH".equals(functionality.getType().toString()));
    }

    //iterez prin fiecare url si creez un request catre el
    private Flux<FlightResponseDto> requestFlightsFromOperators(Flux<String> urls, String departure,
                                                                String destination, String date) {
      return urls.flatMap(url -> {
            try {
                return makeHttpRequestForFlights(url, departure, destination, date);
            } catch (IllegalArgumentException e) {
                return Flux.empty(); //empty ca sa continue cu celelalte url-uri
            }
        });
    }

    //aici descompun si compun la loc url-ul pentru a putea fi apelat serviciul extern local
    private Flux<FlightResponseDto> makeHttpRequestForFlights(String url, String departure, String destination, String date) {
        String baseUrl = determineBaseUrl(url);
        String completeUrl = baseUrl + "/flight-search";

        return this.webClient.get()
                .uri(completeUrl, uriBuilder -> uriBuilder
                        .queryParam("departure", departure)
                        .queryParam("destination", destination)
                        .queryParam("date", date)
                        .build())
                .retrieve()
                .bodyToFlux(FlightResponseDto.class);
    }

    private String determineBaseUrl(String url) {
        if (url.contains("lufthansa.com")) {
            return lufthansaApiBaseUrl;
        } else if (url.contains("tarom.com")) {
            return taromApiBaseUrl;
        }

        throw new IllegalArgumentException("Unknown URL: " + url);
    }
}

