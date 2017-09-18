package com.mrjeffapp.provider.repository;

import com.mrjeffapp.provider.domain.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface NotificationRepository extends PagingAndSortingRepository<Notification, String> {

    Page<Notification> findByProviderIdAndState(
            @Param("providerId") String providerId,
            @Param("state") String state,
            Pageable pageable);
}
