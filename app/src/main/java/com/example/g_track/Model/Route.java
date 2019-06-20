package com.example.g_track.Model;

import java.util.ArrayList;

public class Route {
    int routeID;
    ArrayList<Stop> stops;
    Bus routeBus;
    String routeName;
    boolean routeStatus;

    public int getRouteID() {
        return routeID;
    }

    public void setRouteID(int routeID) {
        this.routeID = routeID;
    }

    public ArrayList<Stop> getStops() {
        return stops;
    }

    public void setStops(ArrayList<Stop> stops) {
        this.stops = stops;
    }

    public Bus getRouteBus() {
        return routeBus;
    }

    public void setRouteBus(Bus routeBus) {
        this.routeBus = routeBus;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public boolean isRouteStatus() {
        return routeStatus;
    }

    public void setRouteStatus(boolean routeStatus) {
        this.routeStatus = routeStatus;
    }
}
