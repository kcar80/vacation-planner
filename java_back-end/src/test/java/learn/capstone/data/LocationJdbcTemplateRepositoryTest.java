package learn.capstone.data;

import learn.capstone.models.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class LocationJdbcTemplateRepositoryTest {

    final static int NEXT_ID = 7;

    @Autowired
    LocationJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll() {
        List<Location> locations = repository.findAll();
        assertNotNull(locations);
        assertTrue(locations.size() >= 6 && locations.size() <= 10);
    }

    @Test
    void shouldFindById() {
        Location location = repository.findById(1);
        assertEquals(1, location.getLocationId());
    }

    @Test
    void shouldAdd() {
        Location location = makeLocation();
        Location actual = repository.add(location);
        assertNotNull(actual);
        assertEquals(NEXT_ID, actual.getLocationId());
    }

    @Test
    void shouldUpdate() {
        Location location = makeLocation();
        location.setLocationId(1);
        assertTrue(repository.update(location));
        location.setLocationId(13);
        assertFalse(repository.update(location));
    }

    @Test
    void shouldDelete() {
        assertTrue(repository.deleteById(2));
        assertFalse(repository.deleteById(2));
    }

    private Location makeLocation() {
        Location location = new Location();
        location.setDescription("Test Location");
        location.setLatitude(10);
        location.setLongitude(10);
        return location;
    }
}
