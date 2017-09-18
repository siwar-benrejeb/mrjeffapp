package com.mrjeffapp.product.repository;

import com.mrjeffapp.product.domain.ProductType;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ProductTypeRepository extends PagingAndSortingRepository<ProductType, String> {
}
