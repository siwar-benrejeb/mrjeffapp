package com.mrjeffapp.provider.domain.projection;

import com.mrjeffapp.provider.domain.AuthorizationType;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "full", types = { AuthorizationType.class })
public interface FullProjectionAuthorizationType {

    String getId();

    String getCode();

    String getName();

    String getDescription();

}
