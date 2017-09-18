package com.mrjeffapp.customer.domain.projection;

import com.mrjeffapp.customer.domain.CustomerBusinessDetails;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "full", types = { CustomerBusinessDetails.class })
public interface FullProjectionCustomerBusinessDetails {

    String getId();

    String getBusinessId();

    String getBusinessName();

}
