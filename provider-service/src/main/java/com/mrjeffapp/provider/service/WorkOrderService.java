package com.mrjeffapp.provider.service;

import com.mrjeffapp.provider.domain.WorkOrder;

import java.util.Date;

public interface WorkOrderService {

    WorkOrder createWorkOrder(String orderId, String orderCode, String customerId, String cityId, Date pickupDate, Date deliveryDate);

}
