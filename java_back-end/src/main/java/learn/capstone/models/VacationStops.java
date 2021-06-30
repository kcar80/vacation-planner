package learn.capstone.models;

import java.time.LocalDate;

public class VacationStops {

    private int vacationId;
    private LocalDate start_date;
    private LocalDate end_date;
    private String identifier;

    private Location location;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getVacationId() {
        return vacationId;
    }

    public void setVacationId(int vacationId) {
        this.vacationId = vacationId;
    }


    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
