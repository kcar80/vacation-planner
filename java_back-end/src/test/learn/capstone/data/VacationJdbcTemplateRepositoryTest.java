package learn.capstone.data;

import learn.capstone.models.Vacation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class VacationJdbcTemplateRepositoryTest {

    final static int NEXT_ID = 7;

    @Autowired
    VacationJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup(){knownGoodState.set();}

    @Test
    void shouldFindAll(){
        List<Vacation> vacations = repository.findAll();
        assertNotNull(vacations);

        assertTrue(vacations.size() == 6);
    }

    @Test
    void shouldFindMNTrip(){
        Vacation mn = repository.findById(1);
        assertEquals(1, mn.getVacationId());
        assertEquals("A trip to the capital of MN", mn.getDescription() );
        assertEquals(1, mn.getUsers().size());
    }



    private Vacation makeVacation(){
        Vacation vacation = new Vacation();
        vacation.setDescription("test");
        vacation.setLeisureLevel(2);

        return vacation;
    }



}