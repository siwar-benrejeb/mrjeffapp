package com.mrjeffapp.provider.repository;

import com.mrjeffapp.provider.domain.Incident;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface IncidentRepository extends PagingAndSortingRepository<Incident, String> {
}
