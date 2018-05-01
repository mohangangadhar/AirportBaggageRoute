package com.airport.baggage.service;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.airport.baggage.dao.*;
import com.airport.baggage.dao.SectionDaoImpl;
import com.airport.baggage.model.Baggage;
import com.airport.baggage.model.Gate;
import com.airport.baggage.util.Constants;

public class BaggageServiceImpl implements BaggageService{
    private static Logger logger = Logger.getLogger(BaggageServiceImpl.class.getName());

    private DeparturesDao departuresDao;
    private ConnectionsDao connectionsDao;
    private BagsDao baggageDao;
    private SectionDao sectionsDao;
    private InputValidationService validationService;

    public BaggageServiceImpl(Level logLevel) {
        logger.setLevel(logLevel);
        this.sectionsDao = new SectionDaoImpl();
        this.departuresDao = new DeparturesDaoImpl(logLevel);
        this.connectionsDao = new ConnectionsDaoImpl(logLevel);
        this.baggageDao = new BagsDaoImpl(logLevel);
        this.validationService = new InputValidationServiceImpl(logLevel);
    }

    /**
     * Search path between departure and arrival
     *
     * @param baggageSystem
     * @param baggageDetail
     * @param visited
     */
    private void searchRoute(Baggage baggageDetail, LinkedList<String> visited) {

        /* Get adjacent gates List based on the last gate visited */
        List<Gate> gates = connectionsDao.getAdjacentGates(visited.getLast());

        /* Get Gate if the arrivalGate is found */
        Optional<Gate> tempGate = gates.stream().filter(gate -> gate.name.equalsIgnoreCase(baggageDetail.getArrivalGate()))
                .findFirst();

        /* if arrival gate is present set the connection for the baggage */
        if (tempGate.isPresent()) {
            visited.add(tempGate.get().name);
            logger.log(Level.INFO, "END of Search Destination Path Found:"+ visited);
            baggageDetail.updateConnections(visited);
            calculateTravelTime((LinkedList<String>)baggageDetail.cloneConnections(),baggageDetail);
            visited.removeLast();
            return;
        }

        /* If destination is not found search and iterate the unvisited gates */
        gates.stream().filter(gate -> (!baggageDetail.isPathFound() && !visited.contains(gate.name)
                && !gate.name.equalsIgnoreCase(baggageDetail.getArrivalGate()))).forEach(gate -> {
            visited.addLast(gate.name);
            logger.log(Level.INFO, "Gates visited" + visited + " Current Gate[" + gate.name + "]");
            searchRoute(baggageDetail, visited);
            visited.removeLast();
        });
    }

    /**
     * Validate & Create Flight Connection
     *
     * @param currentSection
     * @param values
     * @return
     */
    private boolean createConnection(final List<String> values) {
        if (Constants.CONNECTIONS.equalsIgnoreCase(sectionsDao.getCurrentSection())
                && validationService.validateConnection(values)) {
            connectionsDao.addTwoWayConnection(values);
            return true;
        }
        return false;
    }

    /**
     * Validate and Create Flight Departure
     *
     * @param currentSection
     * @param values
     * @return
     */
    private boolean createDeparture(final List<String> values) {
        if (Constants.DEPARTURES.equalsIgnoreCase(sectionsDao.getCurrentSection())
                && validationService.validateDeparture(values)) {
            if(departuresDao.addFlight(values.get(0), values.get(1),values.get(2), values.get(3))) {
                return true;
            }
            validationService.addDuplicateFlightException(values);
            return true;
        }
        return false;
    }

    /**
     * Validate & Create Baggage details
     *
     * @param currentSection
     * @param values
     * @return
     */
    private boolean createBaggage(final List<String> values) {
        if (Constants.BAGS.equalsIgnoreCase(sectionsDao.getCurrentSection()) && 	validationService.validateBaggageGates(values)) {
            String arrivalGate = departuresDao.findDestination(values.get(2));
            values.set(2, arrivalGate);
            if(validationService.validateBaggageDestination(values)) {
                baggageDao.addBaggageDetail(values.get(0), values.get(1), arrivalGate);
                return true;
            }
        }
        return false;
    }

    /**
     * Calculate travel time based on connections
     *
     * @param connections
     * @param bag
     */
    private void calculateTravelTime(LinkedList<String> connections, Baggage bag) {
        if (connections.size() < 2) {
            return;
        }
        final LinkedHashSet<Gate> connectingGates = (LinkedHashSet<Gate>)connectionsDao.getConnections(connections.getFirst());
        connections.removeFirst();
        Optional<Gate> value = connectingGates.stream()
                .filter(gate -> gate.name.equalsIgnoreCase(connections.getFirst())).findFirst();
        if(value.isPresent()) {
            int travelTime = bag.updateTravelTime( value.get().travelTime );
            if(logger.isLoggable(Level.FINE))
                logger.log(Level.FINE, bag.getDepartureGate() + " -> " + connections.getFirst() + " ---> totalTraveltime:" + travelTime);
        }
        calculateTravelTime(connections, bag);
    }

    /**
     * Insert Sections, Connections, Flight Departures and Bagagge Data
     */
    public void insertSystemData(String inputString) {
        if (validationService.isSectionLine(inputString))
            sectionsDao.setCurrentSection(
                    validationService.validateSection(sectionsDao.getCurrentSection(), inputString, sectionsDao.getSectionNames()));
        else {
            Optional<String> section = sectionsDao.getSectionNames().stream()
                    .filter(value -> value.equalsIgnoreCase(sectionsDao.getCurrentSection())).findFirst();
            if (section.isPresent()) {
                List<String> values = Pattern.compile(Constants.SPLIT_VALUE).splitAsStream(inputString).filter(str->!str.isEmpty()).collect(Collectors.toList());
                if( createConnection(values) || createDeparture(values) || createBaggage(values))
                    return;
                return;
            }
            validationService.addExceptionDetail(Constants.NOSECTION + " to process Data["+inputString+"];");
        }
    }

    /**
     * Find Baggage route for all the baggage's in the system and calculate travel
     * path
     */
    public String findBaggageRoute() {
        StringBuilder result = new StringBuilder();
        validationService.getExceptions().forEach(str->result.append(str).append("\n"));
        if(validationService.getExceptions().isEmpty()) {
            logger.log(Level.INFO, "No Exceptions found while inserting data;");

            baggageDao.getBaggageList().stream().forEach(bag -> {
                LinkedList<String> visited = new LinkedList<>();
                visited.add(bag.getDepartureGate());
                searchRoute(bag, visited);
                result.append(bag.toString()).append("\n");
            });
        }
        return result.toString();
    }

}
