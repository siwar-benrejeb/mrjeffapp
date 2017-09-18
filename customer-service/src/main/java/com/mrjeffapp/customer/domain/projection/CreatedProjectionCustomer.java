package com.mrjeffapp.customer.domain.projection;

import com.mrjeffapp.customer.domain.Customer;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "created", types = { Customer.class })
public interface CreatedProjectionCustomer {

    String getId();

}
