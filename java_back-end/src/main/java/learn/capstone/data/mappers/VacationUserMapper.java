package learn.capstone.data.mappers;

import learn.capstone.models.VacationUser;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VacationUserMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        VacationUser vacationUser = new VacationUser();
        vacationUser.setVacation_id(resultSet.getInt("vacation_id"));
        vacationUser.setUser_id(resultSet.getInt("user_id"));
        vacationUser.setIdentifier(resultSet.getString("identifier"));

        VacationMapper vacationMapper = new VacationMapper();
        vacationUser.setVacation(vacationMapper.mapRow(resultSet, i));

        UserMapper userMapper = new UserMapper();
        vacationUser.setUser(userMapper.mapRow(resultSet, i));
        return vacationUser;
    }
}
