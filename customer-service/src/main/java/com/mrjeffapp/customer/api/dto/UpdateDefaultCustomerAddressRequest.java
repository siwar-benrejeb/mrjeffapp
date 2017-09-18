package com.mrjeffapp.customer.api.dto;

import javax.validation.constraints.NotNull;

public class UpdateDefaultCustomerAddressRequest {

    @NotNull
    private String addressId;

    @NotNull
    private boolean state;

    public UpdateDefaultCustomerAddressRequest() {
    }

    public UpdateDefaultCustomerAddressRequest(String addressId, boolean state) {
        this.addressId = addressId;
        this.state = state;
    }

    public String getAddressId() {
        return addressId;
    }

    public boolean getState() {
        return state;
    }

    @Override
    public String toString() {
        return "UpdateDefaultCustomerAddressRequest{" +
                "addressId='" + addressId + '\'' +
                ", state=" + state +
                '}';
    }
}

