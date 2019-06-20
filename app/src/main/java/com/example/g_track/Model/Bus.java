package com.example.g_track.Model;

public class Bus {
    int busID;
   // Driver busDriver;
    String busDriver;
    boolean busStatus;

    public String getBusDriver() {
        return busDriver;
    }

    public void setBusDriver(String busDriver) {
        this.busDriver = busDriver;
    }

    public Bus() {
    }

    public int getBusID() {
        return busID;
    }

    public void setBusID(int busID) {
        this.busID = busID;
    }

   /* public Driver getBusDriver() {
        return busDriver;
    }*/

   /* public void setBusDriver(Driver busDriver) {
        this.busDriver = busDriver;
    }*/

    public boolean isBusStatus() {
        return busStatus;
    }
    public void setBusStatus(boolean busStatus) {
        this.busStatus = busStatus;
    }


}
