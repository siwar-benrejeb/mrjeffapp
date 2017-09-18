package com.mrjeffapp.order.repository;

import com.mrjeffapp.order.domain.Channel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface ChannelRepository extends PagingAndSortingRepository<Channel, String> {

    Page<Channel> findByActiveTrue(Pageable pageable);

    Optional<Channel> findByCodeAndActiveTrue(String code);

}
