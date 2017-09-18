package com.mrjeffapp.customer.domain.projection;

import com.mrjeffapp.customer.domain.CustomerType;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "full", types = { CustomerType.class })
public interface FullProjectionCustomerType {

    String getId();

    String getName();

    String getCode();

    String getDescription();

}
