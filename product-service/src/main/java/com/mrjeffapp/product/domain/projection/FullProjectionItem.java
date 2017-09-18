package com.mrjeffapp.product.domain.projection;

import com.mrjeffapp.product.domain.Item;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "full", types = { Item.class })
public interface FullProjectionItem {

    String getId();

    String getCode();

    String getName();

    String getDescription();

}
