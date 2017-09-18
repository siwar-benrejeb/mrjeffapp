package com.mrjeffapp.product.domain.projection;

import com.mrjeffapp.product.domain.ProductItem;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "detailProductType", types = { ProductItem.class })
public interface DetailProductTypeProjectionProduct {

    String getId();

    String getCode();

    String getName();

    String getDescription();

    Double getPrice();

}
