package com.example.g_track.Model;

public class Stop {
    int stopID;
    String stopName;
    Location stopLocation;
    boolean stopStatus;

    public int getStopID() {
        return stopID;
    }

    public void setStopID(int stopID) {
        this.stopID = stopID;
    }

    public String getStopName() {
        return stopName;
    }

    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

    public Location getStopLocation() {
        return stopLocation;
    }

    public void setStopLocation(Location stopLocation) {
        this.stopLocation = stopLocation;
    }

    public boolean isStopStatus() {
        return stopStatus;
    }

    public void setStopStatus(boolean stopStatus) {
        this.stopStatus = stopStatus;
    }

}
