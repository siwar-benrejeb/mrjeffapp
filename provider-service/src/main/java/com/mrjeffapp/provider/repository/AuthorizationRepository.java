package com.mrjeffapp.provider.repository;

import com.mrjeffapp.provider.domain.Authorization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Date;
import java.util.List;

@RepositoryRestResource
public interface AuthorizationRepository extends PagingAndSortingRepository<Authorization, String> {

    @Query("select auth "
            + "from Authorization auth "
            + "join auth.authorizationState state "
            + "where state.code = 'WAITING' ")
    Page<Authorization> findAuthorizationsByWaitingState(Pageable pageable);

    @Query("select auth "
            + "from Authorization auth "
            + "join auth.product product "
            + "join product.workOrder workOrder "
            + "where workOrder.customerId = :customerId ")
    Page<Authorization> findAuthorizationsByCustomer(@Param("customerId") String customerId, Pageable pageable);

    @Query("select auth "
            + "from Authorization auth "
            + "join auth.product product "
            + "join product.workOrder workOrder "
            + "where workOrder.deliveryDate = :eventDate "
            + "and workOrder.country = :country ")
    List<Authorization> findByDeliveryWorkOrderDateAndCountryCode(@Param("eventDate") Date eventDate,
                                                                  @Param("country") String countryCode);


}
