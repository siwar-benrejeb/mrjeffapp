package com.mrjeffapp.provider.repository;

import com.mrjeffapp.provider.domain.WorkOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@RepositoryRestResource
public interface WorkOrderRepository extends PagingAndSortingRepository<WorkOrder, String> {

    @Query("select wo "
            + "from WorkOrder wo "
            + "where wo.provider.id = :providerId "
            + "and wo.pickupDate = :date ")
    Page<WorkOrder> findProviderDailyPickupWorkOrder(@Param("providerId") String providerId,
                                                     @DateTimeFormat(pattern = "yyyy-MM-dd") @Param("date") Date date,
                                                     Pageable pageable);

    @Query("select wo "
            + "from WorkOrder wo "
            + "where wo.provider.id = :providerId "
            + "and wo.deliveryDate = :date ")
    Page<WorkOrder> findProviderDailyDeliveryWorkOrder(@Param("providerId") String providerId,
                                                       @DateTimeFormat(pattern = "yyyy-MM-dd") @Param("date") Date date,
                                                       Pageable pageable);

    Page<WorkOrder> findByOrderIdAndProviderId(@Param("orderId") String orderId, @Param("providerId") String providerId, Pageable pageable);

    Page<WorkOrder> findByOrderCodeAndProviderId(@Param("code") String orderId, @Param("providerId") String providerId, Pageable pageable);



}
