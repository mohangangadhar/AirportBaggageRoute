package com.airport.baggage;


import com.airport.baggage.service.BaggageService;
import com.airport.baggage.service.BaggageServiceImpl;

import java.util.Scanner;
import java.util.logging.Level;

public class BaggageRoute {

    public static void main(String [] args){
        Scanner input = new Scanner(System.in);
        String inputString = null;
        BaggageService baggageService = new BaggageServiceImpl(Level.OFF);

        // Read Input
        while(!(inputString = input.nextLine().trim()).isEmpty()){
            baggageService.insertSystemData(inputString);
        }

        System.out.println(baggageService.findBaggageRoute());
        input.close();

    }
}
