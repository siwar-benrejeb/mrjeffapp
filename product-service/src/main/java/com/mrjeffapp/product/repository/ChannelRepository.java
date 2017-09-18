package com.mrjeffapp.product.repository;

import com.mrjeffapp.product.domain.Channel;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ChannelRepository extends PagingAndSortingRepository<Channel, String> {
}
