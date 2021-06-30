package learn.capstone.data;

import learn.capstone.models.VacationUser;

public interface VacationUserRepository {

    boolean add(VacationUser vacationUser);

    boolean update(VacationUser vacationUser);

    boolean deleteByKey(int vacationId, int userId);
}
