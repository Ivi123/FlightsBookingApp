package org.example.paymentservice.controller.stripe;

import org.example.paymentservice.model.stripe.WebRequest;
import org.example.paymentservice.model.stripe.WebResponse;
import org.example.paymentservice.service.stripe.StripeServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Controller class for handling payment intents.
 */
@RestController
public class PaymentIntentController {

    private final StripeServiceImpl paymentService;

    public PaymentIntentController(StripeServiceImpl paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create-payment-intent")
    public Mono<WebResponse> createPaymentIntent(@RequestBody WebRequest request) {
        return paymentService.processPaymentRequest(request);
    }
}
