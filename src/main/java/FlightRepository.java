package ru.netology.repository;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import ru.netology.domain.Flight;
import ru.netology.exception.NotFoundException;
import ru.netology.exception.AlreadyExistsException;

import java.util.Arrays;

@NoArgsConstructor
@AllArgsConstructor
public class FlightRepository {
    private Flight[] flights = new Flight[0];

    public Flight[] findAll() {
        return this.flights;
    }

    public void save(Flight flight) throws AlreadyExistsException {
        int id = flight.getId();
        if (this.findById(id) != null) {
            throw new AlreadyExistsException("Flight with ID " + id + " is already exists");
        }
        int length = flights.length + 1;
        Flight[] tmp = Arrays.copyOf(flights, length);
        tmp[length - 1] = flight;
        flights = tmp;
    }

    public void removeById(int id) throws NotFoundException {
        if (this.findById(id) == null) {
            throw new NotFoundException("Flight with ID " + id + " not found");
        }
        int length = flights.length - 1;
        Flight[] tmp = new Flight[length];
        int index = 0;
        for (Flight flight : flights) {
            if (flight.getId() != id) {
                tmp[index] = flight;
                index++;
            }
        }
        flights = tmp;
    }

    public Flight findById(int id) {
        for (Flight flight : flights) {
            if (flight.getId() == id) {
                return flight;
            }
        }
        return null;
    }
}
