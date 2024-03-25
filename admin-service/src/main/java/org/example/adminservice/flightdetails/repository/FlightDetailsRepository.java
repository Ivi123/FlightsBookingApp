package org.example.adminservice.flightdetails.repository;
import org.example.adminservice.flightdetails.model.FlightDetails;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FlightDetailsRepository extends MongoRepository<FlightDetails, String> {
}
