package org.example.paymentservice.mapper.stripe;

import avro.PaymentRequest;
import org.example.paymentservice.model.stripe.Payment;

public class PaymentMapper {
    public static PaymentRequest paymentToPaymentRequest(Payment p) {
        PaymentRequest paymentRequest = new PaymentRequest();
        //
        paymentRequest.setBookingId(p.getBookingId());
        paymentRequest.setPrice(p.getPrice());
        paymentRequest.setCardHolderName(p.getCardHolderName());
        paymentRequest.setCardNumber(p.getCardNumber());
        paymentRequest.setExpirationMonth(p.getExpirationMonth());
        paymentRequest.setExpirationYear(p.getExpirationYear());
        paymentRequest.setCvv(p.getCvv());
        paymentRequest.setCurrency(p.getCurrency());
        paymentRequest.setStatus(p.getStatus());
        return paymentRequest;


    }

    public static Payment paymentRequestToPayment(PaymentRequest paymentRequest) {
        return new Payment(
                //paymentRequest.getId().toString(),
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
