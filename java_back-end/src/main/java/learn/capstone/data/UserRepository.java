package learn.capstone.data;

import learn.capstone.models.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository {
    List<User> findAll();

    User findById(int id);

    User findByUsername(String username);

    User add(User user);

    boolean update(User user);

    @Transactional
    boolean deleteById(int id);
}
