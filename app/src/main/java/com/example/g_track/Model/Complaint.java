package com.example.g_track.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Complaint implements Parcelable {
    int complaintID;
    String complaintDesc;
    String complaintSubject;
    int resolvedStatus;
    boolean complaintStatus;
    int studentId;
    String complaintTime;


    public Complaint() {

    }

    protected Complaint(Parcel in) {
        complaintID = in.readInt();
        complaintDesc = in.readString();
        complaintSubject = in.readString();
        resolvedStatus = in.readInt();
        complaintStatus = in.readByte() != 0;
        studentId = in.readInt();
        complaintTime = in.readString();
    }

    public static final Creator<Complaint> CREATOR = new Creator<Complaint>() {
        @Override
        public Complaint createFromParcel(Parcel in) {
            return new Complaint(in);
        }

        @Override
        public Complaint[] newArray(int size) {
            return new Complaint[size];
        }
    };

    public String getComplaintTime() {
        return complaintTime;
    }

    public void setComplaintTime(String complaintTime) {
        this.complaintTime = complaintTime;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
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

    public boolean getComplaintStatus() {
        return complaintStatus;
    }

    public void setComplaintStatus(boolean complaintStatus) {
        this.complaintStatus = complaintStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(complaintID);
        dest.writeString(complaintDesc);
        dest.writeString(complaintSubject);
        dest.writeInt(resolvedStatus);
        dest.writeByte((byte) (complaintStatus ? 1 : 0));
        dest.writeLong(studentId);
        dest.writeString(complaintTime);
    }
}
