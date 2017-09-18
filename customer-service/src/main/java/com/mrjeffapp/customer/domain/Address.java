package com.mrjeffapp.customer.domain;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Address {

    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.AUTO)
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition="CHAR(36)")
    private String id;

    @NotNull
    @ManyToOne
    private Customer customer;

    @NotNull
    private String alias;

    @NotNull
    private String address;

    @NotNull
    private String addressNumber;

    private String addressDetails;

    @Column(columnDefinition="CHAR(36)")
    private String cityId;

    private String cityCode;

    @NotNull
    private String cityName;

    @Column(columnDefinition="CHAR(36)")
    private String regionId;

    @Column(columnDefinition="CHAR(36)")
    private String countryId;

    @NotNull
    private String countryCode;

    @NotNull
    private String countryName;

    @Column(columnDefinition="CHAR(36)")
    private String postalCodeId;

    @NotNull
    private String postalCode;

    private String phoneNumber;

    @NotNull
    private Boolean defaultPickup;

    @NotNull
    private Boolean defaultDelivery;

    @NotNull
    private Boolean defaultBilling;

    @NotNull
    @CreatedDate
    private Date creationDate;

    @NotNull
    private Boolean active;

    public String getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getAlias() {
        return alias;
    }

    public String getAddressDetails() {
        return addressDetails;
    }

    public String getCityId() {
        return cityId;
    }

    public String getRegionId() {
        return regionId;
    }

    public String getCountryId() {
        return countryId;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setAddressDetails(String addressDetails) {
        this.addressDetails = addressDetails;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
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

    public Boolean getDefaultBilling() {
        return defaultBilling;
    }

    public void setDefaultBilling(Boolean defaultBilling) {
        this.defaultBilling = defaultBilling;
    }

    public String getPostalCodeId() {
        return postalCodeId;
    }

    public void setPostalCodeId(String postalCodeId) {
        this.postalCodeId = postalCodeId;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

}
