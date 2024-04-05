package org.example.paymentservice.exception;

public class PaymentNotFoundException extends RuntimeException {
    public PaymentNotFoundException(String id) {
        super("Payment with ID " + id + " not found.");
    }
}
