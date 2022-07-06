package ru.netology.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlightTest {
    private Flight flight = new Flight(1, 70_000, "VKO", "HND", 567);

    @ParameterizedTest
    @CsvSource({
            "Upper case, VKO, HND, true",
            "Lower case, vko, hnd, true",
            "Match from, No Match to, VKO, VKO, false",
            "Match to, No Match from, HND, HND, false"
    })
    public void shouldMatchesFlights(String name, String from, String to, boolean expected) {
        boolean actual = flight.matches(from, to);
        assertEquals(actual, expected);
    }

    @ParameterizedTest
    @CsvSource({
            "Lower Price, 69999, 1",
            "Higher Price, 70001, -1",
            "Equal Price, 70000, 0"
    })
    public void shouldCompareTo(String name, int price, int expected) {
        Flight testFlight = new Flight(0, price, "", "", 0);
        int actual = flight.compareTo(testFlight);
        assertEquals(actual, expected);
    }
}
