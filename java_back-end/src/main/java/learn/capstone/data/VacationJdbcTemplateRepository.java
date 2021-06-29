package learn.capstone.data;

import learn.capstone.data.mappers.VacationMapper;
import learn.capstone.models.Vacation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class VacationJdbcTemplateRepository implements VacationRepository{

    private final JdbcTemplate jdbcTemplate;

    public VacationJdbcTemplateRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Vacation> findAll() {
        final String sql = "select vacation_id, start_date, end_date, `description`, "
                +"leisure_level, location_id from vacation limit 1000;";
        return jdbcTemplate.query(sql, new VacationMapper());
    }

    @Override
    @Transactional
    public Vacation findById(int vacationId) {
        final String sql = "select vacation_id, start_date, end_date, `description`, "
                +"leisure_level, location_id from vacation where vacation_id = ?;";
        Vacation vacation = jdbcTemplate.query(sql, new VacationMapper(), vacationId).stream()
                .findFirst().orElse(null);


        return vacation;
    }

    @Override
    public Vacation add(Vacation vacation) {
        final String sql = "insert into vacation (vacation_id, start_date, end_date, `description`, "
                +"leisure_level, location_id) values (?,?,?,?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setDate(1, Date.valueOf(vacation.getStartDate()));
            ps.setDate(2, Date.valueOf(vacation.getEndDate()));
            ps.setString(3, vacation.getDescription());
            ps.setInt(4, vacation.getLeisureLevel());
            ps.setInt(5,vacation.getLocationId());
            return ps;
        }, keyHolder);
        if (rowsAffected <= 0){
            return null;
        }
        vacation.setVacationId(keyHolder.getKey().intValue());
        return vacation;
    }

    @Override
    public boolean update(Vacation vacation) {
        final String sql = "update vacation set "
                +"vacation_id =?, "
                +"start_date =?, "
                +"end_date =?, "
                +"`description` =?, "
                +"leisure_level =?, "
                +"location_id =? "
                + "where vacation_id= ?;";

        return jdbcTemplate.update(sql,
                vacation.getStartDate(),
                vacation.getEndDate(),
                vacation.getDescription(),
                vacation.getLeisureLevel(),
                vacation.getLocationId(),
                vacation.getVacationId()) >0;

    }

    @Override
    @Transactional
    public boolean deleteById(int vacationId) {
        return jdbcTemplate.update("delete from vacation where vacation_id =?;", vacationId) >0;

    }
}
