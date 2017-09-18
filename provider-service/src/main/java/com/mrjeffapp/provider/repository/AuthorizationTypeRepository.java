package com.mrjeffapp.provider.repository;

import com.mrjeffapp.provider.domain.AuthorizationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AuthorizationTypeRepository extends PagingAndSortingRepository<AuthorizationType, String> {

    Page<AuthorizationType> findByActiveTrue(Pageable pageable);

    AuthorizationType findByCode(String code);

}
