package com.mrjeffapp.provider.service.Authorization;

import com.mrjeffapp.provider.api.dto.AuthorizationRequest;
import com.mrjeffapp.provider.domain.Authorization;

import java.util.List;

public interface AuthorizationService {

    List<Authorization> authorizationAuthorized(AuthorizationRequest authorizationRequest);
    List<Authorization> authorizationNoAuthorized(AuthorizationRequest authorizationRequest);
}
