package com.mrjeffapp.provider.domain.projection;

import com.mrjeffapp.provider.domain.Provider;
import org.springframework.data.rest.core.config.Projection;

import java.util.Collection;

@Projection(name = "full", types = { Provider.class })
public interface FullProjectionProvider {

    String getId();

    String getCode();

    String getName();

    String getDescription();

    Boolean getFranchise();

    Collection<FullProjectionUser> getUsers();

    String getCountryId();

}
