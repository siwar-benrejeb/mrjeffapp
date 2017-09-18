package com.mrjeffapp.customer.repository;

import com.mrjeffapp.customer.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Collection;
import java.util.Optional;

@RepositoryRestResource
public interface CustomerRepository extends PagingAndSortingRepository<Customer, String> {

    Page<Customer> findByIdIn(@Param("ids") Collection<String> ids, Pageable pageable);

    @RestResource(exported = false)
    Optional<Customer> findByEmailAndEnabledTrue(@Param("email") String email);

    Page<Customer> findByEmailAndEnabledTrue(@Param("email") String email, Pageable pageable);
    Optional<Customer> findByEmail(@Param("email") String email);

    Page<Customer> findByIdAndEnabledTrue(@Param("id") String id, Pageable pageable);
    Optional<Customer> findById(@Param("id") String id);

    Page<Customer> findByPhoneNumber(@Param("phoneNumber") String phoneNumber, Pageable pageable);


    @Query("select c " +
            "from Customer c " +
            "join c.customerBusinessDetails cd " +
            "where c.customerType.code = 'BUSINESS' " +
            "and cd.businessId = :businessId " +
            "and c.enabled = true ")
    Page<Customer> findCustomerByBusinessIdAndEnabledTrue(@Param("businessId") String businessId, Pageable pageable);

}
