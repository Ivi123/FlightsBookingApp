package org.example.adminservice.flight_details.exception;

public class FlightDetailsNotFoundException extends RuntimeException {
    public FlightDetailsNotFoundException(String id) {
        super("Flight details with id: " + id + " not found!");
    }
}
