package com.mrjeffapp.provider.repository;

import com.mrjeffapp.provider.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ProductRepository extends PagingAndSortingRepository<Product, String> {

    @Query("select p from Product p where p.workOrder.id = :workOrderId")
    Page<Product> findByWorkOrderId(@Param("workOrderId") String orderId, Pageable pageable);

}