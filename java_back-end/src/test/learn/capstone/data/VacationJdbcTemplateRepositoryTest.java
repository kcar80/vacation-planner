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


    private Vacation makeVacation(){
        Vacation vacation = new Vacation();
        vacation.setStartDate(LocalDate.of(2020, 5,5));
        vacation.setEndDate(LocalDate.of(2020,5,10));
        vacation.setDescription("test");
        vacation.setLeisureLevel(2);
        vacation.setLocationId(4);
        return vacation;
    }



}