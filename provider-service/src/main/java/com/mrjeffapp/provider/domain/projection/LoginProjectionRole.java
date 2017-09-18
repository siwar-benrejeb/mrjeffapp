package com.mrjeffapp.provider.domain.projection;

import com.mrjeffapp.provider.domain.Role;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "login", types = { Role.class })
public interface LoginProjectionRole {

    String getCode();

}
