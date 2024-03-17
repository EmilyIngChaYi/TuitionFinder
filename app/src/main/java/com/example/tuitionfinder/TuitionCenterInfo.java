package com.example.tuitionfinder;

import java.util.ArrayList;

public class TuitionCenterInfo {
    String TuitionName, TuitionPhone, TuitionAddress, TuitionPostcode, TuitionPersonInChargeName, TuitionPersonInChargePhone, Price, Description, state, startBusinessHour, endBusinessHour, uid;

    ArrayList<String> student;
    ArrayList<String> subjects;

    public TuitionCenterInfo() {

    }

    public TuitionCenterInfo(String tuitionName, String tuitionPhone, String tuitionAddress, String tuitionPostcode, String tuitionPersonInChargeName, String tuitionPersonInChargePhone, String price, String description, String state, String startBusinessHour, String endBusinessHour, String uid, ArrayList<String> student, ArrayList<String> subjects) {
        TuitionName = tuitionName;
        TuitionPhone = tuitionPhone;
        TuitionAddress = tuitionAddress;
        TuitionPostcode = tuitionPostcode;
        TuitionPersonInChargeName = tuitionPersonInChargeName;
        TuitionPersonInChargePhone = tuitionPersonInChargePhone;
        Price = price;
        Description = description;
        this.state = state;
        this.startBusinessHour = startBusinessHour;
        this.endBusinessHour = endBusinessHour;
        this.uid = uid;
        this.student = student;
        this.subjects = subjects;
    }

    public String getTuitionName() {
        return TuitionName;
    }

    public void setTuitionName(String tuitionName) {
        TuitionName = tuitionName;
    }

    public String getTuitionPhone() {
        return TuitionPhone;
    }

    public void setTuitionPhone(String tuitionPhone) {
        TuitionPhone = tuitionPhone;
    }

    public String getTuitionAddress() {
        return TuitionAddress;
    }

    public void setTuitionAddress(String tuitionAddress) {
        TuitionAddress = tuitionAddress;
    }

    public String getTuitionPostcode() {
        return TuitionPostcode;
    }

    public void setTuitionPostcode(String tuitionPostcode) {
        TuitionPostcode = tuitionPostcode;
    }

    public String getTuitionPersonInChargeName() {
        return TuitionPersonInChargeName;
    }

    public void setTuitionPersonInChargeName(String tuitionPersonInChargeName) {
        TuitionPersonInChargeName = tuitionPersonInChargeName;
    }

    public String getTuitionPersonInChargePhone() {
        return TuitionPersonInChargePhone;
    }

    public void setTuitionPersonInChargePhone(String tuitionPersonInChargePhone) {
        TuitionPersonInChargePhone = tuitionPersonInChargePhone;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public ArrayList<String> getStudent() {
        return student;
    }

    public void setStudent(ArrayList<String> student) {
        this.student = student;
    }

    public ArrayList<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(ArrayList<String> subjects) {
        this.subjects = subjects;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStartBusinessHour() {
        return startBusinessHour;
    }

    public void setStartBusinessHour(String startBusinessHour) {
        this.startBusinessHour = startBusinessHour;
    }

    public String getEndBusinessHour() {
        return endBusinessHour;
    }

    public void setEndBusinessHour(String endBusinessHour) {
        this.endBusinessHour = endBusinessHour;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

}
