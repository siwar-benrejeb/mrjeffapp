package com.mrjeffapp.provider.domain.projection;

import com.mrjeffapp.provider.domain.AuthorizationState;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "full", types = { AuthorizationState.class })
public interface FullProjectionAuthorizationState {

    String getId();

    String getCode();

    String getName();

    String getDescription();

}
