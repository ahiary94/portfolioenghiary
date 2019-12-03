package com.example.abeer.mysecretportfolio.models;

public class SignupModel {

    private String username;

    private String password;

    public SignupModel() {
    }

    public SignupModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

   }
