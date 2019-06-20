package com.example.g_track.Model;

public class Parent extends Person {
    String parentCNIC;
    Student childStudent;

    public String getParentCNIC() {
        return parentCNIC;
    }

    public void setParentCNIC(String parentCNIC) {
        this.parentCNIC = parentCNIC;
    }

    public Student getChildStudent() {
        return childStudent;
    }

    public void setChildStudent(Student childStudent) {
        this.childStudent = childStudent;
    }
}
