package learn.capstone.domain;

import learn.capstone.data.UserRepository;
import learn.capstone.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserServiceTest {

    @Autowired
    UserService service;

    @MockBean
    UserRepository repository;

    //add
    @Test
    void shouldNotAddNull() {
        User user = null;

        Result<User> result = service.add(user);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotAddWithMissingName() {
        User user = new User(0, "", "test", "test", "test", false);

        Result<User> result = service.add(user);
        assertFalse(result.isSuccess());

        user = new User(0, null, "test", "test", "test", false);

        result = service.add(user);
        assertFalse(result.isSuccess());

        user = new User(0, "test", "", "test", "test", false);

        result = service.add(user);
        assertFalse(result.isSuccess());

        user = new User(0, "test", "", "test", "test", false);

        result = service.add(user);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotAddWithMissingUsername() {
        User user = new User(0, "test", "test", "", "test", false);

        Result<User> result = service.add(user);
        assertFalse(result.isSuccess());

        user = new User(0, "test", "test", null, "test", false);

        result = service.add(user);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotAddWithMissingPassword() {
        User user = new User(0, "test", "test", "test", "", false);

        Result<User> result = service.add(user);
        assertFalse(result.isSuccess());

        user = new User(0, "test", "test", "test", null, false);

        result = service.add(user);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotAddWithSetID() {
        User user = new User(4, "test", "test", "test", "test", false);

        Result<User> result = service.add(user);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotAddWithDuplicateUsername() {
        User user = new User(4, "test", "test", "kathryn@gmail.com", "test", false);

        Result<User> result = service.add(user);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldAdd() {
        User user = new User(0, "test", "test", "test", "test", false);

        Result<User> result = service.add(user);
        assertTrue(result.isSuccess());
    }

    //update
    @Test
    void shouldNotUpdateNull() {
        User user = new User(0, "test", "test", "test", "test", false);
        User setUser = new User(1, "test", "test", "test", "test", false);
        when(repository.add(user)).thenReturn(setUser);

        User updatedUser = null;
        when(repository.update(updatedUser)).thenReturn(false);

        Result<User> result = service.update(updatedUser);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotUpdateWithMissingName() {
        User user = new User(0, "test", "test", "test", "test", false);
        User setUser = new User(1, "test", "test", "test", "test", false);
        when(repository.add(user)).thenReturn(setUser);

        User updatedUser = new User(1, null, "updated", "test", "data", false);
        when(repository.update(updatedUser)).thenReturn(false);

        Result<User> result = service.update(updatedUser);
        assertEquals(ResultType.INVALID, result.getType());

        updatedUser = new User(1, "", "updated", "test", "", false);

        result = service.update(updatedUser);
        assertEquals(ResultType.INVALID, result.getType());

        updatedUser = new User(1, "new", null, "test", "data", false);
        when(repository.update(updatedUser)).thenReturn(false);

        result = service.update(updatedUser);
        assertEquals(ResultType.INVALID, result.getType());

        updatedUser = new User(1, "new", "", "test", "", false);

        result = service.update(updatedUser);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotUpdateWithMissingUsername() {
        User user = new User(0, "test", "test", "test", "test", false);
        User setUser = new User(1, "test", "test", "test", "test", false);
        when(repository.add(user)).thenReturn(setUser);

        User updatedUser = new User(1, "new", "updated", null, "data", false);
        when(repository.update(updatedUser)).thenReturn(false);

        Result<User> result = service.update(updatedUser);
        assertEquals(ResultType.INVALID, result.getType());

        updatedUser = new User(1, "new", "updated", "", "data", false);

        result = service.update(updatedUser);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotUpdateWithMissingPassword() {
        User user = new User(0, "test", "test", "test", "test", false);
        User setUser = new User(1, "test", "test", "test", "test", false);
        when(repository.add(user)).thenReturn(setUser);

        User updatedUser = new User(1, "new", "updated", "test", null, false);
        when(repository.update(updatedUser)).thenReturn(false);

        Result<User> result = service.update(updatedUser);
        assertEquals(ResultType.INVALID, result.getType());

        updatedUser = new User(1, "new", "updated", "test", "", false);

        result = service.update(updatedUser);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotUpdateWithoutSetID() {
        User user = new User(0, "test", "test", "test", "test", false);
        User setUser = new User(1, "test", "test", "test", "test", false);
        when(repository.add(user)).thenReturn(setUser);

        User updatedUser = new User(0, "new", "updated", "test", "data", false);
        when(repository.update(updatedUser)).thenReturn(false);

        Result<User> result = service.update(updatedUser);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldUpdate() {
        User user = new User(0, "test", "test", "test", "test", false);
        User setUser = new User(1, "test", "test", "test", "test", false);
        when(repository.add(user)).thenReturn(setUser);

        User updatedUser = new User(1, "new", "updated", "test", "data", false);
        when(repository.update(updatedUser)).thenReturn(true);

        Result<User> result = service.update(updatedUser);
        assertTrue(result.isSuccess());
    }

    //delete
    @Test
    void shouldNotDelete() {
        User user = new User(0, "test", "test", "test", "test", false);
        User setUser = new User(1, "test", "test", "test", "test", false);
        when(repository.add(user)).thenReturn(setUser);

        when(repository.deleteById(3)).thenReturn(false);

        Result<User> result = service.deleteById(3);
        assertEquals(ResultType.NOT_FOUND, result.getType());
    }

    @Test
    void shouldDelete() {
        User user = new User(0, "test", "test", "test", "test", false);
        User setUser = new User(1, "test", "test", "test", "test", false);
        when(repository.add(user)).thenReturn(setUser);

        when(repository.deleteById(1)).thenReturn(true);

        Result<User> result = service.deleteById(1);
        assertTrue(result.isSuccess());
    }
}