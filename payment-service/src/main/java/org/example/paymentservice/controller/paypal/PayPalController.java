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
    public Mono<PaymentOrder> createPayment(@RequestParam(name = "totalAmount") Double totalAmount,
                                            @RequestParam(value = "paymentId", required = false, defaultValue = "") String paymentId,
                                            @RequestParam(value = "bookingId", required = false, defaultValue = "") String bookingId,
                                            @RequestParam(value = "email", required = false, defaultValue = "") String email) {
        System.out.println(paymentId);
        System.out.println(bookingId);
        System.out.println(totalAmount);
        return payPalService.createPayment(totalAmount,paymentId,bookingId,email);
    }

    @PostMapping("/capture")
    public Mono<CompletedOrder> completePayment(@RequestParam("token") String token) {
        return payPalService.completePayment(token);
    }

}
