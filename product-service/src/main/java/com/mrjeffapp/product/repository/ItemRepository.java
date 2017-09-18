package com.mrjeffapp.product.repository;

import com.mrjeffapp.product.domain.Item;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ItemRepository extends PagingAndSortingRepository<Item, String> {
}
