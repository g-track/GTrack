package com.example.g_track.Model;

public class Student {
    String fatherName;
    String fatherCNIC;
    Route studentRoute;
    Stop studentStop;
    boolean feeStatus;
    boolean alertStatus;
    long alertTime;
    boolean studentStatus;

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

    public Route getStudentRoute() {
        return studentRoute;
    }

    public void setStudentRoute(Route studentRoute) {
        this.studentRoute = studentRoute;
    }

    public Stop getStudentStop() {
        return studentStop;
    }

    public void setStudentStop(Stop studentStop) {
        this.studentStop = studentStop;
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

    public long getAlertTime() {
        return alertTime;
    }

    public void setAlertTime(long alertTime) {
        this.alertTime = alertTime;
    }

    public boolean isStudentStatus() {
        return studentStatus;
    }

    public void setStudentStatus(boolean studentStatus) {
        this.studentStatus = studentStatus;
    }
}
