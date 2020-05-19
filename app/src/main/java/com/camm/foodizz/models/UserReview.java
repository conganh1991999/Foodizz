package com.camm.foodizz.models;

public class UserReview {
    private String userName;
    private String userImage;
    private String review;
    private double rating;
    private long timestamp;

    public UserReview(String userName, String userImage, String review, double rating, long timestamp) {
        this.userName = userName;
        this.userImage = userImage;
        this.review = review;
        this.rating = rating;
        this.timestamp = timestamp;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserImage() {
        return userImage;
    }

    public String getReview() {
        return review;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public double getRating() {
        return rating;
    }
}
