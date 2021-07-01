package learn.capstone.domain;

import learn.capstone.data.VacationRepository;
import learn.capstone.models.Vacation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.validation.*;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class VacationServiceTest {

    @Autowired
    VacationService service;

    @MockBean
    VacationRepository vacationRepository;

    @Test
    void shouldAdd(){
        Vacation vacation = new Vacation(0, "test", 2);
        Vacation mockOut = new Vacation(4, "test", 2 );

        when(vacationRepository.add(vacation)).thenReturn(mockOut);

        Result<Vacation> actual = service.add(vacation);
        assertEquals(ResultType.SUCCESS, actual.getType());
        assertEquals(mockOut, actual.getPayload());
    }

    @Test
    void emptyVacationShouldFail(){
        Vacation vacation = new Vacation();



        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Vacation>> violations = validator.validate(vacation);

        assertEquals(2, violations.size());
    }
    @Test
    void invalidLeisureLevelShouldFail(){
        Vacation vacation = makeVacation();
        vacation.setLeisureLevel(5);


        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Vacation>> violations = validator.validate(vacation);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldNotAddWhenInvalid(){
        Vacation vacation = new Vacation();
        Result<Vacation> actual = service.add(vacation);
    }

    @Test
    void shouldUpdate(){
        Vacation vacation = makeVacation();
        vacation.setVacationId(5);

        when(vacationRepository.update(vacation)).thenReturn(true);
        Result<Vacation> actual = service.update(vacation);
        assertEquals(ResultType.SUCCESS, actual.getType());
    }

    @Test
    void shouldNotUpdateMissing(){
        Vacation vacation = makeVacation();
        vacation.setVacationId(50);

        when(vacationRepository.update(vacation)).thenReturn(false);
        Result<Vacation> actual = service.update(vacation);
        assertEquals(ResultType.NOT_FOUND, actual.getType());
    }

    @Test
    void shouldNotUpdateInvalid(){
        Vacation vacation = makeVacation();
        vacation.setVacationId(5);
        vacation.setDescription(null);

        Result<Vacation> actual = service.update(vacation);
        assertEquals(ResultType.NOT_FOUND, actual.getType());
    }

    private Vacation makeVacation(){
        Vacation vacation = new Vacation();
        vacation.setDescription("test");
        vacation.setLeisureLevel(2);

        return vacation;
    }



}