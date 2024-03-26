package org.example.adminservice.flight.repository;

import org.example.adminservice.flight.model.Flight;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FlightRepository extends MongoRepository<Flight, String> {
}

