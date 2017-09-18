package com.mrjeffapp.provider.repository;

import com.mrjeffapp.provider.domain.ProviderAssignmentConfiguration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderAssignmentConfigurationRepository extends CrudRepository<ProviderAssignmentConfiguration, String> {

    public ProviderAssignmentConfiguration findByPostalCodeIdAndActiveTrue(String postalCodeId);

}
