package learn.capstone.data;

import learn.capstone.models.User;
import learn.capstone.models.Vacation;
import learn.capstone.models.VacationUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class VacationUserJdbcRepositoryTest {

    @Autowired
    VacationUserJdbcRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup(){knownGoodState.set();}

    @Test
    void shouldAdd(){
        VacationUser vacationUser = makeVacationUser();
        assertTrue(repository.add(vacationUser));

        try{
            repository.add(vacationUser);
            fail("cannot add twice");
        } catch (DataAccessException ex){

        }
    }

    @Test
    void shouldUpdate(){
        VacationUser vacationUser = makeVacationUser();
        vacationUser.setIdentifier("20-20");
        vacationUser.getUser().setUserId(1);
        assertTrue(repository.update(vacationUser));

        vacationUser.setVacationId(15);
        assertFalse(repository.update(vacationUser));

    }

    @Test
    void shouldDelete(){
        assertTrue(repository.deleteByKey(5, 1));
        assertFalse(repository.deleteByKey(5, 1));
    }

    VacationUser makeVacationUser(){
        User user = new User();
        user.setUserId(2);
        user.setFirstName("test");
        user.setLastName("test");
        user.setUsername("test");
        user.setPassword("test");
        user.setUserType(false);

        VacationUser vacationUser = new VacationUser();
        vacationUser.setVacationId(1);
        vacationUser.setIdentifier("1-2");


        vacationUser.setUser(user);
        return vacationUser;
    }

}