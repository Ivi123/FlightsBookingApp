package org.example.paymentservice.controller.paypal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class PaymentController {

    @GetMapping("/successPay")
    public Mono<String> paymentSuccess() {
        return Mono.just("successPayPal");
    }

    @GetMapping(value = "/errorPay")
    public Mono<String> paymentCancel() {
        return Mono.just("errorPayPal");
    }
}
