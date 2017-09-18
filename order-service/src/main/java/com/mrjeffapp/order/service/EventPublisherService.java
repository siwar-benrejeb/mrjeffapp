package com.mrjeffapp.order.service;

import com.mrjeffapp.event.OrderEvent;
import com.mrjeffapp.event.VisitEvent;
import com.mrjeffapp.order.domain.Order;
import com.mrjeffapp.order.domain.Visit;

public interface EventPublisherService {

    OrderEvent sendOrderEventByOrderStatusCode(Order order);

    VisitEvent sendVisitCreatedEvent(Visit visit);

    VisitEvent sendVisitRelocatedEvent(Visit visit);

}
