package com.mrjeffapp.customer.client.login.model;

public class RetroLoginResponse {

    private Boolean authenticated;

    public RetroLoginResponse() {
    }

    public RetroLoginResponse(Boolean authenticated) {
        this.authenticated = authenticated;
    }

    public Boolean getAuthenticated() {
        return authenticated;
    }

}
