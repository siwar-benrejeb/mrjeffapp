package com.mrjeffapp.notification.service.impl;

import com.mrjeffapp.notification.client.model.Customer;
import com.mrjeffapp.notification.service.EmailDestinationResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class EmailDestinationResolverImpl implements EmailDestinationResolver {
    private final static Logger log = LoggerFactory.getLogger(EmailDestinationResolverImpl.class);
    private static final String PROFILE_PRO = "pro";

    private final Environment environment;

    private final String defaultDestinationEmail;

    @Autowired
    public EmailDestinationResolverImpl(Environment environment, @Value("${mail.defaultReceiver}") String defaultDestinationEmail) {
        this.environment = environment;
        this.defaultDestinationEmail = defaultDestinationEmail;
    }

    @Override
    public String getDestinationEmail(Customer customer) {
        boolean productionEnvironment = Arrays.stream(environment.getActiveProfiles()).anyMatch(profile -> PROFILE_PRO.equalsIgnoreCase(profile));
        log.info("productionEnvironment: {}", productionEnvironment);

        String emailDestination;
        if (productionEnvironment) {
            emailDestination = customer.getEmail();
            log.debug("email destination from customer: email={}", emailDestination);

        } else {
            emailDestination = defaultDestinationEmail;
            log.debug("email destination from default destination: email={}", emailDestination);

        }

        log.info("selected email destination: email={}", emailDestination);
        return emailDestination;
    }

}
