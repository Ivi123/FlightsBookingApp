package com.example.integrationtests.glue;

import com.example.integrationtests.config.TestConfig;
import com.example.integrationtests.dtos.FlightDto;
import com.example.integrationtests.dtos.FlightResponseDto;
import com.example.integrationtests.dtos.LoginDto;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.mockserver.integration.ClientAndServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@CucumberContextConfiguration
@SpringBootTest(classes = {TestConfig.class})
public class FlightSearchSteps {
    @Autowired
    private ClientAndServer taromMockServer;

    @Autowired
    private ClientAndServer lufthansaMockServer;

    @Autowired
    private WebClient.Builder webClientBuilder;

    private Flux<FlightResponseDto> searchResults;
    private String authToken;
    private String flightIdTarom;
    private String flightIdLufthansa;
    private void obtainAuthToken() {
        WebClient webClientAuth = webClientBuilder.baseUrl("http://localhost:8087").build();
        LoginDto loginDTO = new LoginDto("ivona", "1234567");

        Mono<String> tokenMono = webClientAuth.post()
                .uri("/api/user/login")
                .bodyValue(loginDTO)
                .retrieve()
                .bodyToMono(String.class);

        authToken = "Bearer " + tokenMono.block();
    }

    @Given("the admin service has flights available for departure {string} and destination {string} on {string}")
    public void theAdminServiceHasFlightsAvailable(String departure, String destination, String date) {
        obtainAuthToken();
        WebClient webClient = webClientBuilder.baseUrl("http://localhost:8080").build();

        FlightDto flightTarom = new FlightDto("6641fabe8b87b96fe43d17d1", departure, destination, 100, date);
        Mono<FlightDto> responseTarom = webClient.post()
                .uri("/flights/add")
                .header("Authorization", authToken)
                .bodyValue(flightTarom)
                .retrieve()
                .bodyToMono(FlightDto.class);

        FlightDto addedFlightTarom = responseTarom.block();
        if (addedFlightTarom != null) {
            flightIdTarom = addedFlightTarom.getId();
        }

        FlightDto flightLufthansa= new FlightDto("6641fad78b87b96fe43d17d2", departure, destination, 100, date);
        Mono<FlightDto> responseLufthansa = webClient.post()
                .uri("/flights/add")
                .header("Authorization", authToken)
                .bodyValue(flightLufthansa)
                .retrieve()
                .bodyToMono(FlightDto.class);

        FlightDto addedFlightLufthansa = responseLufthansa.block();
        if (addedFlightLufthansa != null) {
            flightIdLufthansa = addedFlightLufthansa.getId();
        }
    }

    @And("the external service for operator {string} is available")
    public void theExternalServiceForOperatorIsAvailable(String operator) {
        if (operator.equals("lufthansa")) {
            lufthansaMockServer.when(
                    request()
                            .withMethod("GET")
                            .withPath("/lufthansa/flight-search")
            ).respond(
                    response()
                            .withHeader("Content-Type", "application/json")
                            .withBody("{ \"flightDto\": { \"id\": \"1\", \"operatorId\": \"lufthansa\", \"departure\": \"Bucharest\", \"destination\": \"London\", \"date\": \"2023-05-20\", \"numberOfSeats\": 150, \"departureTime\": \"2023-05-20T10:00:00\", \"arrivalTime\": \"2023-05-20T12:00:00\", \"standardPrice\": 200.0 }, \"operatorDto\": { \"id\": \"lufthansa\", \"code\": \"LH\", \"name\": \"Lufthansa\", \"IBAN\": \"DE89 3704 0044 0532 0130 00\" } }")
            );
        } else if (operator.equals("tarom")) {
            taromMockServer.when(
                    request()
                            .withMethod("GET")
                            .withPath("/tarom/flight-search")
            ).respond(
                    response()
                            .withHeader("Content-Type", "application/json")
                            .withBody("{ \"flightDto\": { \"id\": \"1\", \"operatorId\": \"tarom\", \"departure\": \"Bucharest\", \"destination\": \"London\", \"date\": \"2023-05-20\", \"numberOfSeats\": 150, \"departureTime\": \"2023-05-20T10:00:00\", \"arrivalTime\": \"2023-05-20T12:00:00\", \"standardPrice\": 200.0 }, \"operatorDto\": { \"id\": \"tarom\", \"code\": \"RO\", \"name\": \"Tarom\", \"IBAN\": \"RO49 AAAA 1B31 0075 9384 0000\" } }")
            );
        }
    }

    @When("the user searches for flights from {string} to {string} between {string} and {string}")
    public void theUserSearchesForFlights(String departure, String destination, String dateFrom, String dateTo) {
        WebClient webClient = webClientBuilder.baseUrl("http://localhost:8082").build();

        searchResults = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search-flights")
                        .queryParam("departure", departure)
                        .queryParam("destination", destination)
                        .queryParam("dateFrom", dateFrom)
                        .queryParam("dateTo", dateTo)
                        .build())
                .header("Authorization", authToken)
                .retrieve()
                .bodyToFlux(FlightResponseDto.class);
    }

    @Then("the user receives a list of available flights from both {string} and {string}")
    public void theUserReceivesAListOfAvailableFlights(String operator1, String operator2) {
        StepVerifier.create(searchResults)
                .expectNextMatches(flight -> flight.getOperatorDto().getName().equals(operator1) || flight.getOperatorDto().getName().equals(operator2))
                .expectNextCount(1)
                .verifyComplete(); //ar trebui sa am fix 2 zboruri
    }

    @After
    public void cleanUp() {
        WebClient webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
        if (flightIdTarom != null) {
            Mono<Void> deleteResponseTarom = webClient.delete()
                    .uri("/flights/delete/{id}", flightIdTarom)
                    .header("Authorization", authToken)
                    .retrieve()
                    .bodyToMono(Void.class);

            deleteResponseTarom.block();
        }

        if (flightIdLufthansa != null) {
            Mono<Void> deleteResponseLufthansa = webClient.delete()
                    .uri("/flights/delete/{id}", flightIdLufthansa)
                    .header("Authorization", authToken)
                    .retrieve()
                    .bodyToMono(Void.class);

            deleteResponseLufthansa.block();
        }
    }
}
