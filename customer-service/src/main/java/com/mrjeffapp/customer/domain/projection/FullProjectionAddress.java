package com.mrjeffapp.customer.domain.projection;

import com.mrjeffapp.customer.domain.Address;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "full", types = { Address.class })
public interface FullProjectionAddress {

    String getId();

    @Value("#{target.alias}")
    String getFullName();

    String getAlias();

    String getAddress();

    String getAddressNumber();

    String getAddressDetails();

    String getCityCode();

    String getCityName();

    String getCityId();

    String getRegionId();

    String getCountryCode();

    String getCountryName();

    String getCountryId();

    String getPostalCodeId();

    String getPostalCode();

    String getPhoneNumber();

    Boolean getDefaultPickup();

    Boolean getDefaultDelivery();

    Boolean getDefaultBilling();

}
