package learn.capstone.models;

public class Location {
    private int location_id;
    private String description;

    public Location() {
    }

    public Location(int location_id, String description) {
        this.location_id = location_id;
        this.description = description;
    }

    public int getLocation_id() {
        return location_id;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}