package org.example.paymentservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Exception handler class for handling payment-related exceptions.
 */
@RestControllerAdvice
public class PaymentExceptionHandler {

    /**
     * Handle the exception when a payment is not found.
     *
     * @param ex The PaymentNotFoundException object.
     * @return A Mono containing the response entity with the error details.
     */
    @ExceptionHandler(PaymentNotFoundException.class)
    public Mono<ResponseEntity<Object>> handlePaymentNotFoundException(PaymentNotFoundException ex) {
        return Mono.fromCallable(() -> {
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("timestamp", LocalDateTime.now());
            body.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
        });
    }
}
