package com.mrjeffapp.provider.domain.projection;

import com.mrjeffapp.provider.domain.ItemCount;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "full", types = { ItemCount.class })
public interface FullProjectionItemCount {

    String getId();

    String getProductId();

    String getItemId();

    String getQuantity();

}
