package com.mrjeffapp.customer.api.dto;

import javax.validation.constraints.NotNull;

public class AppendCustomerNoteRequest {

    @NotNull
    private String userId;
    @NotNull
    private String note;

    public AppendCustomerNoteRequest() {
    }

    public AppendCustomerNoteRequest(String userId, String note) {
        this.userId = userId;
        this.note = note;
    }

    public String getUserId() {
        return userId;
    }

    public String getNote() {
        return note;
    }

    @Override
    public String toString() {
        return "AppendCustomerNoteRequest{" +
                "userId='" + userId + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
