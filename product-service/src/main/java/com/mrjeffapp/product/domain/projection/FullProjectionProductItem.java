package com.mrjeffapp.product.domain.projection;

import com.mrjeffapp.product.domain.ProductItem;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "full", types = { ProductItem.class })
public interface FullProjectionProductItem {

    String getId();

    Integer getQuantity();

    FullProjectionItem getItem();

}
