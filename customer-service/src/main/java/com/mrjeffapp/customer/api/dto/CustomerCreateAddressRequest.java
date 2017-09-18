package com.mrjeffapp.customer.api.dto;

import javax.validation.constraints.NotNull;

public class CustomerCreateAddressRequest {

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
    private String cityCode;

    @NotNull
    private String countryCode;

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

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public String toString() {
        return "CustomerCreateAddressRequest{" +
                "alias='" + alias + '\'' +
                ", address='" + address + '\'' +
                ", addressNumber='" + addressNumber + '\'' +
                ", addressDetails='" + addressDetails + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", countryCode='" + countryCode + '\'' +
                '}';
    }

}
