package com.example.tuitionfinder;

public class UserInfo {
    String Name;
    String Email;
    String Phone;
    String IcNum;
    String Age;
    String UserType;
    String School;
    String Grade;

    public UserInfo() {

    }

    public UserInfo(String name, String email, String phone, String icNum, String age, String userType, String school, String grade) {
        Name = name;
        Email = email;
        Phone = phone;
        IcNum = icNum;
        Age = age;
        UserType = userType;
        School = school;
        Grade = grade;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getIcNum() {
        return IcNum;
    }

    public void setIcNum(String icNum) {
        IcNum = icNum;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }

    public String getSchool() {
        return School;
    }

    public void setSchool(String school) {
        School = school;
    }

    public String getGrade() {
        return Grade;
    }

    public void setGrade(String grade) {
        Grade = grade;
    }
}
