package com.mrjeffapp.customer.api.dto;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CustomerSignUpRequest {
    private static final int MIN_PASSWORD_LENGTH = 7;

    @NotNull
    private String name;

    @NotNull
    private String lastName;

    @Email
    @NotNull
    private String email;

    @NotNull
    @Size(min = MIN_PASSWORD_LENGTH)
    private String password;

    @NotNull
    private String phoneNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
