package ru.netology.domain;

import java.util.Comparator;

public class FlightByDurationAsComparator implements Comparator<Flight> {
    public int compare(Flight f1, Flight f2) {
        return f1.getDuration() - f2.getDuration();
    }
}
