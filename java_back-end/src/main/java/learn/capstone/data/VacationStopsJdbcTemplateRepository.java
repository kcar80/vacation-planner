package learn.capstone.data;

import learn.capstone.models.VacationStops;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class VacationStopsJdbcTemplateRepository implements VacationStopsRepository{

    private final JdbcTemplate jdbcTemplate;

    public VacationStopsJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean add(VacationStops vacationStops) {

        final String sql = "insert into vacation_stops (vacation_id, location_id, start_date, end_date, identifier) "
                + " value (?,?,?,?,?);";

        return jdbcTemplate.update(sql,
                vacationStops.getVacationId(),
                vacationStops.getLocation().getLocationId(),
                vacationStops.getStartDate(),
                vacationStops.getEndDate(),
                vacationStops.getIdentifier()) > 0;
    }

    @Override
    public boolean update(VacationStops vacationStops) {

        final String sql = "update vacation_stops set "
                + "start_date = ?, "
                + "end_date = ?, "
                + "identifier = ? "
                + "where vacation_id = ? and location_id = ?;";

        return jdbcTemplate.update(sql,
                vacationStops.getStartDate(),
                vacationStops.getEndDate(),
                vacationStops.getIdentifier(),
                vacationStops.getVacationId(),
                vacationStops.getLocation().getLocationId()) > 0;
    }

    @Override
    public boolean deleteByKey(int vacationId, int locationId) {

        final String sql = "delete from vacation_stops "
                + "where vacation_id = ? and location_id = ?;";

        return jdbcTemplate.update(sql, vacationId, locationId) > 0;
    }
}
