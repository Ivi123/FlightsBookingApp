package org.example.adminservice.functionality.exceptions;

public class FunctionalityNotFoundException extends RuntimeException {
    public FunctionalityNotFoundException(String id) {
        super("Functionality with id: " + id + " not found!");
    }
}