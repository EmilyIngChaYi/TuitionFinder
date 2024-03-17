package com.example.tuitionfinder;

import java.util.ArrayList;

public class BookingInfo {
    String TuitionName, StudentName, StudentPhone, StudentGrade, StudentEmail, StudentID, TuitionID, TuitionPhone;
    ArrayList<String> SelectSubjects;

    public BookingInfo() {
    }

    public BookingInfo(String tuitionName, String studentName, String studentPhone, String studentGrade, String studentEmail, String studentID, String tuitionID, String tuitionPhone, ArrayList<String> selectSubjects) {
        TuitionName = tuitionName;
        StudentName = studentName;
        StudentPhone = studentPhone;
        StudentGrade = studentGrade;
        StudentEmail = studentEmail;
        StudentID = studentID;
        TuitionID = tuitionID;
        TuitionPhone = tuitionPhone;
        SelectSubjects = selectSubjects;
    }

    public String getTuitionName() {
        return TuitionName;
    }

    public void setTuitionName(String tuitionName) {
        TuitionName = tuitionName;
    }

    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(String studentName) {
        StudentName = studentName;
    }

    public String getStudentPhone() {
        return StudentPhone;
    }

    public void setStudentPhone(String studentPhone) {
        StudentPhone = studentPhone;
    }

    public String getStudentGrade() {
        return StudentGrade;
    }

    public void setStudentGrade(String studentGrade) {
        StudentGrade = studentGrade;
    }

    public String getStudentEmail() {
        return StudentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        StudentEmail = studentEmail;
    }

    public ArrayList<String> getSelectSubjects() {
        return SelectSubjects;
    }

    public void setSelectSubjects(ArrayList<String> selectSubjects) {
        SelectSubjects = selectSubjects;
    }

    public String getStudentID() {
        return StudentID;
    }

    public void setStudentID(String studentID) {
        StudentID = studentID;
    }

    public String getTuitionID() {
        return TuitionID;
    }

    public void setTuitionID(String tuitionID) {
        TuitionID = tuitionID;
    }

    public String getTuitionPhone() {
        return TuitionPhone;
    }

    public void setTuitionPhone(String tuitionPhone) {
        TuitionPhone = tuitionPhone;
    }
}
