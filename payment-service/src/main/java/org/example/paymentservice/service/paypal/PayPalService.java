package org.example.paymentservice.service.paypal;

import org.example.paymentservice.model.paypal.CompletedOrder;
import org.example.paymentservice.model.paypal.PaymentOrder;
import reactor.core.publisher.Mono;

public interface PayPalService {
    Mono<PaymentOrder> createPayment(Double totalAmount, String paymentId, String bookingId);

    Mono<CompletedOrder> completePayment(String token);

}