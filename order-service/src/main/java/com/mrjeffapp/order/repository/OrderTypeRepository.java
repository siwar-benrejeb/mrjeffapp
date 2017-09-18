package com.mrjeffapp.order.repository;

import com.mrjeffapp.order.domain.OrderType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface OrderTypeRepository extends PagingAndSortingRepository<OrderType, String> {

    Page<OrderType> findByActiveTrue(Pageable pageable);

    Optional<OrderType> findByCodeAndActiveTrue(@Param("code") String code);

}
