package com.mrjeffapp.customer.api.dto;

import javax.validation.constraints.NotNull;

public class CreateAddressRequest {

    @NotNull
    private String alias;

    @NotNull
    private String address;

    @NotNull
    private String addressNumber;

    private String addressDetails;

    @NotNull
    private String postalCode;

    @NotNull
    private String countryCode;

    @NotNull
    private String cityName;

    @NotNull
    private String countryName;

    private String phoneNumber;

    @NotNull
    private Boolean defaultBilling;

    @NotNull
    private Boolean defaultPickup;

    @NotNull
    private Boolean defaultDelivery;

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

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getDefaultBilling() {
        return defaultBilling;
    }

    public void setDefaultBilling(Boolean defaultBilling) {
        this.defaultBilling = defaultBilling;
    }

    public Boolean getDefaultPickup() {
        return defaultPickup;
    }

    public void setDefaultPickup(Boolean defaultPickup) {
        this.defaultPickup = defaultPickup;
    }

    public Boolean getDefaultDelivery() {
        return defaultDelivery;
    }

    public void setDefaultDelivery(Boolean defaultDelivery) {
        this.defaultDelivery = defaultDelivery;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Override
    public String toString() {
        return "CreateAddressRequest{" +
                "alias='" + alias + '\'' +
                ", address='" + address + '\'' +
                ", addressNumber='" + addressNumber + '\'' +
                ", addressDetails='" + addressDetails + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", cityName='" + cityName + '\'' +
                ", countryName='" + countryName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", defaultBilling=" + defaultBilling +
                ", defaultPickup=" + defaultPickup +
                ", defaultDelivery=" + defaultDelivery +
                '}';
    }
}
