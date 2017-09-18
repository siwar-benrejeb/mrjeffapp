package com.mrjeffapp.product.domain.projection;

import com.mrjeffapp.product.domain.ProductType;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "full", types = { ProductType.class })
public interface FullProjectionProductType {

    String getId();

    String getCode();

    String getName();

    String getDescription();

}
