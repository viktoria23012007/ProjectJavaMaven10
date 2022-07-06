package ru.netology.repository;

import ru.netology.domain.Flight;
import ru.netology.exception.NotFoundException;
import ru.netology.exception.AlreadyExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class FlightRepositoryTest {

    private Flight flight1 = new Flight(1, 70_000, "VKO", "HND", 567);
    private Flight flight2 = new Flight(2, 90_000, "SVO", "LAX", 765);
    private Flight flight3 = new Flight(3, 50_000, "DME", "LHR", 255);
    private Flight[] flights = new Flight[]{flight1, flight2};
    private FlightRepository repository = new FlightRepository(flights);

    @Test
    public void shouldFindAll() {
        Flight[] actual = repository.findAll();
        Flight[] expected = flights;
        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSave() throws AlreadyExistsException {
        repository.save(flight3);
        Flight[] actual = repository.findAll();
        Flight[] expected = new Flight[]{flight1, flight2, flight3};
        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldTrowAlreadyExistsException() {
        Assertions.assertThrows(AlreadyExistsException.class, () -> repository.save(flight1));
    }

    @Test
    public void shouldRemoveById() {
        repository.removeById(2);
        Flight[] actual = repository.findAll();
        Flight[] expected = new Flight[]{flight1};
        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldTrowNotFoundException() {
        Assertions.assertThrows(NotFoundException.class, () -> repository.removeById(4));
    }

    @Test
    public void shouldSortByPrice() {
        Flight[] expected = new Flight[]{flight3, flight1, flight2};
        Flight[] actual = new Flight[]{flight1, flight2, flight3};
        Arrays.sort(actual);
        assertArrayEquals(expected, actual);
    }
}
