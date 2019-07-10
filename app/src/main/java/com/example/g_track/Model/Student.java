package com.example.g_track.Model;

public class Student {
    int studentID;
    String studentName;
    String studentPhone;
    String fatherName;
    String fatherCNIC;
    int  studentRouteID;
    int studentStopID;
    boolean feeStatus;
    boolean alertStatus;
    int alertDepartureTime;
    int alertArrivalTime;
    boolean studentStatus;

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentPhone() {
        return studentPhone;
    }

    public void setStudentPhone(String studentPhone) {
        this.studentPhone = studentPhone;
    }

    public int getStudentRouteID() {
        return studentRouteID;
    }

    public void setStudentRouteID(int studentRouteID) {
        this.studentRouteID = studentRouteID;
    }

    public int getStudentStopID() {
        return studentStopID;
    }

    public void setStudentStopID(int studentStopID) {
        this.studentStopID = studentStopID;
    }

    public int getAlertDepartureTime() {
        return alertDepartureTime;
    }

    public void setAlertDepartureTime(int alertDepartureTime) {
        this.alertDepartureTime = alertDepartureTime;
    }

    public int getAlertArrivalTime() {
        return alertArrivalTime;
    }

    public void setAlertArrivalTime(int alertArrivalTime) {
        this.alertArrivalTime = alertArrivalTime;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getFatherCNIC() {
        return fatherCNIC;
    }

    public void setFatherCNIC(String fatherCNIC) {
        this.fatherCNIC = fatherCNIC;
    }

    public boolean isFeeStatus() {
        return feeStatus;
    }

    public void setFeeStatus(boolean feeStatus) {
        this.feeStatus = feeStatus;
    }

    public boolean isAlertStatus() {
        return alertStatus;
    }

    public void setAlertStatus(boolean alertStatus) {
        this.alertStatus = alertStatus;
    }

    public boolean isStudentStatus() {
        return studentStatus;
    }

    public void setStudentStatus(boolean studentStatus) {
        this.studentStatus = studentStatus;
    }
}
