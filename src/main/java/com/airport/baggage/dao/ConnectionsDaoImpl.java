package com.airport.baggage.dao;

import com.airport.baggage.model.Gate;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ConnectionsDaoImpl implements ConnectionsDao{

    private static Logger logger = Logger.getLogger(ConnectionsDaoImpl.class.getName());

    private static Map<String, LinkedHashSet<Gate>> conveyorSystemMap = new HashMap<>();

    public ConnectionsDaoImpl(Level logLevel) {
        logger.setLevel(logLevel);
    }

    /**
     * Get connections for gate
     *
     * @param gate
     * @return
     */
    public Set<Gate> getConnections(String gate) {
        LinkedHashSet<Gate> connectingGates = null;
        if (conveyorSystemMap.containsKey(gate)) {
            connectingGates = conveyorSystemMap.get(gate);
        } else {
            connectingGates = new LinkedHashSet<>();
            conveyorSystemMap.put(gate, connectingGates);
        }
        return connectingGates;
    }

    /**
     * Get adjacent gates List for the gate
     *
     * @param gate
     * @return
     */
    public List<Gate> getAdjacentGates(String gate) {
        LinkedHashSet<Gate> adjacent = conveyorSystemMap.get(gate);

        if(adjacent != null && !adjacent.isEmpty())
            return adjacent.stream().collect(Collectors.toList());
        return new LinkedList<>();
    }

    /**
     * Create two way Connection between gates and record travel time
     *
     * @param values
     */
    public void addTwoWayConnection(final List<String> values) {
        int travelTime = Integer.parseInt(values.get(2));
        final LinkedHashSet<Gate> connectingGates1 = (LinkedHashSet<Gate>)getConnections(values.get(0));
        final LinkedHashSet<Gate> connectingGates2 = (LinkedHashSet<Gate>)getConnections(values.get(1));
        connectingGates1.add(new Gate(values.get(1), travelTime ));
        connectingGates2.add(new Gate(values.get(0), travelTime ));
        if(logger.isLoggable(Level.FINE))
            logger.log(Level.FINE, "Connection added between gates[" + values.get(0) + "," + values.get(1) + "-" + travelTime + "]");
    }
}
