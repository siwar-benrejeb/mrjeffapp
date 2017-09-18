package com.mrjeffapp.provider.service.impl;

import com.mrjeffapp.provider.domain.Provider;
import com.mrjeffapp.provider.domain.ProviderAssignmentConfiguration;
import com.mrjeffapp.provider.repository.ProviderAssignmentConfigurationRepository;
import com.mrjeffapp.provider.service.ProviderAssignmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProviderAssignmentServiceImpl implements ProviderAssignmentService {
    private static final Logger LOG = LoggerFactory.getLogger(ProviderAssignmentServiceImpl.class);

    private final ProviderAssignmentConfigurationRepository providerCityAssignmentRepository;

    @Autowired
    public ProviderAssignmentServiceImpl(ProviderAssignmentConfigurationRepository providerAssignmentConfigurationRepository) {
        this.providerCityAssignmentRepository = providerAssignmentConfigurationRepository;
    }

    public Provider findProviderAssigned(String postalCodeId) {
        LOG.debug("WorkOrder postalCodeId: {}", postalCodeId);

        ProviderAssignmentConfiguration providerAssignmentConfiguration = providerCityAssignmentRepository.findByPostalCodeIdAndActiveTrue(postalCodeId);

        Provider provider;
        if(providerAssignmentConfiguration == null) {
            LOG.error("Not found provider for postalCodeId:{}", postalCodeId);
            provider = null;

        } else {
            provider = providerAssignmentConfiguration.getProvider();
        }

        return provider;
    }

}
