package main.dto;

import java.time.LocalDateTime;
import java.util.List;

public class BookingDTO {
    /**
     * for avro schema
     * bookingId
     * flightDetails - all fields separately
     * numberOfPassengers - to be calculated from counting the list of passengers at POST time
     */

    private String bookingId;
    private String userId;
    private PaymentDetailsDTO paymentDetails;
    private List<PassengerDetailsDTO> passengerDetails;
    private FlightDetailsDTO flightDetails;
    private LocalDateTime createdAt;

    private String status;

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public void setNumberOfPassengers(Integer numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }

    private LocalDateTime expiresAt;

    private LocalDateTime updatedAt;

    private Integer numberOfPassengers;

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public PaymentDetailsDTO getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(PaymentDetailsDTO paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public List<PassengerDetailsDTO> getPassengerDetails() {
        return passengerDetails;
    }

    public void setPassengerDetails(List<PassengerDetailsDTO> passengerDetails) {
        this.passengerDetails = passengerDetails;
    }

    public FlightDetailsDTO getFlightDetails() {
        return flightDetails;
    }

    public void setFlightDetails(FlightDetailsDTO flightDetails) {
        this.flightDetails = flightDetails;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}