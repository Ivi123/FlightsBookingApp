package com.example.notificationservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "notification_index")
public class Notification {
    @Id
    private String id;
    private String message;
    private String userEmail;
    private String bookingId;
    private String paymentId;
    private Double price;
    private String currency;
    private NotificationType type;
    private String operatorId;
    private String flightId;
    private String departure;
    private String destination;
    private int numberOfSeats;
    private Double standardPrice;
    private String date;

    public Notification() {
    }

    public Notification(String id,
                        String message,
                        String userEmail,
                        String bookingId,
                        String paymentId,
                        Double price,
                        String currency,
                        NotificationType type,
                        String operatorId,
                        String flightId,
                        String departure,
                        String destination,
                        int numberOfSeats,
                        Double standardPrice,
                        String date) {
        this.id = id;
        this.message = message;
        this.userEmail = userEmail;
        this.bookingId = bookingId;
        this.paymentId = paymentId;
        this.price = price;
        this.currency = currency;
        this.type = type;
        this.operatorId = operatorId;
        this.flightId = flightId;
        this.departure = departure;
        this.destination = destination;
        this.numberOfSeats = numberOfSeats;
        this.standardPrice = standardPrice;
        this.date = date;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public Double getStandardPrice() {
        return standardPrice;
    }

    public void setStandardPrice(Double standardPrice) {
        this.standardPrice = standardPrice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
