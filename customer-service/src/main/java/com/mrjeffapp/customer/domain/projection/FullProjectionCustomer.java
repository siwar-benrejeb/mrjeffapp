package com.mrjeffapp.customer.domain.projection;

import com.mrjeffapp.customer.domain.Customer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.Set;

@Projection(name = "full", types = { Customer.class })
public interface FullProjectionCustomer {

    String getId();

    String getName();

    String getLastName();

    String getEmail();

    Set<FullProjectionAddress> getAddresses();

    String getPhoneNumber();

    FullProjectionCustomerType getCustomerType();

    FullProjectionCustomerBusinessDetails getCustomerBusinessDetails();

    Set<FullProjectionCustomerNote> getCustomerNote();

    @Value("#{target.customerBusinessDetails}")
    FullProjectionCustomerBusinessDetails getCustomerDetailsB2B();

    @Value("#{target.email}")
    String getUserNicename();

    @Value("#{target.email}")
    String getUserLogin();

    Boolean getEnabled();

    String getLanguageCode();

}
