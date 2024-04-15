package org.example.paymentservice.controller.paypal;

import org.example.paymentservice.model.paypal.CompletedOrder;
import org.example.paymentservice.model.paypal.PaymentOrder;
import org.example.paymentservice.service.paypal.PayPalServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/paypal")
public class PayPalController {

    private static final Logger LOG = LoggerFactory.getLogger(PayPalController.class);
    private final PayPalServiceImpl payPalService;

    public PayPalController(PayPalServiceImpl payPalService) {
        this.payPalService = payPalService;
    }

    @PostMapping("/init")
    public Mono<PaymentOrder> createPayment(@RequestParam(name = "totalAmount") Double totalAmount,
                                            @RequestParam(value = "paymentId", required = false, defaultValue = "") String paymentId,
                                            @RequestParam(value = "bookingId", required = false, defaultValue = "") String bookingId,
                                            @RequestParam(value = "email", required = false, defaultValue = "") String email) {
        LOG.info(paymentId);
        LOG.info(bookingId);
        LOG.info(email);
        LOG.info(totalAmount.toString());
        return payPalService.createPayment(totalAmount, paymentId, bookingId);
    }

    @PostMapping("/capture")
    public Mono<CompletedOrder> completePayment(@RequestParam("token") String token) {
        return payPalService.completePayment(token);
    }

}
