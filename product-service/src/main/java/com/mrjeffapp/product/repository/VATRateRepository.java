package com.mrjeffapp.product.repository;

import com.mrjeffapp.product.domain.VATRate;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface VATRateRepository extends PagingAndSortingRepository<VATRate, String> {
}
