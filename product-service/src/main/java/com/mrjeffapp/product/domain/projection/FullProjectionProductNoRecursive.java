package com.mrjeffapp.product.domain.projection;

import com.mrjeffapp.product.domain.Product;
import org.springframework.data.rest.core.config.Projection;

import java.util.Set;

@Projection(name = "fullNoRecursive", types = { Product.class })
public interface FullProjectionProductNoRecursive {

    String getId();

    String getCode();

    String getName();

    String getDescription();

    Double getPrice();

    String getCountryId();

    FullProjectionProductType getProductType();

    Set<FullProjectionProductItem> getProductItems();

    FullProjectionVATRate getVatRate();

}
