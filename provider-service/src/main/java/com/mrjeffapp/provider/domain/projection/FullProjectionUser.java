package com.mrjeffapp.provider.domain.projection;

import com.mrjeffapp.provider.domain.User;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "full", types = { User.class })
public interface FullProjectionUser {

    String getId();

    String getEmail();

}
