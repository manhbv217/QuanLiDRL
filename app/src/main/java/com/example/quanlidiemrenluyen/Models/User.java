package com.example.quanlidiemrenluyen.Models;

public class User {
    private String username;
    private String id;
    private String imageURL;

    public User(String userName, String id, String imageURL) {
        this.username = userName;
        this.id = id;
        this.imageURL = imageURL;
    }
    public User(){

    }
    public String getUserName() {
        return username;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
