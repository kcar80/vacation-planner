package learn.capstone.data.mappers;

import learn.capstone.models.Vacation;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VacationMapper implements RowMapper<Vacation> {


    @Override
    public Vacation mapRow(ResultSet resultSet, int i) throws SQLException {
        Vacation vacation = new Vacation();
        vacation.setVacationId(resultSet.getInt("vacation_id"));
        vacation.setDescription(resultSet.getString("description"));
        vacation.setLeisureLevel(resultSet.getInt("leasure_level"));
        vacation.setLocationId(resultSet.getInt("location_id"));
        return vacation;

    }
}
