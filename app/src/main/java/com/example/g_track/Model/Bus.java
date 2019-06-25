package com.example.g_track.Model;

public class Bus {
    int busID;
    boolean busStatus;
    int busDriverID;
    double busLatitude;
    double busLongitude;

    public double getBusLatitude() {
        return busLatitude;
    }

    public void setBusLatitude(double busLatitude) {
        this.busLatitude = busLatitude;
    }

    public double getBusLongitude() {
        return busLongitude;
    }

    public void setBusLongitude(double busLongitude) {
        this.busLongitude = busLongitude;
    }

    public Bus() {
    }

    public int getBusDriverID() {
        return busDriverID;
    }

    public void setBusDriverID(int busDriverID) {
        this.busDriverID = busDriverID;
    }
    public int getBusID() {
        return busID;
    }

    public void setBusID(int busID) {
        this.busID = busID;
    }

    public boolean isBusStatus() {
        return busStatus;
    }
    public void setBusStatus(boolean busStatus) {
        this.busStatus = busStatus;
    }


}
