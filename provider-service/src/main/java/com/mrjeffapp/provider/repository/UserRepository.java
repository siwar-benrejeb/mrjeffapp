package com.mrjeffapp.provider.repository;

import com.mrjeffapp.provider.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface UserRepository extends PagingAndSortingRepository<User, String> {

    Optional<User> findUserByEmailAndActiveTrue(String email);

}
