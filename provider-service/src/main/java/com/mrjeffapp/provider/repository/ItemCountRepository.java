package com.mrjeffapp.provider.repository;

import com.mrjeffapp.provider.domain.ItemCount;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ItemCountRepository extends PagingAndSortingRepository<ItemCount, String> {
}
