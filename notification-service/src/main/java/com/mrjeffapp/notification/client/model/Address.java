package com.mrjeffapp.notification.client.model;

public class Address {
    private String alias;

    private String address;

    private String addressNumber;

    private String addressDetails;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressNumber() {
        return addressNumber;
    }

    public void setAddressNumber(String addressNumber) {
        this.addressNumber = addressNumber;
    }

    public String getAddressDetails() {
        return addressDetails;
    }

    public void setAddressDetails(String addressDetails) {
        this.addressDetails = addressDetails;
    }

    @Override
    public String toString() {
        return "Address{" +
                "alias='" + alias + '\'' +
                ", address='" + address + '\'' +
                ", addressNumber='" + addressNumber + '\'' +
                ", addressDetails='" + addressDetails + '\'' +
                '}';
    }
}
