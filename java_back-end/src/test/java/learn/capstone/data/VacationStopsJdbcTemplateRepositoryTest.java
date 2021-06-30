package learn.capstone.data;

import learn.capstone.models.Location;
import learn.capstone.models.VacationStops;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class VacationStopsJdbcTemplateRepositoryTest {

    @Autowired
    VacationStopsJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldAdd() {
        VacationStops vacationStops = makeVacationStops();
        assertTrue(repository.add(vacationStops));

        try {
            repository.add(vacationStops); // must fail
            fail("cannot add an location to a vacation twice.");
        } catch (DataAccessException ex) {
            // this is expected.
        }
    }

    @Test
    void shouldUpdate() {
        VacationStops vacationStops = makeVacationStops();
        vacationStops.setIdentifier("107"); // avoid duplicates
        vacationStops.getLocation().setLocationId(1);
        assertTrue(repository.update(vacationStops));

        vacationStops.setVacationId(12);
        assertFalse(repository.update(vacationStops));
    }

    @Test
    void shouldDelete() {
        assertTrue(repository.deleteByKey(1, 7));
        assertFalse(repository.deleteByKey(1, 7));
    }

    VacationStops makeVacationStops() {
        VacationStops vacationStops = new VacationStops();
        vacationStops.setVacationId(1);

        Location location = new Location();
        location.setLocationId(7);
        location.setDescription("Test");
        vacationStops.setLocation(location);

        vacationStops.setStartDate(LocalDate.of(2010, 6, 19));
        vacationStops.setEndDate(LocalDate.of(2010, 6, 19));
        vacationStops.setIdentifier("1-7");

        return vacationStops;
    }
}
