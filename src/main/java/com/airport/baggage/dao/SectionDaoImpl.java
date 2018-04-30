package com.airport.baggage.dao;

import com.airport.baggage.util.Constants;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SectionDaoImpl implements SectionDao{

    private Map<String, Integer> sections = new HashMap<>();
    private String currentSection;

    public SectionDaoImpl (){
        sections.put(Constants.CONNECTIONS, Constants.CONNECTIONS_INPUT_LENGTH );
        sections.put(Constants.DEPARTURES,Constants.DEPARTURES_INPUT_LENGTH );
        sections.put(Constants.BAGS, Constants.BAGS_INPUT_LENGTH );
    }

    @Override
    public Set<String> getSectionNames(){
        return this.sections.keySet();
    }

    @Override
    public String getCurrentSection(){
        return this.currentSection;
    }

    @Override
    public void setCurrentSection(String section){
        this.currentSection = section;
    }

}
