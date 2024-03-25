package org.example.adminservice.operator.exceptions;

public class OperatorNotFoundException extends RuntimeException{
    public OperatorNotFoundException(String id) {
        super("Operator with id: " + id + " not found!");
    }
}
