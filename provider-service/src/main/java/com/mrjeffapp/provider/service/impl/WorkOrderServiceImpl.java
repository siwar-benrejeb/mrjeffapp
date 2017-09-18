package com.mrjeffapp.provider.service.impl;

import com.mrjeffapp.provider.domain.Provider;
import com.mrjeffapp.provider.domain.WorkOrder;
import com.mrjeffapp.provider.domain.WorkOrderState;
import com.mrjeffapp.provider.repository.WorkOrderRepository;
import com.mrjeffapp.provider.repository.WorkOrderStateRepository;
import com.mrjeffapp.provider.service.EventPublisherService;
import com.mrjeffapp.provider.service.ProviderAssignmentService;
import com.mrjeffapp.provider.service.WorkOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;

import static com.mrjeffapp.provider.config.Constants.WORK_ORDER_STATUS_INCOMING;

@Service
public class WorkOrderServiceImpl implements WorkOrderService {
    private static final Logger LOG = LoggerFactory.getLogger(WorkOrderServiceImpl.class);

    private final EventPublisherService eventPublisherService;

    private final ProviderAssignmentService providerAssignmentService;

    private final WorkOrderRepository workOrderRepository;

    private final WorkOrderStateRepository workOrderStateRepository;

    @Autowired
    public WorkOrderServiceImpl(EventPublisherService eventPublisherService,
                                ProviderAssignmentService providerAssignmentService,
                                WorkOrderRepository workOrderRepository,
                                WorkOrderStateRepository workOrderStateRepository) {

        this.eventPublisherService = eventPublisherService;
        this.providerAssignmentService = providerAssignmentService;
        this.workOrderRepository = workOrderRepository;
        this.workOrderStateRepository = workOrderStateRepository;
    }

    @Transactional
    public WorkOrder createWorkOrder(String orderId, String orderCode, String customerId, String postalCodeId, Date pickupDate, Date deliveryDate) {
        LOG.debug("Creating work order orderId: {}, customerId: {}, postalCodeId: {}, workOderDate: {} ", orderId, customerId, postalCodeId, pickupDate);

        Provider provider = providerAssignmentService.findProviderAssigned(postalCodeId);

        WorkOrder workOrder;
        if(provider != null) {
            workOrder = createWorkOrderWithProvider(orderId, orderCode, customerId, postalCodeId, pickupDate, deliveryDate, provider);

        } else {
            workOrder = null;
            LOG.error("Not found valid provider orderId: {}, postalCodeId: {}", orderId, postalCodeId);

        }

        return workOrder;

    }

    private WorkOrder createWorkOrderWithProvider(String orderId, String orderCode, String customerId, String cityId, Date pickupDate, Date deliveryDate, Provider provider) {
        WorkOrder workOrderTransient = new WorkOrder();
        workOrderTransient.setOrderId(orderId);
        workOrderTransient.setOrderCode(orderCode);
        workOrderTransient.setCustomerId(customerId);
        workOrderTransient.setPickupDate(pickupDate);
        workOrderTransient.setDeliveryDate(deliveryDate);
        workOrderTransient.setProvider(provider);
        workOrderTransient.setProducts(new HashSet<>());

        WorkOrderState workOrderState = workOrderStateRepository.findByActiveTrueAndCode(WORK_ORDER_STATUS_INCOMING);
        workOrderTransient.setWorkOrderState(workOrderState);

        WorkOrder workOrder = workOrderRepository.save(workOrderTransient);

        LOG.debug("Sending event provider assigned orderId:{}, provider:{}", orderId, provider.getId());
        eventPublisherService.sendProviderOrderAssignmentEvent(orderId, provider.getId());

        return workOrder;
    }
}
