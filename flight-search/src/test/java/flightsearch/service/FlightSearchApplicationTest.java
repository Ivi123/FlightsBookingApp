package flightsearch.service;

import flightsearch.dtos.FlightDto;
import flightsearch.dtos.FlightResponseDto;
import flightsearch.dtos.FunctionalityDto;
import flightsearch.dtos.FunctionalityType;
import flightsearch.dtos.OperatorDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;

@SpringBootTest
public class FlightSearchApplicationTest {

    public static final String TAROM_URL = "http://tarom.com/flight-search";
    public static final String LUFTHANSA_URL = "http://lufthansa.com/flight-search";
    @Mock
    private WebClient.Builder mockWebClientBuilder;
    @Mock
    private WebClient mockWebClient;
    private FlightSearchService flightSearchService;

    @BeforeEach
    public void setup() {
        Mockito.when(mockWebClientBuilder.baseUrl("http://localhost:8080")).thenReturn(mockWebClientBuilder);
        Mockito.when(mockWebClientBuilder.build()).thenReturn(mockWebClient);

        flightSearchService = new FlightSearchService(
                "http://localhost:8084/lufthansa",
                "http://localhost:8083/tarom",
                mockWebClientBuilder);
    }

    @AfterEach
    public void cleanup() {
        flightSearchService = null;
    }

    @Test
    public void testRetrieveLocalFlights() {
        WebClient.RequestHeadersUriSpec requestHeadersUriMock = Mockito.mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec requestHeadersMock = Mockito.mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpecMock = Mockito.mock(WebClient.ResponseSpec.class);

        Flux<FlightDto> expectedFlights = Flux.just(new FlightDto());

        Mockito.when(mockWebClient.get()).thenReturn(requestHeadersUriMock);
        Mockito.when(requestHeadersUriMock.uri(Mockito.any(Function.class))).thenReturn(requestHeadersMock);
        Mockito.when(requestHeadersMock.retrieve()).thenReturn(responseSpecMock);
        Mockito.when(responseSpecMock.bodyToFlux(FlightDto.class)).thenReturn(expectedFlights);

        Flux<FlightDto> actualFlights =
                flightSearchService.retrieveLocalFlights("New York", "London", "2024-01-01", "2024-01-07");

        Assertions.assertEquals(actualFlights, expectedFlights);
        //testez ca primesc ceva inapoi -> ca returneaza ceva
        Assertions.assertNotNull(actualFlights, "Flights were not received");
    }

    @Test
    public void testExtractDistinctOperatorIds() {
        List<FlightDto> flightDtos = Arrays.asList(
                new FlightDto("1", "op1", "New York", "London", "12-12-2024", 2, LocalDateTime.of(2024, 12, 12, 12, 37, 8, 644 * 1000000), LocalDateTime.of(2024, 12, 12, 12, 37, 8, 644 * 1000000), 219.00),
                new FlightDto("2", "op3", "New York", "London", "12-12-2024", 2, LocalDateTime.of(2024, 12, 12, 12, 37, 8, 644 * 1000000), LocalDateTime.of(2024, 12, 12, 12, 37, 8, 644 * 1000000), 219.00),
                new FlightDto("3", "op1", "New York", "London", "12-12-2024", 2, LocalDateTime.of(2024, 12, 12, 12, 37, 8, 644 * 1000000), LocalDateTime.of(2024, 12, 12, 12, 37, 8, 644 * 1000000), 219.00),
                new FlightDto("4", "op2", "New York", "London", "12-12-2024", 2, LocalDateTime.of(2024, 12, 12, 12, 37, 8, 644 * 1000000), LocalDateTime.of(2024, 12, 12, 12, 37, 8, 644 * 1000000), 219.00)
        );

        Flux<FlightDto> flightDtoFlux = Flux.fromIterable(flightDtos);

        List<String> result = flightSearchService.extractDistinctOperatorIds(flightDtoFlux)
                .toStream()
                .toList();

        Set<String> expectedSet = new TreeSet<>(Arrays.asList("op1", "op2", "op3"));
        Set<String> resultSet = new TreeSet<>(result);

        Assertions.assertEquals(expectedSet, resultSet);
    }

    @Test
    public void testRetrieveFunctionalityByOperatorId() {
        WebClient.RequestHeadersUriSpec requestHeadersUriMock = Mockito.mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec requestHeadersMock = Mockito.mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpecMock = Mockito.mock(WebClient.ResponseSpec.class);

        List<FunctionalityDto> functionalityDtos = Arrays.asList(
                new FunctionalityDto("1", "op1", LUFTHANSA_URL, FunctionalityType.FLIGHT_SEARCH),
                new FunctionalityDto("2", "op1", "http://lufthansa.com/booking", FunctionalityType.BOOKING),
                new FunctionalityDto("3", "op1", "http://lufthansa.com/payment", FunctionalityType.PAYMENT)
                );

        Mockito.when(mockWebClient.get()).thenReturn(requestHeadersUriMock);
        Mockito.when(requestHeadersUriMock.uri(Mockito.anyString(), Mockito.anyString())).thenReturn(requestHeadersMock);
        Mockito.when(requestHeadersMock.retrieve()).thenReturn(responseSpecMock);
        Mockito.when(responseSpecMock.bodyToFlux(FunctionalityDto.class)).thenReturn(Flux.fromIterable(functionalityDtos));

        // Verifică dacă doar funcționalitatea cu tipul "FLIGHT_SEARCH" este returnata
        StepVerifier.create(flightSearchService.retrieveFunctionalityByOperatorId("op1"))
                .expectNextMatches(f -> f.getURL().equals(LUFTHANSA_URL))
                .verifyComplete();
    }

    @Test
    void testExtractFlightSearchUrls() {
        WebClient.RequestHeadersUriSpec requestHeadersUriMock = Mockito.mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec requestHeadersMock = Mockito.mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpecMock = Mockito.mock(WebClient.ResponseSpec.class);

        List<FunctionalityDto> functionalitiesOp1 = Arrays.asList(
                new FunctionalityDto("1", "op1", LUFTHANSA_URL, FunctionalityType.FLIGHT_SEARCH),
                new FunctionalityDto("2", "op1", "http://lufthansa.com/booking", FunctionalityType.BOOKING),
                new FunctionalityDto("3", "op1", "http://lufthansa.com/payment", FunctionalityType.PAYMENT)
        );
        List<FunctionalityDto> functionalitiesOp2 = Arrays.asList(
                new FunctionalityDto("4", "op2", TAROM_URL, FunctionalityType.FLIGHT_SEARCH),
                new FunctionalityDto("5", "op2", "http://tarom.com/booking", FunctionalityType.BOOKING),
                new FunctionalityDto("6", "op2", "http://tarom.com/payment", FunctionalityType.PAYMENT)
        );

        Mockito.when(mockWebClient.get()).thenReturn(requestHeadersUriMock);
        Mockito.when(requestHeadersUriMock.uri(Mockito.anyString(), Mockito.anyString())).thenReturn(requestHeadersMock);
        Mockito.when(requestHeadersMock.retrieve()).thenReturn(responseSpecMock);

        Mockito.when(responseSpecMock.bodyToFlux(FunctionalityDto.class))
                .thenReturn(Flux.fromIterable(functionalitiesOp1))
                .thenReturn(Flux.fromIterable(functionalitiesOp2));

        // Creează fluxul de operatori distinct
        Flux<String> distinctOperatorIds = Flux.just("op1", "op2");

        // Verifică dacă doar URL-urile relevante pentru "FLIGHT_SEARCH" sunt returnate
        StepVerifier.create(flightSearchService.extractFlightSearchUrls(distinctOperatorIds))
                .expectNext(LUFTHANSA_URL)
                .expectNext(TAROM_URL)
                .verifyComplete();
    }

    @Test
    void testMakeHttpRequestForFlights() {
        WebClient.RequestHeadersUriSpec requestHeadersUriMock = Mockito.mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec requestHeadersMock = Mockito.mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpecMock = Mockito.mock(WebClient.ResponseSpec.class);

        // Creează mock-uri pentru cererile WebClient
        FlightDto flightDto1 = new FlightDto("1", "op1", "New York", "London", "2024-12-12", 2, LocalDateTime.of(2024, 12, 12, 12, 37, 8, 644 * 1000000), LocalDateTime.of(2024, 12, 12, 12, 37, 8, 644 * 1000000), 219.0);
        FlightDto flightDto2 = new FlightDto("2", "op1", "New York", "London", "2024-12-12", 2, LocalDateTime.of(2024, 12, 12, 12, 37, 8, 644 * 1000000), LocalDateTime.of(2024, 12, 12, 12, 37, 8, 644 * 1000000), 219.0);
        OperatorDto operatorDto1 = new OperatorDto("op1", "LFT5465", "Lufthansa", "CRT232JFWHJFJ8811RF");

        List<FlightResponseDto> flights = Arrays.asList(
                new FlightResponseDto(flightDto1, operatorDto1),
                new FlightResponseDto(flightDto2, operatorDto1)
        );

        // Configurează mock-urile
        Mockito.when(mockWebClient.get()).thenReturn(requestHeadersUriMock);
        Mockito.when(requestHeadersUriMock.uri(Mockito.anyString(), Mockito.any(Function.class))).thenReturn(requestHeadersMock);
        Mockito.when(requestHeadersMock.retrieve()).thenReturn(responseSpecMock);
        Mockito.when(responseSpecMock.bodyToFlux(FlightResponseDto.class)).thenReturn(Flux.fromIterable(flights));

        // Verifică rezultatele fluxului returnat de makeHttpRequestForFlights
        StepVerifier.create(flightSearchService.makeHttpRequestForFlights(LUFTHANSA_URL, "New York", "London", "2024-12-12", "2024-12-14"))
                .expectNext(flights.get(0))
                .expectNext(flights.get(1))
                .verifyComplete();
    }

    @Test
    void testRequestFlightsFromOperators() {
        WebClient.RequestHeadersUriSpec requestHeadersUriMock = Mockito.mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec requestHeadersMock = Mockito.mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpecMock = Mockito.mock(WebClient.ResponseSpec.class);

        FlightDto flightDto1 = new FlightDto("1", "op1", "New York", "London", "2024-12-12", 2, LocalDateTime.of(2024, 12, 12, 12, 37, 8, 644 * 1000000), LocalDateTime.of(2024, 12, 12, 12, 37, 8, 644 * 1000000), 219.0);
        FlightDto flightDto2 = new FlightDto("2", "op1", "New York", "London", "2024-12-12", 2, LocalDateTime.of(2024, 12, 12, 12, 37, 8, 644 * 1000000), LocalDateTime.of(2024, 12, 12, 12, 37, 8, 644 * 1000000), 219.0);

        FlightDto flightDto3 = new FlightDto("3", "op2", "New York", "London", "2024-12-12", 2, LocalDateTime.of(2024, 12, 12, 12, 37, 8, 644 * 1000000), LocalDateTime.of(2024, 12, 12, 12, 37, 8, 644 * 1000000), 219.0);
        FlightDto flightDto4 = new FlightDto("4", "op2", "New York", "London", "2024-12-12", 2, LocalDateTime.of(2024, 12, 12, 12, 37, 8, 644 * 1000000), LocalDateTime.of(2024, 12, 12, 12, 37, 8, 644 * 1000000), 219.0);

        OperatorDto operatorDto1 = new OperatorDto("op1", "LFT5465", "Lufthansa", "CRT232JFWHJFJ8811RF");
        OperatorDto operatorDto2 = new OperatorDto("op2", "TRM899", "Tarom", "CRT232JFWHJFJ8811RF");


        List<FlightResponseDto> flights1 = Arrays.asList(
                new FlightResponseDto(flightDto1, operatorDto1),
                new FlightResponseDto(flightDto2, operatorDto1)
        );
        List<FlightResponseDto> flights2 = Arrays.asList(
                new FlightResponseDto(flightDto3, operatorDto2),
                new FlightResponseDto(flightDto4, operatorDto2)
        );

        Mockito.when(mockWebClient.get()).thenReturn(requestHeadersUriMock);
        Mockito.when(requestHeadersUriMock.uri(Mockito.anyString(), Mockito.any(Function.class))).thenReturn(requestHeadersMock);

        Mockito.when(requestHeadersMock.retrieve()).thenReturn(responseSpecMock);
        Mockito.when(responseSpecMock.bodyToFlux(FlightResponseDto.class))
                .thenReturn(Flux.fromIterable(flights1))
                .thenReturn(Flux.fromIterable(flights2));

        Flux<String> urls = Flux.just(LUFTHANSA_URL, TAROM_URL);

        // Verifică rezultatele fluxului returnat de requestFlightsFromOperators
        StepVerifier.create(flightSearchService.requestFlightsFromOperators(urls, "New York", "London", "2024-12-12", "2024-12-14"))
                .expectNext(flights1.get(0))
                .expectNext(flights1.get(1))
                .expectNext(flights2.get(0))
                .expectNext(flights2.get(1))
                .verifyComplete();
    }

    @Test
    public void testBaseUrlDetermination() {
        String inputUrl = "https://www.lufthansa.com/api";
        String expectedUrl = "http://localhost:8084/lufthansa";

        String actualUrl = flightSearchService.determineBaseUrl(inputUrl);

        Assertions.assertEquals(expectedUrl, actualUrl);
    }
}
