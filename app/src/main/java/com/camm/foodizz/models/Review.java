package com.camm.foodizz.models;

public class Review {
    private String userId;
    private String review;
    private double rating;
    private long timestamp;

    public Review(String userId, String review, double rating, long timestamp) {
        this.userId = userId;
        this.review = review;
        this.rating = rating;
        this.timestamp = timestamp;
    }

    public String getUserId() {
        return userId;
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
