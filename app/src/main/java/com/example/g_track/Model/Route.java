package com.example.g_track.Model;

public class Route {
    int routeID;
   // ArrayList<Stop> stops;
    String routeName;
    boolean routeStatus;
    String routeBusID;
    double destinationLatitude;
    double destinationLongitude;

    public Route() {
    }

    public double getDestinationLatitude() {
        return destinationLatitude;
    }

    public void setDestinationLatitude(double destinationLatitude) {
        this.destinationLatitude = destinationLatitude;
    }

    public double getDestinationLongitude() {
        return destinationLongitude;
    }

    public void setDestinationLongitude(double destinationLongitude) {
        this.destinationLongitude = destinationLongitude;
    }

    public String getRouteBusID() {
        return routeBusID;
    }

    public void setRouteBusID(String routeBusID) {
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
