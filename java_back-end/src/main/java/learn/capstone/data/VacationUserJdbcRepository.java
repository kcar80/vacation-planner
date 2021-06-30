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

        return jdbcTemplate.update(sql,
                vacationUser.getVacationId(),
                vacationUser.getUser().getUserId(),
                vacationUser.getIdentifier()) >0;
    }

    @Override
    public boolean update(VacationUser vacationUser) {

        final String sql = "update vacation_user set "
                +"identifier =? "
                +"where vacation_id= ? and user_id =?;";
        return jdbcTemplate.update(sql,
                vacationUser.getIdentifier(),
                vacationUser.getVacationId(),
                vacationUser.getUser().getUserId())>0;


    }

    @Override
    public boolean deleteByKey(int vacationId, int userId) {
        final String sql = "delete from vacation_user "
                +"where vacation_id = ? and user_id= ?;";
        return jdbcTemplate.update(sql, vacationId, userId) >0;
    }
}
