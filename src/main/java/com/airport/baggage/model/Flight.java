package com.airport.baggage.model;

public class Flight {
    public final String flightId;
    public final String flightGate;
    public final String destination;
    public final String flightTime;

    public Flight(String flightId, String flightGate, String destination, String flightTime) {
        this.flightId = flightId;
        this.flightGate = flightGate;
        this.destination = destination;
        this.flightTime = flightTime;
    }

}
