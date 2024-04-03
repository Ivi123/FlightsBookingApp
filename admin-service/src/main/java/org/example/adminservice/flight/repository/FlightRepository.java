package org.example.adminservice.flight.repository;

import org.example.adminservice.flight.dto.FlightDto;
import org.example.adminservice.flight.model.Flight;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FlightRepository extends MongoRepository<Flight, String> {
    List<FlightDto> findByDepartureAndDestinationAndDate(String departure, String destination, String date);
}

