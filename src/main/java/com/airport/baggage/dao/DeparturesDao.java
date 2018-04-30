package com.airport.baggage.dao;

public interface DeparturesDao {

    boolean addFlight(String string, String string2, String string3, String string4);

    String findDestination(String string);
}
