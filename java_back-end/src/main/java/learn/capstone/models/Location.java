package learn.capstone.models;

public class Location {
    private int locationId;
    private String description;
    private float latitude;
    private float longitude;

    public Location() {
    }

    public Location(int locationId, String description, float latitude, float longitude) {
        this.locationId = locationId;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}