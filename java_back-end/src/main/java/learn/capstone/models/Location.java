package learn.capstone.models;

public class Location {
    private int locationId;
    private String description;

    public Location() {
    }

    public Location(int locationId, String description) {
        this.locationId = locationId;
        this.description = description;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}