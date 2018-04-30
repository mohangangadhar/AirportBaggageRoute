package com.airport.baggage.model;

import java.util.LinkedList;
import java.util.List;

public class Baggage {
    private String bagName;

    public String getDepartureGate() {
        return departureGate;
    }

    private String departureGate;
    private boolean pathFound;
    private String arrivalGate;
    private boolean isPathFound;
    private int travelTime;
    private LinkedList<String> connections;

    public Baggage(String bagName, String departureGate, String arrivalGate){
        this.bagName = bagName;
        this.departureGate = departureGate;
        this.arrivalGate = arrivalGate;
        this.connections = new LinkedList<>();
    }

    public String getArrivalGate() {
        return arrivalGate;
    }


    // Contains true if path is found
    public boolean isPathFound(){
        return isPathFound;
    }

    public void updateConnections(List<String> connectionsFound) {
        this.connections.addAll(connectionsFound);
        this.pathFound = true;
    }

    @SuppressWarnings("unchecked")
    public List<String>  cloneConnections() {
        return  (LinkedList<String>) connections.clone();
    }

    public int updateTravelTime(int travelTime) {
        this.travelTime += travelTime;
        return this.travelTime;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(bagName);
        connections.forEach(gate -> builder.append(" " + gate));
        builder.append(" : " + travelTime);
        return builder.toString();
    }
}
