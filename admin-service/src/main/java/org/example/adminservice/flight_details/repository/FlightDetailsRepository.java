package org.example.adminservice.flight_details.repository;
import org.example.adminservice.flight_details.model.FlightDetails;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FlightDetailsRepository extends MongoRepository<FlightDetails, String> {
}
