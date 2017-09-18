package com.mrjeffapp.customer.service;

import javax.validation.constraints.NotNull;

public class UpdateDefaultCustomerDeliveryAddressDto {

    @NotNull
    private String customerId;

    @NotNull
    private String addressId;

    @NotNull
    private boolean state;

    public UpdateDefaultCustomerDeliveryAddressDto(String addressId, boolean state, String customerId) {
        this.addressId = addressId;
        this.state = state;
        this.customerId = customerId;
    }

    public String getAddressId() {
        return addressId;
    }

    public boolean getState() {
        return state;
    }

    public String getCustomerId() {
        return customerId;
    }
}
