package learn.capstone.data;

import learn.capstone.data.mappers.VacationMapper;
import learn.capstone.data.mappers.VacationStopMapper;
import learn.capstone.data.mappers.VacationUserMapper;
import learn.capstone.models.User;
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
        final String sql = "select vacation_id, `description`, "
                +"leasure_level from vacation limit 1000;";
        List<Vacation> vacations = jdbcTemplate.query(sql, new VacationMapper());

        for(Vacation v: vacations) {
            if(v !=null){
                addUsers(v);
                addLocations(v);
            }
        }

        return vacations;
    }

    @Override
    public List<Vacation> findByUserId(int userId) {
        final String sql = "select v.vacation_id, v.description, v.leasure_level " +
                "from vacation v " +
                "inner join vacation_user vu on vu.vacation_id = v.vacation_id " +
                "inner join user u on u.user_id = vu.user_id " +
                "where u.user_id=?;";

        List<Vacation> vacations = jdbcTemplate.query(sql, new VacationMapper(), userId);

        for(Vacation v: vacations) {
            if(v !=null){
                addUsers(v);
                addLocations(v);
            }
        }

        return vacations;
    }

    @Override
    @Transactional
    public Vacation findById(int vacationId) {
        final String sql = "select vacation_id, `description`, "
                +"leasure_level from vacation where vacation_id = ?;";
        Vacation vacation = jdbcTemplate.query(sql, new VacationMapper(), vacationId).stream()
                .findFirst().orElse(null);

        if(vacation !=null){
            addUsers(vacation);
            addLocations(vacation);

        }

        return vacation;
    }

    @Override
    public Vacation add(Vacation vacation) {
        final String sql = "insert into vacation (`description`, "
                +"leasure_level) values (?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, vacation.getDescription());
            ps.setInt(2, vacation.getLeisureLevel());
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
                +"`description` =?, "
                +"leasure_level =? "

                + "where vacation_id= ?;";

        return jdbcTemplate.update(sql,
                vacation.getDescription(),
                vacation.getLeisureLevel(),

                vacation.getVacationId()) >0;

    }

    @Override
    @Transactional
    public boolean deleteById(int vacationId) {
        jdbcTemplate.update("delete from vacation_user where vacation_id =?", vacationId);
        jdbcTemplate.update("delete from vacation_stops where vacation_id =?", vacationId);
        return jdbcTemplate.update("delete from vacation where vacation_id =?;", vacationId) >0;

    }

    private void addUsers(Vacation vacation){
        final String sql = "select vu.vacation_id, vu.user_id, vu.identifier, "
                + "u.first_name, u.last_name, u.user_name, u.password, u.user_type "
                + "from vacation_user vu "
                + "inner join user u on vu.user_id = u.user_id "
               + "where vu.vacation_id = ?;";

        var vacationUsers = jdbcTemplate.query(sql, new VacationUserMapper(), vacation.getVacationId());
        vacation.setUsers(vacationUsers);
    }

    private void addLocations(Vacation vacation){
        final String sql = "select vs.vacation_id, vs.location_id, vs.start_date, "
                +" vs.end_date, vs.identifier, l.description, l.latitude, l.longitude "
                + "from vacation_stops vs "
                + "inner join location l on vs.location_id = l.location_id "
                + "where vs.vacation_id = ?;";

        var vacationStops = jdbcTemplate.query(sql, new VacationStopMapper(), vacation.getVacationId());
        vacation.setLocations(vacationStops);
    }


}
