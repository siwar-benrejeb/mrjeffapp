package com.mrjeffapp.product.domain.projection;

import com.mrjeffapp.product.domain.ProductType;
import org.springframework.data.rest.core.config.Projection;

import java.util.Set;

@Projection(name = "detailProductType", types = { ProductType.class })
public interface DetailProductTypeProjectionProductType {

    String getId();

    String getCode();

    String getName();

    String getDescription();

    Set<DetailProductTypeProjectionProduct> getProducts();
}
