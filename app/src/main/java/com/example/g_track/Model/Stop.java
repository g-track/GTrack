package com.example.g_track.Model;

public class Stop {
    int stopID;
    String stopName;
    //Location stopLocation;
    boolean stopStatus;
    double stopLatitude;
    double stopLongitude;
    int stopRouteID;

    public Stop() {
    }

    public int getStopRouteID() {
        return stopRouteID;
    }

    public void setStopRouteID(int stopRouteID) {
        this.stopRouteID = stopRouteID;
    }

    public double getStopLatitude() {
        return stopLatitude;
    }

    public void setStopLatitude(double stopLatitude) {
        this.stopLatitude = stopLatitude;
    }

    public double getStopLongitude() {
        return stopLongitude;
    }

    public void setStopLongitude(double stopLongitude) {
        this.stopLongitude = stopLongitude;
    }

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
/*
    public Location getStopLocation() {
        return stopLocation;
    }

    public void setStopLocation(Location stopLocation) {
        this.stopLocation = stopLocation;
    }*/

    public boolean isStopStatus() {
        return stopStatus;
    }

    public void setStopStatus(boolean stopStatus) {
        this.stopStatus = stopStatus;
    }

}
