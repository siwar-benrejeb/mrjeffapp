package com.mrjeffapp.customer.api;

import com.mrjeffapp.customer.api.dto.LoginRequest;
import com.mrjeffapp.customer.domain.Customer;
import com.mrjeffapp.customer.domain.projection.FullProjectionCustomer;
import com.mrjeffapp.customer.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
public class LoginController {
    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    private final ProjectionFactory projectionFactory;

    private final LoginService loginService;

    @Autowired
    public LoginController(ProjectionFactory projectionFactory, LoginService loginService) {
        this.projectionFactory = projectionFactory;
        this.loginService = loginService;
    }

    @PostMapping("/customers/login")
    public FullProjectionCustomer login(@Valid @RequestBody LoginRequest loginRequest) {

        Customer customer = loginService.authenticated(loginRequest.getUsername(), loginRequest.getPassword());

        FullProjectionCustomer fullProjectionUser = projectionFactory.createProjection(FullProjectionCustomer.class, customer);
        return fullProjectionUser;
    }
}