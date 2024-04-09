package org.example.paymentservice.model.stripe;

import jakarta.validation.constraints.*;

public class WebRequest {
    @NotNull
    @Min(4)
    private Long amount;
    @Email
    private String email;
    @NotBlank
    @Size(min = 5, max = 200)
    private String bookingId;
    @NotBlank
    private String paymentId;

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
