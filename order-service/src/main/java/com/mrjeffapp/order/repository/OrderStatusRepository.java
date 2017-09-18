package com.mrjeffapp.order.repository;

import com.mrjeffapp.order.domain.OrderStatus;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface OrderStatusRepository extends PagingAndSortingRepository<OrderStatus, String> {

    Optional<OrderStatus> findByCodeAndActiveTrue(@Param("code") String code);

}
