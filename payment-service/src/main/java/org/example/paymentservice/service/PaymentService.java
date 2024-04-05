package org.example.paymentservice.service;

import org.example.paymentservice.model.WebRequest;
import org.example.paymentservice.model.WebResponse;
import reactor.core.publisher.Mono;

public interface PaymentService {
    public Mono<WebResponse> processPaymentRequest(WebRequest request);

}
