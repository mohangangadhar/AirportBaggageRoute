package com.airport.baggage.model;

import java.util.LinkedList;

public class Baggage {
    private String bagName;
    private String departureGate;
    private String arrivalGate;
    private boolean isPathFound;
    private Integer tripTime;
    private LinkedList<String> connections;

    public Baggage(String bagName, String departureGate, String arrivalGate){
        this.bagName = bagName;
        this.departureGate = departureGate;
        this.arrivalGate = arrivalGate;
        this.connections = new LinkedList<>();
    }

    public void addConnection(String connection){
        this.connections.add(connection);
    }

    // Increment trip time when a new connection is added
    public Integer addTripTime(Integer tripTime) {
        return this.tripTime += tripTime;
    }

    // Contains true if path is found
    public boolean isPathFound(){
        return isPathFound;
    }
    

}
