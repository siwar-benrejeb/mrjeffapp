package com.mrjeffapp.product.domain.projection;

import com.mrjeffapp.product.domain.VATRate;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "full", types = { VATRate.class })
public interface FullProjectionVATRate {

    String getId();

    String getCountryId();

    String getName();

    String getDescription();

    Double getPercentage();

}
