package com.mrjeffapp.order.repository;

import com.mrjeffapp.order.domain.ProductOrder;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ProductOrderRepository extends PagingAndSortingRepository<ProductOrder, String> {
}
