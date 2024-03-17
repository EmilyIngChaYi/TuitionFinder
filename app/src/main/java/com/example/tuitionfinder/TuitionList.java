package com.example.tuitionfinder;

public class TuitionList {
    String tuitionName, price, tuitionAddress, averageRating, state;

    public TuitionList(String tuitionName, String price, String tuitionAddress, String averageRating, String state) {
        this.tuitionName = tuitionName;
        this.price = price;
        this.tuitionAddress = tuitionAddress;
        this.averageRating = averageRating;
        this.state = state;
    }

    public TuitionList() {
    }

    public String getTuitionName() {
        return tuitionName;
    }

    public void setTuitionName(String tuitionName) {
        this.tuitionName = tuitionName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTuitionAddress() {
        return tuitionAddress;
    }

    public void setTuitionAddress(String tuitionAddress) {
        this.tuitionAddress = tuitionAddress;
    }

    public String getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(String averageRating) {
        this.averageRating = averageRating;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
