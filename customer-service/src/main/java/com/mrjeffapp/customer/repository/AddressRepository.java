package com.mrjeffapp.customer.repository;

import com.mrjeffapp.customer.domain.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Collection;
import java.util.Optional;

@RepositoryRestResource
public interface AddressRepository extends PagingAndSortingRepository<Address, String> {

    Page<Address> findByIdIn(@Param("ids") Collection<String> ids, Pageable pageable);

    @Query("select a " +
            "from Address a " +
            "where a.customer.id = :customerId " +
            "and a.active = true")
    Collection<Address> findCustomerAddresses(@Param("customerId") String id);

    Page<Address> findByCustomerIdAndIdInAndActiveTrue(@Param("customerId") String customerId, @Param("ids") Collection<String> ids, Pageable pageable);
    Optional<Address> findByCustomerIdAndIdAndActiveTrue(@Param("customerId") String customerId, @Param("id") String id);

    @RestResource(exported = false)
    @Modifying
    @Query("update Address a set a.defaultPickup = false where a.customer.id = :customerId")
    int setDisableDefaultPickupAddresses(@Param("customerId") String customerId);

    @RestResource(exported = false)
    @Modifying
    @Query("update Address a set a.defaultDelivery = false where a.customer.id = :customerId")
    int setDisableDefaultDeliveryAddresses(@Param("customerId") String customerId);

    @RestResource(exported = false)
    @Modifying
    @Query("update Address a set a.defaultBilling = false where a.customer.id = :customerId")
    int setDisableDefaultBillingAddresses(@Param("customerId") String customerId);

}
