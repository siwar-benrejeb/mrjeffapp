package com.mrjeffapp.customer.api.dto;

import org.hibernate.validator.constraints.Email;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class CustomerCreateRequest {

    @NotNull
    private String name;

    @NotNull
    private String lastName;

    @NotNull
    private String phoneNumber;

    @Email
    @NotNull
    private String email;

    @NotNull
    @Valid
    private CustomerCreateAddressRequest pickupAddress;

    @Valid
    private CustomerCreateAddressRequest deliveryAddress;

    @Valid
    private CustomerCreateAddressRequest billingAddress;

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

    public CustomerCreateAddressRequest getPickupAddress() {
        return pickupAddress;
    }

    public void setPickupAddress(CustomerCreateAddressRequest pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    public CustomerCreateAddressRequest getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(CustomerCreateAddressRequest deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public CustomerCreateAddressRequest getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(CustomerCreateAddressRequest billingAddress) {
        this.billingAddress = billingAddress;
    }

    public boolean hasDeliveryAddress() {
        return deliveryAddress != null;
    }

    public boolean hasBillingAddress() {
        return billingAddress != null;
    }

    @Override
    public String toString() {
        return "CustomerCreateRequest{" +
                "name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", pickupAddress=" + pickupAddress +
                ", deliveryAddress=" + deliveryAddress +
                ", billingAddress=" + billingAddress +
                '}';
    }

}
