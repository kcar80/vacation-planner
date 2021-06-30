package learn.capstone.data;

import learn.capstone.models.VacationUser;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class VacationUserJdbcRepository implements VacationUserRepository{

    private final JdbcTemplate jdbcTemplate;

    public VacationUserJdbcRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }


    @Override
    public boolean add(VacationUser vacationUser) {
        final String sql = "insert into vacation_user (vacation_id, user_id, identifier) values"
                + "(?,?,?);";

        return false;
    }

    @Override
    public boolean update(VacationUser vacationUser) {
        return false;
    }

    @Override
    public boolean deleteByKey(int vacationId, int userId) {
        return false;
    }
}
