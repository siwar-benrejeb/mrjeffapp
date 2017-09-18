package com.mrjeffapp.provider.domain.projection;

import com.mrjeffapp.provider.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.Set;

@Projection(name = "login", types = { User.class })
public interface LoginProjectionUser {

    String getId();

    @Value("#{target.email}")
    String getUsername();

    @Value("#{target.provider.id}")
    String getProviderId();

    Set<LoginProjectionRole> getRoles();

}
