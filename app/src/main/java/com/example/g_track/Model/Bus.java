package com.example.g_track.Model;

public class Bus {
    String busID;
    boolean busStatus;
    int busDriverID;
    double busLatitude;
    double busLongitude;

    public String getBusID() {
        return busID;
    }

    public void setBusID(String busID) {
        this.busID = busID;
    }

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

    public boolean isBusStatus() {
        return busStatus;
    }
    public void setBusStatus(boolean busStatus) {
        this.busStatus = busStatus;
    }


}
