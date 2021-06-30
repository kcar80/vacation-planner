package learn.capstone.data.mappers;

import learn.capstone.models.VacationUser;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VacationUserMapper implements RowMapper<VacationUser> {
    @Override
    public VacationUser mapRow(ResultSet resultSet, int i) throws SQLException {
        VacationUser vacationUser = new VacationUser();
        vacationUser.setVacationId(resultSet.getInt("vacation_id"));
        vacationUser.setIdentifier(resultSet.getString("identifier"));

        UserMapper userMapper = new UserMapper();
        vacationUser.setUser(userMapper.mapRow(resultSet, i));
        return vacationUser;
    }
}
