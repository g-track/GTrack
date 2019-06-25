package com.example.g_track.Model;

import java.util.ArrayList;

public class Route {
    int routeID;
   // ArrayList<Stop> stops;
    String routeName;
    boolean routeStatus;
    int routeBusID;

    public Route() {
    }

    public int getRouteBusID() {
        return routeBusID;
    }

    public void setRouteBusID(int routeBusID) {
        this.routeBusID = routeBusID;
    }

    public int getRouteID() {
        return routeID;
    }

    public void setRouteID(int routeID) {
        this.routeID = routeID;
    }

 /*   public ArrayList<Stop> getStops() {
        return stops;
    }

    public void setStops(ArrayList<Stop> stops) {
        this.stops = stops;
    }*/


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
