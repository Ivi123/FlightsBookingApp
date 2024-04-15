package org.example.paymentservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Document
public class Payment {
    @Id
    private String id;
    private String bookingId;
    private double price;
    private String cardNumber;
    private String cardHolderName;
    private String expirationMonth;
    private String expirationYear;
    private int cvv;
    private String currency;
    private String status;
    private LocalDateTime creationTime;
    private LocalDateTime expirationTime;

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    private String userEmail;

    public double getPrice() {
        return price;
    }


    public String getCardNumber() {
        return cardNumber;
    }


    public String getCardHolderName() {
        return cardHolderName;
    }


    public String getExpirationMonth() {
        return expirationMonth;
    }


    public String getExpirationYear() {
        return expirationYear;
    }


    public int getCvv() {
        return cvv;
    }


    public String getCurrency() {
        return currency;
    }



    public Payment(String bookingId, double price,
                   String cardNumber, String cardHolderName, String expirationMonth,
                   String expirationYear, int cvv, String currency, String status) {
        this.bookingId = bookingId;
        this.price = price;
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
        this.cvv = cvv;
        this.currency = currency;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id='" + id + '\'' +
                ", bookingId='" + bookingId + '\'' +
                ", price=" + price +
                ", cardNumber='" + cardNumber + '\'' +
                ", cardHolderName='" + cardHolderName + '\'' +
                ", expirationMonth='" + expirationMonth + '\'' +
                ", expirationYear='" + expirationYear + '\'' +
                ", cvv=" + cvv +
                ", currency='" + currency + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(LocalDateTime expirationTime) {
        this.expirationTime = expirationTime;
    }
}
