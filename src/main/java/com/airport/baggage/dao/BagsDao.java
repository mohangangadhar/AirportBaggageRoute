package com.airport.baggage.dao;

import com.airport.baggage.model.Baggage;

import java.util.List;

public interface BagsDao {

    void addBaggageDetail(String string, String string2, String arrivalGate);

    List<Baggage> getBaggageList();
}
