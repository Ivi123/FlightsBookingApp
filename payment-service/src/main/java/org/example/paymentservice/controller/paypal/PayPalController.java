package org.example.paymentservice.controller.paypal;

import org.example.paymentservice.model.paypal.CompletedOrder;
import org.example.paymentservice.model.paypal.PaymentOrder;
import org.example.paymentservice.service.paypal.PayPalService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/paypal")
public class PayPalController {

    private final PayPalService payPalService;

    public PayPalController(PayPalService payPalService) {
        this.payPalService = payPalService;
    }

    @PostMapping("/init")
    public Mono<PaymentOrder> createPayment(@RequestParam(name = "totalAmount") Double totalAmount) {
        return payPalService.createPayment(totalAmount);
    }

    @PostMapping("/capture")
    public Mono<CompletedOrder> completePayment(@RequestParam("token") String token) {
        return payPalService.completePayment(token);
    }

}
