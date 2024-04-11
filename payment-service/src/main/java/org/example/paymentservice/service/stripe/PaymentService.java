package org.example.paymentservice.service.stripe;

import org.example.paymentservice.model.stripe.WebRequest;
import org.example.paymentservice.model.stripe.WebResponse;
import reactor.core.publisher.Mono;

public interface PaymentService {
    public Mono<WebResponse> processPaymentRequest(WebRequest request);

}
