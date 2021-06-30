package learn.capstone.data;

import learn.capstone.data.mappers.UserMapper;
import learn.capstone.models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class UserJdbcTemplateRepository implements UserRepository{

    private final JdbcTemplate jdbcTemplate;

    public UserJdbcTemplateRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> findAll() {
        final String sql = "select user_id, first_name, last_name, user_name, password, user_type "
                + "from user;";
        return jdbcTemplate.query(sql, new UserMapper());
    }

    @Override
    public User findById(int id) {
        final String sql = "select user_id, first_name, last_name, user_name, password, user_type "
                + "from user where user_id = ?;";
        User user = jdbcTemplate.query(sql, new UserMapper(), id).stream().findFirst().orElse(null);

        return user;
    }

    @Override
    public User add(User user) {
        final String sql = "insert into user (user_id, first_name, last_name, user_name, password, user_type) "
                + "values (?,?,?,?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, user.getUserId());
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getLastName());
            ps.setString(4, user.getUsername());
            ps.setString(5, user.getPassword());
            ps.setBoolean(6, user.getUserType());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        user.setUserId(keyHolder.getKey().intValue());
        return user;
    }

    @Override
    public boolean update(User user) {
        final String sql = "update user set "
                + "first_name = ?, "
                + "last_name = ?, "
                + "user_name = ?, "
                + "password = ?, "
                + "user_type = ? "
                + "where user_id = ?;";
        return jdbcTemplate.update(sql,
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getPassword(),
                user.getUserType(),
                user.getUserId()) > 0;
    }

    @Override
    public boolean deleteById(int id) {
        jdbcTemplate.update("delete from comment where user_id = ?;", id);
        jdbcTemplate.update("delete from vacation_user where user_id = ?;", id);
        return jdbcTemplate.update("delete from user where user_id = ?;", id) > 0;
    }
}
