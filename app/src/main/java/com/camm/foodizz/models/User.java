package com.camm.foodizz.models;

public class User {
    private String userName;
    private String userImageUri;
    private String userPhoneNumber;

    public User(String userName, String userImageUri, String userPhoneNumber) {
        this.userName = userName;
        this.userImageUri = userImageUri;
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserImageUri() {
        return userImageUri;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

}
