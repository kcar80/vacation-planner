package learn.capstone.models;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Vacation {
    public int getVacationId() {
        return vacationId;
    }

    public void setVacationId(int vacationId) {
        this.vacationId = vacationId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLeisureLevel() {
        return leisureLevel;
    }

    public void setLeisureLevel(int leisureLevel) {
        this.leisureLevel = leisureLevel;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public List<VacationUser> getUsers() {
        return users;
    }

    public void setUsers(List<VacationUser> users) {
        this.users = users;
    }

    @NotNull
    private int vacationId;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotBlank
    @Size(max = 250)
    private String description;

    @Min(value = 1)
    @Max(value =3)
    private int leisureLevel;

    @NotNull
    private int locationId;
    private List<VacationUser> users = new ArrayList<>();

    public Vacation(){

    }




}