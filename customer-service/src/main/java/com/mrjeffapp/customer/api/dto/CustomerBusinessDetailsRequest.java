package com.mrjeffapp.customer.api.dto;

import javax.validation.constraints.NotNull;

public class CustomerBusinessDetailsRequest {

    @NotNull
    private String businessId;

    @NotNull
    private String businessName;

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

}
