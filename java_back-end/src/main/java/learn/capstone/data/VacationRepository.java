package learn.capstone.data;

import learn.capstone.models.Vacation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface VacationRepository {

    List<Vacation> findAll();

    List<Vacation> findByUserId(int userId);

    @Transactional
    Vacation findById (int vacationId);



    Vacation add(Vacation vacation);

    boolean update(Vacation vacation);

    @Transactional
    boolean deleteById(int vacationId);

}
