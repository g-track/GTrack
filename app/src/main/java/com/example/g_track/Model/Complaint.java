package com.example.g_track.Model;

public class Complaint {
    int complaintID;
    String complaintDesc;
    String complaintSubject;
    int resolvedStatus;
    boolean complaintStatus;
    Route complaintRoute;

    public Route getComplaintRoute() {
        return complaintRoute;
    }

    public void setComplaintRoute(Route complaintRoute) {
        this.complaintRoute = complaintRoute;
    }

    public int getComplaintID() {
        return complaintID;
    }

    public void setComplaintID(int complaintID) {
        this.complaintID = complaintID;
    }

    public String getComplaintDesc() {
        return complaintDesc;
    }

    public void setComplaintDesc(String complaintDesc) {
        this.complaintDesc = complaintDesc;
    }

    public String getComplaintSubject() {
        return complaintSubject;
    }

    public void setComplaintSubject(String complaintSubject) {
        this.complaintSubject = complaintSubject;
    }

    public int getResolvedStatus() {
        return resolvedStatus;
    }

    public void setResolvedStatus(int resolvedStatus) {
        this.resolvedStatus = resolvedStatus;
    }

    public boolean isComplaintStatus() {
        return complaintStatus;
    }

    public void setComplaintStatus(boolean complaintStatus) {
        this.complaintStatus = complaintStatus;
    }
}
