package com.airport.baggage;

import com.airport.baggage.service.BaggageService;
import com.airport.baggage.service.BaggageServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class BaggageRouteTest {

    BaggageService baggageService;

    /**
     * Successful Flow test data available in "Input1.txt"
     */
    @Test
    public void test1Successflow() {
        Stream<String> stream = null;

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

    /**
     * Successful Flow test data available in "Input2.txt"
     */
    @Test
    public void test2Exceptionsflow() {
        Stream<String> stream = null;

        try {
            stream = Files.lines(Paths.get(ClassLoader.getSystemResource("TestInput2.txt").toURI()));
            stream.map(line -> line.split("\n+")) // Stream<String[]>
                    .flatMap(Arrays::stream) // Stream<String>
                    .filter(data -> !data.trim().isEmpty()).forEach(string -> baggageService.insertSystemData(string));

            String actualresult = baggageService.findBaggageRoute();
            String expectedResult = new String(
                    Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("TestOutput2.txt").toURI())));
            expectedResult = expectedResult.replaceAll("\r", "");

            assertNotNull(actualresult, "No result returned");
            assertEquals(expectedResult, actualresult);
        } catch (Exception e) {
            assertTrue( e.getMessage(),false);
        } finally {
            if(stream !=  null)
                stream.close();
        }
    }

    @Before
    public void beforeExecution() {
        this.baggageService = new BaggageServiceImpl(Level.FINE);
    }
}
