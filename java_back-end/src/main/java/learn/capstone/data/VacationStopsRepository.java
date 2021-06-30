package learn.capstone.data;

import learn.capstone.models.VacationStops;

public interface VacationStopsRepository {

    boolean add(VacationStops vacationStops);

    boolean update(VacationStops vacationStops);

    boolean deleteByKey(int vacationId, int locationId);
}