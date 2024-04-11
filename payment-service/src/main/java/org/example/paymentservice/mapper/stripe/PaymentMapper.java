package org.example.paymentservice.mapper.stripe;


import avro.PaymentRequest;
import org.example.paymentservice.model.stripe.Payment;

public class PaymentMapper {
    public static PaymentRequest paymentToPaymentRequest(Payment p) {
     return new PaymentRequest(
                p.getId(),
                p.getBookingId(),
                p.getPrice(),
                p.getCardNumber(),
                p.getCardHolderName(),
                p.getExpirationMonth(),
                p.getExpirationYear(),
                p.getCvv(),
                p.getCurrency(),
                p.getStatus()
        );

    }
    public static Payment paymentRequestToPayment(PaymentRequest paymentRequest) {
        return new Payment(
                paymentRequest.getId().toString(),
                paymentRequest.getBookingId().toString(),
                paymentRequest.getPrice(),
                paymentRequest.getCardNumber().toString(),
                paymentRequest.getCardHolderName().toString(),
                paymentRequest.getExpirationMonth().toString(),
                paymentRequest.getExpirationYear().toString(),
                paymentRequest.getCvv(),
                paymentRequest.getCurrency().toString(),
                paymentRequest.getStatus().toString());

    }
}
