package com.airport.baggage.dao;

import com.airport.baggage.model.Flight;
import com.airport.baggage.util.Constants;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeparturesDaoImpl implements DeparturesDao{
    private static Logger logger = Logger.getLogger(DeparturesDaoImpl.class.getName());

    public DeparturesDaoImpl(Level logLevel) {
        logger.setLevel(logLevel);
    }

    private Map<String,Flight> flightDeparture = new ConcurrentHashMap<>();

    /**
     * Add Fight details to the List
     *
     * @param flightId
     * @param flightGate
     * @param destination
     * @param flightTime
     * @return
     */
    public boolean addFlight(String flightId, String flightGate, String destination, String flightTime) {
        if(flightDeparture.containsKey(flightId)) {
            return false;
        }
        flightDeparture.put(flightId,new Flight(flightId, flightGate, destination, flightTime));
        if(logger.isLoggable(Level.FINE))
            logger.log(Level.FINE,"FlightDeparture added for flightId[" + flightId + "] flightGate[" + flightGate
                    + "] destination[" + destination + "] flightTime[" + flightTime + "]");
        return true;
    }


    /**
     * Find Gate name based on flightId from FlightDeparture Data
     *
     * @param flightId
     * @return
     */
    public String findDestination(String flightId) {
        Optional<Flight> flightdata = flightDeparture.values().stream()
                .filter(flightData -> flightData.flightId.equalsIgnoreCase(flightId)
                        || flightId.equalsIgnoreCase(Constants.ARRIVAL))
                .findFirst();
        String gate = null;
        if (flightdata.isPresent())
            gate = (Constants.ARRIVAL.equalsIgnoreCase(flightId) ? Constants.BAGGAGECLAIM
                    : flightdata.get().flightGate);
        if(logger.isLoggable(Level.FINE))
            logger.log(Level.FINE,gate + " Gate Found for " + flightId);
        return gate;
    }
}
