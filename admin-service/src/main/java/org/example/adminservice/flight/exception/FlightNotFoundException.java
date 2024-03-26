package org.example.adminservice.flight.exception;

public class FlightNotFoundException extends RuntimeException {
    public FlightNotFoundException(String id) {
        super("Flight with id: " + id + " not found!");
    }
}
