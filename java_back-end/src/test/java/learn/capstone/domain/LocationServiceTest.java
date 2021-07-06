package learn.capstone.domain;

import learn.capstone.data.LocationRepository;
import learn.capstone.models.Location;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class LocationServiceTest {

    @Autowired
    LocationService service;

    @MockBean
    LocationRepository repository;

    @Test
    void shouldFindById() {
        Location location = makeLocation();
        when(repository.findById(1)).thenReturn(location);
        Location actual = service.findById(1);
        assertEquals(location, actual);
    }

    @Test
    void shouldNotAddWhenInvalid() {
        Location location = makeLocation();
        location.setLocationId(0);
        location.setDescription(null);
        Result<Location> result = service.add(location);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldAddWhenValid() {
        Location location = makeLocation();
        Location local = makeLocation();
        local.setLocationId(0);

        when(repository.add(local)).thenReturn(location);
        Result<Location> result = service.add(local);
        assertEquals(ResultType.SUCCESS, result.getType());

        assertEquals(location, result.getPayload());
    }

    @Test
    void shouldNotUpdateInvalid() {
        Location location = makeLocation();
        Location expected = makeLocation();
        expected.setLocationId(0);
        Location local = new Location();
        local.setLocationId(1);
        local.setDescription(null);
        local.setLatitude(10);
        local.setLongitude(10);

        when(repository.add(expected)).thenReturn(location);

        when(repository.update(local)).thenReturn(false);
        Result<Location> result = service.update(local);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldUpdateValid() {
        Location location = makeLocation();
        Location expected = makeLocation();
        expected.setLocationId(0);
        Location local = new Location();
        local.setLocationId(1);
        local.setDescription("Updated Test");
        local.setLatitude(10);
        local.setLongitude(10);

        when(repository.add(expected)).thenReturn(location);

        when(repository.update(local)).thenReturn(true);
        Result<Location> result = service.update(local);
        assertEquals(ResultType.SUCCESS, result.getType());
    }

    @Test
    void shouldNotDelete() {
        Location location = makeLocation();
        Location expected = makeLocation();
        expected.setLocationId(0);

        when(repository.add(location)).thenReturn(expected);

        when(repository.deleteById(10)).thenReturn(false);
        boolean result = service.deleteById(10);
        assertFalse(result);
    }

    @Test
    void shouldDelete() {
        Location location = makeLocation();
        Location expected = makeLocation();
        expected.setLocationId(0);

        when(repository.add(location)).thenReturn(expected);

        when(repository.deleteById(1)).thenReturn(true);
        boolean result = service.deleteById(1);
        assertTrue(result);
    }

    private Location makeLocation() {
        Location location = new Location();
        location.setLocationId(1);
        location.setDescription("Test Location");
        location.setLatitude(0);
        location.setLongitude(0);
        return location;
    }
}