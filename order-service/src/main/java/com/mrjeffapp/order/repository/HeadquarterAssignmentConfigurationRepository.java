package com.mrjeffapp.order.repository;

import com.mrjeffapp.order.domain.HeadquarterAssignmentConfiguration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeadquarterAssignmentConfigurationRepository extends CrudRepository<HeadquarterAssignmentConfiguration, String> {

    HeadquarterAssignmentConfiguration findByPostalCodeIdAndActiveTrue(String postalCodeId);

}
