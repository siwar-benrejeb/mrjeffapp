package com.mrjeffapp.customer.client.login.model;

public class RetroLoginRequest {

    private String username;

    private String password;

    public RetroLoginRequest() {
    }

    public RetroLoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
