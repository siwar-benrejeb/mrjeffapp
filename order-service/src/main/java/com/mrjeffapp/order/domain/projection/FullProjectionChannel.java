package com.mrjeffapp.order.domain.projection;

import com.mrjeffapp.order.domain.Channel;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "full", types = { Channel.class })
public interface FullProjectionChannel {

    String getId();

    String getCode();

    String getName();

    String getDescription();

}
