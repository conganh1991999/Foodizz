package com.camm.foodizz.models;

public class User {
    private String userName;
    private String userImage;
    private String phoneNumber;

    public User(String userName, String userImage, String phoneNumber) {
        this.userName = userName;
        this.userImage = userImage;
        this.phoneNumber = phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserImage() {
        return userImage;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

}
