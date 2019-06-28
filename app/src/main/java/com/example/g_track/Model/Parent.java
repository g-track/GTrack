package com.example.g_track.Model;

public class Parent{
    int parentID;
    String parentName;
    String parentCNIC;
    String parentPhoneNo;
    int childStudentID;
    int arrivalTime;
    boolean alertStatus;



    public Parent() {
    }

    public boolean isAlertStatus() {
        return alertStatus;
    }

    public void setAlertStatus(boolean alertStatus) {
        this.alertStatus = alertStatus;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getParentID() {
        return parentID;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentPhoneNo() {
        return parentPhoneNo;
    }

    public void setParentPhoneNo(String parentPhoneNo) {
        this.parentPhoneNo = parentPhoneNo;
    }

    public int getChildStudentID() {
        return childStudentID;
    }

    public void setChildStudentID(int childStudentID) {
        this.childStudentID = childStudentID;
    }

    public String getParentCNIC() {
        return parentCNIC;
    }

    public void setParentCNIC(String parentCNIC) {
        this.parentCNIC = parentCNIC;
    }

}
