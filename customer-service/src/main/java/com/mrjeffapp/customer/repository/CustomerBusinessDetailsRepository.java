package com.mrjeffapp.customer.repository;

import com.mrjeffapp.customer.domain.CustomerBusinessDetails;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CustomerBusinessDetailsRepository extends PagingAndSortingRepository<CustomerBusinessDetails, String> {

}
