package learn.capstone.data.mappers;

import learn.capstone.models.VacationStops;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VacationStopMapper implements RowMapper<VacationStops> {
    @Override
    public VacationStops mapRow(ResultSet resultSet, int i) throws SQLException {
        VacationStops vacationStops = new VacationStops();
        vacationStops.setVacationId(resultSet.getInt("vacation_id"));
        vacationStops.setStartDate(resultSet.getDate("start_date").toLocalDate());
        vacationStops.setEndDate(resultSet.getDate("end_date").toLocalDate());
        vacationStops.setIdentifier(resultSet.getString("identifier"));

        LocationMapper locationMapper = new LocationMapper();
        vacationStops.setLocation(locationMapper.mapRow(resultSet, i));

        return vacationStops;
    }
}
