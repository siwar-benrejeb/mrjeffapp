package com.mrjeffapp.provider.api;

import com.mrjeffapp.provider.api.dto.AuthorizationRequest;
import com.mrjeffapp.provider.service.Authorization.AuthorizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/authorizations")
public class AuthorizationController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthorizationController.class);
    private static final String ID_DELIMITER = ",";

    private AuthorizationService authorizationServiceImpl;

    @Autowired
    public AuthorizationController(AuthorizationService authorizationServiceImpl) {
        this.authorizationServiceImpl = authorizationServiceImpl;
    }

    @PostMapping("/authorized")
    public void authorizationAuthorized(@Valid @RequestBody AuthorizationRequest authorizationRequest)
    {


        authorizationServiceImpl.authorizationAuthorized(authorizationRequest);
    }

    @PostMapping("/noauthorized")
    public void authorizationNoAuthorized(@Valid @RequestBody AuthorizationRequest authorizationRequest)
    {
        authorizationServiceImpl.authorizationNoAuthorized(authorizationRequest);
    }

}
