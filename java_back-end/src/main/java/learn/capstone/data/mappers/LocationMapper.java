package learn.capstone.data.mappers;

import learn.capstone.models.Location;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LocationMapper implements RowMapper<Location> {

    @Override
    public Location mapRow(ResultSet resultSet, int i) throws SQLException {
        Location location = new Location();
        location.setLocation_id(resultSet.getInt("location_id"));
        location.setDescription(resultSet.getString("description"));
        return location;
    }
}