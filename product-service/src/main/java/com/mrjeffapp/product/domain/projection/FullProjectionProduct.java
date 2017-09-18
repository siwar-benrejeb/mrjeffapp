package com.mrjeffapp.product.domain.projection;

import com.mrjeffapp.product.domain.Product;
import org.springframework.data.rest.core.config.Projection;

import java.util.Set;

@Projection(name = "full", types = { Product.class })
public interface FullProjectionProduct {

    String getId();

    String getCode();

    String getName();

    String getDescription();

    Double getPrice();

    Double getPriceWithoutVat();

    String getCountryId();

    String getCountryCode();

    FullProjectionProductType getProductType();

    Set<FullProjectionProductItem> getProductItems();

    FullProjectionVATRate getVatRate();

    Set<FullProjectionProductNoRecursive> getRelated();

    Boolean getItemQuotaTracking();

}
