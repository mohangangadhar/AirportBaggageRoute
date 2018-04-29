package com.airport.baggage.service;

public interface InputValidationService {

    public boolean validateGate(String gate);
    public boolean validateTripTime(Integer tripTime);
    public boolean validateSection(String section);
}
