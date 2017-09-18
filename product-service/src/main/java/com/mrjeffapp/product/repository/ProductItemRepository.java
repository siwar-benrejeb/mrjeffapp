package com.mrjeffapp.product.repository;

import com.mrjeffapp.product.domain.ProductItem;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ProductItemRepository extends PagingAndSortingRepository<ProductItem, String> {
}
