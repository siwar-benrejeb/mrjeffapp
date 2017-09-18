package com.mrjeffapp.product.repository;

import com.mrjeffapp.product.domain.Periodicity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PeriodicityRepository extends PagingAndSortingRepository<Periodicity, String> {
}
