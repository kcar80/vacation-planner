package learn.capstone.data;

import learn.capstone.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UserJdbcTemplateRepositoryTest {

    @Autowired
    UserJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll() {
        List<User> all = repository.findAll();

        assertNotNull(all);
        assertTrue(all.size() >= 3);
    }

    @Test
    void shouldFindById() {
        User user = repository.findById(3);

        assertNotNull(user);
        assertEquals("Kathryn", user.getFirstName());
    }

    @Test
    void shouldAdd() {
        User user = new User(0, "Test", "User", "testuser", "password", false);

        User actual = repository.add(user);

        assertEquals(actual, user);
    }

    @Test
    void shouldUpdate() {
        User user = new User(4, "Test", "NewUser", "newusername", "newpassword", true);

        boolean success = repository.update(user);

        assertTrue(success);

        User actual = repository.findById(4);
        assertEquals("NewUser", actual.getLastName());
    }

    @Test
    void shouldDelete() {
        boolean success = repository.deleteById(1);

        assertTrue(success);

        assertNull(repository.findById(1));
    }
}