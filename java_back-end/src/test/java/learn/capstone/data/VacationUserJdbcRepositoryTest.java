package learn.capstone.data;

import learn.capstone.models.User;
import learn.capstone.models.Vacation;
import learn.capstone.models.VacationUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

    }

    VacationUser makeVacationUser(){
        VacationUser vacationUser = new VacationUser();
        vacationUser.setVacationId(1);
        vacationUser.setIdentifier("1-4");

        User user = new User();
        user.setUserId(4);
        user.setFirstName("test");
        user.setLastName("test");
        user.setUsername("test");
        user.setPassword("test");
        vacationUser.setUser(user);
        return vacationUser;
    }

}