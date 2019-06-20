package com.example.g_track.Model;

public class Location {
    int locationID;
    double latitude;
    double longitude;

    public Location(int locationID, double latitude, double longitude) {
        this.locationID = locationID;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getLocationID() {
        return locationID;
    }

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
