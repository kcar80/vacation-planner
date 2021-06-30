package learn.capstone.data.mappers;

import learn.capstone.models.Location;
import learn.capstone.models.VacationStops;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VacationStopMapper implements RowMapper<VacationStops> {
    @Override
    public VacationStops mapRow(ResultSet resultSet, int i) throws SQLException {
        VacationStops vacationStops = new VacationStops();
        vacationStops.setVacationId(resultSet.getInt("vacation_id"));
        vacationStops.setLocationId(resultSet.getInt("location_id"));
        vacationStops.setStart_date(resultSet.getDate("start_date").toLocalDate());
        vacationStops.setEnd_date(resultSet.getDate("end_date").toLocalDate());
        vacationStops.setIdentifier(resultSet.getString("identifier"));

        VacationMapper vacationMapper = new VacationMapper();
        vacationStops.setVacation(vacationMapper.mapRow(resultSet, i));

        LocationMapper locationMapper = new LocationMapper();
        vacationStops.setLocation(locationMapper.mapRow(resultSet, i));

        return vacationStops;
    }
}
