package com.mrjeffapp.provider.service.Authorization;

import com.mrjeffapp.provider.domain.Authorization;
import com.mrjeffapp.provider.domain.AuthorizationState;
import com.mrjeffapp.provider.repository.AuthorizationRepository;
import com.mrjeffapp.provider.repository.AuthorizationStateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static com.mrjeffapp.provider.config.Constants.BUSINESS_EVENT_LOGGER;

@Service
public class ToExpiredAuthorizationServiceImpl implements ToExpiredAuthorizationService{

    private static final Logger LOG = LoggerFactory.getLogger(ToExpiredAuthorizationServiceImpl.class);
    private static final Logger BUSINESS_LOG = LoggerFactory.getLogger(BUSINESS_EVENT_LOGGER);

    private final AuthorizationRepository authorizationRepository;
    private final AuthorizationStateRepository authorizationStateRepository;

    @Autowired
    public ToExpiredAuthorizationServiceImpl(AuthorizationRepository authorizationRepository,
                                             AuthorizationStateRepository authorizationStateRepository) {
        this.authorizationRepository = authorizationRepository;
        this.authorizationStateRepository = authorizationStateRepository;
    }


    @Transactional
    @Override
    public void toExpiredAuthorizationByDeliveryDate(Date deliveryDate, String countryCode,  String userOperator) {
        LOG.debug("Expired authorization by delivery date: {} ", deliveryDate);
        AuthorizationState authorizactionStateExpired = authorizationStateRepository.findByCode(AuthorizationState.EXPIRED);

        List<Authorization> authorizations = authorizationRepository.findByDeliveryWorkOrderDateAndCountryCode(deliveryDate, countryCode);

        authorizations.stream().forEach(authorization -> {
            authorization.changeAuthorizationState(authorizactionStateExpired, userOperator);
            authorizationRepository.save(authorization);
            BUSINESS_LOG.info("Authorization expired: " + authorization.getId() +  " country: " + countryCode);
        });

    }

}
