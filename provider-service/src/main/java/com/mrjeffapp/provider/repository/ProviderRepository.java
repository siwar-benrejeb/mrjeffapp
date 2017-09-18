package com.mrjeffapp.provider.repository;

import com.mrjeffapp.provider.domain.Provider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Set;

@RepositoryRestResource
public interface ProviderRepository extends PagingAndSortingRepository<Provider, String> {

    Page<Provider> findByActiveTrue(Pageable pageable);

    Page<Provider> findProviderByIdIn(@Param("ids") Set<String> ids, Pageable pageable);

}
