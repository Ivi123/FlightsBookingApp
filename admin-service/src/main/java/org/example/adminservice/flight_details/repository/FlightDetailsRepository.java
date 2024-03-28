package org.example.adminservice.flight_details.repository;
import org.example.adminservice.flight_details.model.FlightDetails;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface FlightDetailsRepository extends MongoRepository<FlightDetails, String> {
    Optional<FlightDetails> findByFlightId(String id);
}
