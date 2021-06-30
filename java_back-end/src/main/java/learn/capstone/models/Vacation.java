package learn.capstone.models;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

public class Vacation {
    public int getVacationId() {
        return vacationId;
    }

    public void setVacationId(int vacationId) {
        this.vacationId = vacationId;
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



    public List<VacationUser> getUsers() {
        return users;
    }

    public void setUsers(List<VacationUser> users) {
        this.users = users;
    }

    @NotNull
    private int vacationId;

    @NotBlank
    @Size(max = 250)
    private String description;

    @Min(value = 1)
    @Max(value =3)
    private int leisureLevel;

    private List<VacationUser> users = new ArrayList<>();
    private List<VacationStops> locations = new ArrayList<>();

    public List<VacationStops> getLocations() {
        return locations;
    }

    public void setLocations(List<VacationStops> locations) {
        this.locations = locations;
    }

    public Vacation(){

    }
    public Vacation(int vacationId, String description, int leisureLevel){
        this.vacationId=vacationId;
        this.description=description;
        this.leisureLevel=leisureLevel;
    }




}