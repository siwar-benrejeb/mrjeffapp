package com.mrjeffapp.provider.repository;

import com.mrjeffapp.provider.domain.IncidentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface IncidentTypeRepository extends PagingAndSortingRepository<IncidentType, String> {

    Page<IncidentType> findByActiveTrue(Pageable pageable);

}
