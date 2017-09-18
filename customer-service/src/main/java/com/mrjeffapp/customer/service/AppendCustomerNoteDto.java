package com.mrjeffapp.customer.service;

public class AppendCustomerNoteDto {

    private String customerId;
    private String note;
    private String userId;

    public AppendCustomerNoteDto() {
    }

    public AppendCustomerNoteDto(String customerId, String note, String userId) {
        this.customerId = customerId;
        this.note = note;
        this.userId = userId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getNote() {
        return note;
    }

    public String getUserId() {
        return userId;
    }
}
