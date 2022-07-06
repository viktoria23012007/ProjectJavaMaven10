package ru.netology.manager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.netology.domain.Flight;
import ru.netology.domain.FlightByDurationAsComparator;
import ru.netology.exception.AlreadyExistsException;
import ru.netology.repository.FlightRepository;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class FlightManagerTest {
    @Mock
    private FlightRepository repository;
    @InjectMocks
    private FlightManager manager;
    private Flight flight1 = new Flight(1, 6_000, "VKO", "HND", 600);
    private Flight flight2 = new Flight(2, 7_000, "VKO", "HND", 500);
    private Flight flight3 = new Flight(3, 5_000, "VKO", "HND", 700);
    private Flight flight4 = new Flight(4, 2_000, "VKO", "LAX", 150);
    private Flight flight5 = new Flight(5, 1_000, "VKO", "LAX", 100);
    private Flight flight6 = new Flight(6, 1_000, "VKO", "LAX", 150);
    private Flight[] returned = new Flight[]{flight1, flight2, flight3, flight4, flight5, flight6};

    private FlightByDurationAsComparator comparator = new FlightByDurationAsComparator();

    @Test
    public void shouldAdd() throws AlreadyExistsException {
        manager = new FlightManager(new FlightRepository());
        manager.add(flight1);

        Flight[] actual = manager.getRepository().findAll();
        Flight[] expected = new Flight[]{flight1};
        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldFindAllAndSortByPrice() {
        doReturn(returned).when(repository).findAll();

        Flight[] actual = manager.findAll("VKO", "HND");
        Flight[] expected = new Flight[]{flight3, flight1, flight2};
        assertArrayEquals(expected, actual);
        verify(repository).findAll();
    }

    @Test
    public void shouldFindAllAndSortByPriceWithEqualValues() {
        doReturn(returned).when(repository).findAll();

        Flight[] actual = manager.findAll("VKO", "LAX");
        Flight[] expected = new Flight[]{flight5, flight6, flight4};
        // Ожидаем увидеть равные по цене билеты в том же порядке
        assertArrayEquals(expected, actual);
        verify(repository).findAll();
    }

    @Test
    public void shouldFindAllAndReturnEmptyArray() {
        doReturn(returned).when(repository).findAll();

        Flight[] actual = manager.findAll("HND", "LAX");
        Flight[] expected = new Flight[0];
        assertArrayEquals(expected, actual);
        verify(repository).findAll();
    }

    @Test
    public void shouldFindAllAndSortByDuration() {
        doReturn(returned).when(repository).findAll();

        Flight[] actual = manager.findAll("VKO", "HND", comparator);
        Flight[] expected = new Flight[]{flight2, flight1, flight3};
        assertArrayEquals(expected, actual);
        verify(repository).findAll();
    }

    @Test
    public void shouldFindAllAndSortByDurationWithEqualValues() {
        doReturn(returned).when(repository).findAll();

        Flight[] actual = manager.findAll("VKO", "LAX", comparator);
        Flight[] expected = new Flight[]{flight5, flight4, flight6};
        // Ожидаем увидеть равные по длительности перелета билеты в том же порядке
        assertArrayEquals(expected, actual);
        verify(repository).findAll();
    }
}