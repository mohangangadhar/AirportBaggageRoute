package com.airport.baggage.dao;

import com.airport.baggage.model.Baggage;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BagsDaoImpl implements BagsDao{

    private static Logger logger = Logger.getLogger(BagsDaoImpl.class.getName());

    public BagsDaoImpl(Level logLevel) {
        logger.setLevel(logLevel);
    }

    private List<Baggage> baggageList = new LinkedList<>();

    public List<Baggage> getBaggageList() {
        return baggageList;
    }


    public void addBaggageDetail(final String bagNumber, final String departureGate, final String arrivalGate) {
        getBaggageList().add(new Baggage(bagNumber, departureGate, arrivalGate));
        if(logger.isLoggable(Level.FINE))
            logger.log(Level.FINE, "BaggageDetail added for bagNumber[" + bagNumber + "] departureGate[" + departureGate + "] arrivalGate[" + arrivalGate + "]");
    }
}
