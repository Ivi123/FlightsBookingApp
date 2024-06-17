package flightsearch.service;

import flightsearch.dtos.FunctionalityDto;
import flightsearch.dtos.FlightDto;
import flightsearch.dtos.FlightResponseDto;
import flightsearch.dtos.LoginDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class FlightSearchService {
    private final WebClient webClient;
    private final WebClient webClientAuth;
    private final String lufthansaApiBaseUrl;
    private final String taromApiBaseUrl;
    private String authToken;

    public FlightSearchService(
            @Value("${external.api.lufthansa.baseurl}") String lufthansaApiBaseUrl,
            @Value("${external.api.tarom.baseurl}") String taromApiBaseUrl,
            WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
        this.webClientAuth = webClientBuilder.baseUrl("http://localhost:8087").build();
        this.lufthansaApiBaseUrl = lufthansaApiBaseUrl;
        this.taromApiBaseUrl = taromApiBaseUrl;
    }

    private String obtainAuthToken() {
        LoginDto loginDto = new LoginDto("ivona", "1234567");

        Mono<String> tokenMono = webClientAuth.post()
                .uri("/api/user/login")
                .bodyValue(loginDto)
                .retrieve()
                .bodyToMono(String.class);

        return "Bearer " + tokenMono.block();
    }

    public Flux<FlightResponseDto> searchFlights(String departure, String destination, String dateFrom, String dateTo) {
        authToken = obtainAuthToken();
        Flux<FlightDto> localFlights = retrieveLocalFlights(departure, destination, dateFrom, dateTo);
        Flux<String> distinctOperatorIds = extractDistinctOperatorIds(localFlights);
        Flux<String> urls = extractFlightSearchUrls(distinctOperatorIds);
        urls.subscribe(System.out::println);

        return requestFlightsFromOperators(urls, departure, destination, dateFrom, dateTo);
    }

    //aici extrag toate zborurile din baza de date locala, in functie de departure, destination si date
    protected Flux<FlightDto> retrieveLocalFlights(String departure, String destination, String dateFrom, String dateTo) {

        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/flights/filter")
                        .queryParam("departure", departure)
                        .queryParam("destination", destination)
                        .queryParam("dateFrom", dateFrom)
                        .queryParam("dateTo", dateTo)
                        .build())
                .header("Authorization", authToken)
                .retrieve()
                .bodyToFlux(FlightDto.class);
    }

    //aici extrag un flux de elemente unice cu toti operatorii(id-urile lor) ce au zboruri disponibile
    // in functie de filtrele date
    protected Flux<String> extractDistinctOperatorIds(Flux<FlightDto> localFlights) {
        return localFlights
                .map(FlightDto::getOperatorId)
                .distinct();
    }

    // Pentru fiecare operatorId, apelăm endpointul de getFunctionalityByOperatorId și extragem funcționalitatea
    // de tip FLIGHT_SEARCH pentru fiecare operator
    protected Flux<String> extractFlightSearchUrls(Flux<String> distinctOperatorIds) {
        return distinctOperatorIds
                .flatMap(this::retrieveFunctionalityByOperatorId)
                .map(FunctionalityDto::getURL)
                .distinct();
    }

    protected Flux<FunctionalityDto> retrieveFunctionalityByOperatorId(String operatorId) {
        return this.webClient.get()
                .uri("/functionalities/operator/{operatorId}", operatorId)
                .header("Authorization", authToken)
                .retrieve()
                .bodyToFlux(FunctionalityDto.class)
                .filter(functionality -> "FLIGHT_SEARCH".equals(functionality.getType().toString()));
    }

    //iterez prin fiecare url si creez un request catre el
    protected Flux<FlightResponseDto> requestFlightsFromOperators(Flux<String> urls, String departure,
                                                                  String destination, String dateFrom, String dateTo) {
        return urls.flatMap(url -> {
            try {
                return makeHttpRequestForFlights(url, departure, destination, dateFrom, dateTo);
            } catch (IllegalArgumentException e) {
                return Flux.empty(); //empty ca sa continue cu celelalte url-uri
            }
        });
    }

    //aici descompun si compun la loc url-ul pentru a putea fi apelat serviciul extern local
    protected Flux<FlightResponseDto> makeHttpRequestForFlights(String url, String departure, String destination,
                                                                String dateFrom, String dateTo) {
        String baseUrl = determineBaseUrl(url);
        String completeUrl = baseUrl + "/flight-search";

        return this.webClient.get()
                .uri(completeUrl, uriBuilder -> uriBuilder
                        .queryParam("departure", departure)
                        .queryParam("destination", destination)
                        .queryParam("dateFrom", dateFrom)
                        .queryParam("dateTo", dateTo)
                        .build())
                .header("Authorization", authToken)
                .retrieve()
                .bodyToFlux(FlightResponseDto.class);
    }

    protected String determineBaseUrl(String url) {
        if (url.contains("lufthansa.com")) {
            return lufthansaApiBaseUrl;
        } else if (url.contains("tarom.com")) {
            return taromApiBaseUrl;
        }

        throw new IllegalArgumentException("Unknown URL: " + url);
    }
}

