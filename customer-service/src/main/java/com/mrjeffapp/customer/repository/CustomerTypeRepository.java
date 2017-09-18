package com.mrjeffapp.customer.repository;

import com.mrjeffapp.customer.domain.CustomerType;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface CustomerTypeRepository extends PagingAndSortingRepository<CustomerType, String> {

    Optional<CustomerType> findByCodeAndActiveTrue(@Param("code") String code);

}
