package com.example.tuitionfinder;

public class Review {
    String reviews;
    float rating;

    public Review() {
    }

    public Review(String reviews, float rating) {
        this.reviews = reviews;
        this.rating = rating;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
