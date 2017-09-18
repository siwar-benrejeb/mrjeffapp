package com.mrjeffapp.order.repository;

import com.mrjeffapp.order.domain.Visit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface VisitRepository extends PagingAndSortingRepository<Visit, String> {

    @Query("select v " +
            "from Visit v " +
            "join v.order o " +
            "where v.id = :visitId " +
            "and o.customerId = :customerId ")
    Optional<Visit> findByOrderCustomerIdAndId(@Param("customerId") String customerId, @Param("visitId") String visitId);

}
