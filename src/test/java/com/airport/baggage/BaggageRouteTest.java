package com.airport.baggage;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.stream.Stream;

import org.junit.Test;

import com.airport.baggage.service.BaggageService;
import com.airport.baggage.service.BaggageServiceImpl;

public class BaggageRouteTest {

    @Test
    public void test1Successflow() {
        Stream<String> stream = null;
        BaggageService baggageService = new BaggageServiceImpl(Level.FINE);

        try {
            stream = Files.lines(Paths.get(ClassLoader.getSystemResource("TestInput1.txt").toURI()));

            stream.map(line -> line.split("\n+")) // Stream<String[]>
                    .flatMap(Arrays::stream) // Stream<String>
                    .filter(data -> !data.trim().isEmpty()).forEach(baggageService::insertSystemData);

            String actualresult = baggageService.findBaggageRoute();
            String expectedResult = new String(
                    Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("TestOutput1.txt").toURI())));
            expectedResult = expectedResult.replaceAll("\r", "");

            assertNotNull(actualresult, "No result returned");
            assertEquals(expectedResult, actualresult);
        } catch (Exception e) {
            assertTrue(e.getMessage(),false);
        } finally {
            if(stream !=  null)
                stream.close();
        }

    }
}
