package com.mrjeffapp.order.repository;

import com.mrjeffapp.order.domain.OrderStatusTracking;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface OrderStatusTrackingRepository extends PagingAndSortingRepository<OrderStatusTracking, String> {
}
