package com.airport.baggage;

import com.airport.baggage.service.BaggageServiceImpl;

import java.util.Scanner;
import java.util.logging.Level;

public class BaggageRoute {

    public static void main(String[] args) {

        BaggageServiceImpl baggageService = new BaggageServiceImpl(Level.OFF);
        Scanner input = null;
        String inputString = null;
        input = new Scanner(System.in);
        while (!(inputString = input.nextLine().trim()).isEmpty())
            baggageService.insertSystemData(inputString);

        System.out.println(baggageService.findBaggageRoute());
        input.close();

    }
}
