package com.example.integrationtests.dtos;

public class FlightDto {
    private String id;
    private String operatorId;
    private String departure;
    private String destination;
    private int numberOfSeats;
    private String date;

    public FlightDto(String operatorId, String departure, String destination, int numberOfSeats, String date) {
        this.operatorId = operatorId;
        this.departure = departure;
        this.destination = destination;
        this.numberOfSeats = numberOfSeats;
        this.date = date;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
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
}
