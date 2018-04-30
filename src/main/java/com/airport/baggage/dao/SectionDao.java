package com.airport.baggage.dao;

import java.util.Set;

public interface SectionDao {

    String getCurrentSection();

    Set<String> getSectionNames();

    void setCurrentSection(String validateSection);

}


