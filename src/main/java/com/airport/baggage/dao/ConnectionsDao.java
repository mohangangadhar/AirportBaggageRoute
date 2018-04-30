package com.airport.baggage.dao;

import com.airport.baggage.model.Gate;

import java.util.List;
import java.util.Set;

public interface ConnectionsDao {

    List<Gate> getAdjacentGates(String last);

    void addTwoWayConnection(List<String> values);

    Set<Gate> getConnections(String first);
}
