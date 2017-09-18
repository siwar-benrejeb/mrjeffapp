package com.mrjeffapp.provider.repository;

import com.mrjeffapp.provider.domain.AuthorizationState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AuthorizationStateRepository extends PagingAndSortingRepository<AuthorizationState, String> {

    Page<AuthorizationState> findByActiveTrue(Pageable pageable);

    AuthorizationState findByCode(String code);

}
