package learn.capstone.domain;

import learn.capstone.data.UserRepository;
import learn.capstone.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(int id) {
        return repository.findById(id);
    }

    public Result<User> add(User user) {
        Result<User> result = validateNewUser(user);

        if (!result.isSuccess()) {
            return result;
        }

        result.setPayload(repository.add(user));
        return result;
    }

    public Result<User> update(User user) {
        Result<User> result = validateExistingUser(user);

        if (!result.isSuccess()) {
            return result;
        }

        if (!repository.update(user)) {
            result.addMessage(String.format("User with ID: %s not found.", user.getUserId()), ResultType.NOT_FOUND);
        }

        return result;
    }

    public Result<User> deleteById(int id) {
        Result<User> result = new Result<>();

        if(!repository.deleteById(id)) {
            result.addMessage(String.format("User with ID: %s not found.", id), ResultType.NOT_FOUND);
        }

        return result;
    }

    private Result<User> validateNewUser(User user) {
        Result<User> result = validateUser(user);

        if (!result.isSuccess()) {
            return result;
        }

        if (user.getUserId() != 0) {
            result.addMessage("ID cannot be set for 'add' method", ResultType.INVALID);
        }

        return result;
    }

    private Result<User> validateExistingUser(User user) {
        Result<User> result = validateUser(user);

        if (!result.isSuccess()) {
            return result;
        }

        if (user.getUserId() <= 0) {
            result.addMessage("ID must be set.", ResultType.INVALID);
        }

        return result;
    }

    private Result<User> validateUser(User user) {
        Result<User> result = new Result<>();

        if (user == null) {
            result.addMessage("User cannot be null", ResultType.INVALID);
            return result;
        }

        if (user.getFirstName() == null || user.getFirstName().equals("")
                || user.getLastName() == null || user.getLastName().equals("")) {
            result.addMessage("Name is required.", ResultType.INVALID);
        }

        if (user.getUsername() == null || user.getUsername().equals("")) {
            result.addMessage("Username is required.", ResultType.INVALID);
        }

        if (user.getPassword() == null || user.getPassword().equals("")) {
            result.addMessage("Password is required.", ResultType.INVALID);
        }

        return result;
    }
}
