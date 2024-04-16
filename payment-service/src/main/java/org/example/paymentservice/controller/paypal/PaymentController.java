package org.example.paymentservice.controller.paypal;

import org.example.paymentservice.service.paypal.PayPalServiceImpl;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class PaymentController {
    private final PayPalServiceImpl payPalService;

    public PaymentController(PayPalServiceImpl payPalService) {
        this.payPalService = payPalService;
    }


    @GetMapping("/successPay")
    public Mono<String> paymentSuccess(Model model,
                                       @RequestParam(value = "token", required = false, defaultValue = "") String token) {
        return payPalService.completePayment(token)
                .flatMap(completedOrder -> Mono.just("Congratulations! Your payment was successful."))
                .doOnError(throwable -> {
                    throw new RuntimeException("Error processing payment: " + throwable.getMessage());
                });
    }

    @GetMapping(value = "/errorPay")
    public Mono<String> paymentCancel() {
        return Mono.just("errorPayPal");
    }
}
