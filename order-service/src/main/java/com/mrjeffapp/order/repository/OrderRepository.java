package com.mrjeffapp.order.repository;

import com.mrjeffapp.order.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Collection;
import java.util.Date;

@RepositoryRestResource
public interface OrderRepository extends PagingAndSortingRepository<Order, String> {

    Page<Order> findByIdIn(@Param("ids") Collection<String> ids, Pageable pageable);

    Page<Order> findByCode(@Param("code") String code, Pageable pageable);

    Page<Order> findByCodeAndCountryCode(@Param("code") String code, @Param("countryCode") String countryCode, Pageable pageable);

    Page<Order> findByCodeIn(@Param("codes") Collection<String> codes, Pageable pageable);

    Page<Order> findByCustomerIdIn(@Param("ids") Collection<String> ids, Pageable pageable);

    @Query("select o "
            + "from Order o "
            + "where o.headquarterId = :headquarterId "
            + "and o.orderStatus.id = :orderStatusId "
            + "and ((day(o.orderDate) = day(:date)) and (month(o.orderDate) = month(:date)) and (year(o.orderDate) = year(:date))) ")
    Page<Order> findDailyOrderByStatus(@Param("headquarterId") String headquarterId,
                                       @Param("orderStatusId") String orderStatusId,
                                       @DateTimeFormat(pattern = "yyyy-MM-dd") @Param("date") Date date,
                                       Pageable pageable);


    @Query("select o "
            + "from Order o "
            + "join o.visits v "
            + "where o.headquarterId = :headquarterId "
            + "and o.orderStatus.code in :orderStatusCodes "
            + "and v.visitTypeCode = :visitTypeCode "
            + "and ((day(v.date) = day(:date)) and (month(v.date) = month(:date)) and (year(v.date) = year(:date))) ")
    Page<Order> findDailyOrderByVisitTypeAndStatus(@Param("headquarterId") String headquarterId,
                                                   @Param("visitTypeCode") String visitTypeCode,
                                                   @Param("orderStatusCodes") Collection<String> orderStatusCodes,
                                                   @DateTimeFormat(pattern = "yyyy-MM-dd") @Param("date") Date date,
                                                   Pageable pageable);

    @Query("select o "
            + "from Order o "
            + "join o.visits v "
            + "where o.headquarterId = :headquarterId "
            + "and v.visitTypeCode = :visitTypeCode "
            + "and ((day(v.date) = day(:date)) and (month(v.date) = month(:date)) and (year(v.date) = year(:date))) ")
    Page<Order> findDailyOrderByVisitType(@Param("headquarterId") String headquarterId,
                                          @Param("visitTypeCode") String visitTypeCode,
                                          @DateTimeFormat(pattern = "yyyy-MM-dd") @Param("date") Date date,
                                          Pageable pageable);

}
