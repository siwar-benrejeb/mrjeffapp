package com.mrjeffapp.provider.domain.projection;

import com.mrjeffapp.provider.domain.Authorization;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "full", types = { Authorization.class })
public interface FullProjectionAuthorization {

    String getId();

    FullProjectionAuthorizationState getAuthorizationState();

    FullProjectionAuthorizationType getAuthorizationType();

    FullProjectionProduct getProduct();

}
