package com.airport.baggage.service;

import java.util.List;
import java.util.Set;

public interface InputValidationService {

    boolean validateConnection(List<String> values);

    boolean validateDeparture(List<String> values);

    void addDuplicateFlightException(List<String> values);

    boolean validateBaggageDestination(List<String> values);

    boolean validateBaggageGates(List<String> values);

    boolean isSectionLine(String inputString);

    void addExceptionDetail(String string);

    List<String> getExceptions();

    String validateSection(String currentSection, String inputString, Set<String> keySet);

}
