package com.mrjeffapp.order.service.impl;

import com.mrjeffapp.event.OrderEvent;
import com.mrjeffapp.event.VisitEvent;
import com.mrjeffapp.order.domain.Order;
import com.mrjeffapp.order.domain.OrderStatus;
import com.mrjeffapp.order.domain.Visit;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.Date;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class EventPublisherServiceTest {
    private static final String ORDER_ID = "1L";
    private static final String COUPON_ID = "2L";
    private static final String CUSTOMER_ID = "3L";
    private static final String POSTAL_CODE_ID = "4L";
    private static final String VISIT_PICKUP_ID = "5L";
    private static final String VISIT_DELIVERY_ID = "6L";
    private static final String VISIT_TYPE_CODE_PICKUP = "PICKUP";
    private static final String VISIT_TYPE_CODE_DELIVERY = "DELIVERY";
    private static final String ADDRESS_DELIVERY_ID = "6L";
    private static final String ADDRESS_PICKUP_ID = "7L";
    private static final String VISIT_PICKUP_POSTAL_CODE_ID = "8L";
    private static final String VISIT_TIME_TABLE_TIME_SLOT_ID = "9L";
    private static final String ORDER_STATUS_CODE_CREATED = "CREATED";
    private static final String ORDER_STATUS_CODE_PICKUP = "PICKUP";

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private EventPublisherServiceImpl eventPublisherServiceImpl;

    private Order order;

    private Visit pickupVisit;

    private Visit deliveryVisit;

    private OrderStatus orderStatus;

    private Date orderDate;

    private Date pickupDate;

    private Date deliveryDate;


    @Before
    public void setUp() throws Exception {
        orderDate = DateUtils.parseDate("2017-02-10", new String[] {"yyyy-MM-dd"});
        pickupDate = DateUtils.parseDate("2017-02-19", new String[] {"yyyy-MM-dd"});
        deliveryDate = DateUtils.parseDate("2017-02-21", new String[] {"yyyy-MM-dd"});

        orderStatus = new OrderStatus();
        orderStatus.setCode(ORDER_STATUS_CODE_CREATED);

        pickupVisit = new Visit();
        pickupVisit.setId(VISIT_PICKUP_ID);
        pickupVisit.setAddressId(ADDRESS_PICKUP_ID);
        pickupVisit.setVisitTypeCode(VISIT_TYPE_CODE_PICKUP);
        pickupVisit.setDate(pickupDate);
        pickupVisit.setPostalCodeId(VISIT_PICKUP_POSTAL_CODE_ID);
        pickupVisit.setTimeTableTimeSlotId(VISIT_TIME_TABLE_TIME_SLOT_ID);

        deliveryVisit = new Visit();
        deliveryVisit.setId(VISIT_DELIVERY_ID);
        deliveryVisit.setAddressId(ADDRESS_DELIVERY_ID);
        deliveryVisit.setVisitTypeCode(VISIT_TYPE_CODE_DELIVERY);
        deliveryVisit.setDate(deliveryDate);

        order = new Order();
        order.setId(ORDER_ID);
        order.setCouponId(COUPON_ID);
        order.setCustomerId(CUSTOMER_ID);
        order.setPostalCodeId(POSTAL_CODE_ID);
        order.setOrderDate(orderDate);
        order.setOrderStatus(orderStatus);
        order.setVisits(Stream.of(pickupVisit, deliveryVisit).collect(toSet()));

        pickupVisit.setOrder(order);
        deliveryVisit.setOrder(order);
    }

    @Test
    public void testOrderEventStatusCreated() {
        orderStatus.setCode(ORDER_STATUS_CODE_CREATED);

        OrderEvent event = eventPublisherServiceImpl.sendOrderEventByOrderStatusCode(order);

        verify(rabbitTemplate).convertAndSend("order-service/amq.topic","order.state.created", event);

        assertThat("Order id should be set", event.getOrderId(), is(ORDER_ID));
        assertThat("Order status should be set", event.getOrderStatusCode(), is(ORDER_STATUS_CODE_CREATED));
        assertThat("Coupon id should be set", event.getCouponId(), is(COUPON_ID));
        assertThat("Customer id should be set", event.getCustomerId(), is(CUSTOMER_ID));
        assertThat("Postal code id should be set", event.getPostalCodeId(), is(POSTAL_CODE_ID));
        assertThat("Order date should be set", event.getOrderDate(), is(orderDate));
        assertThat("Pickup date should be obtained from visits", event.getPickupDate(), is(pickupDate));
        assertThat("Delivery date should be obtained from visits", event.getDeliveryDate(), is(deliveryDate));
        assertThat("Event date should be set", event.getEventDate(), is(notNullValue()));
    }

    @Test
    public void testOrderEventStatusCreatedWithoutPickupVisit() {
        order.setVisits(Stream.of(deliveryVisit).collect(toSet()));

        orderStatus.setCode(ORDER_STATUS_CODE_CREATED);

        OrderEvent event = eventPublisherServiceImpl.sendOrderEventByOrderStatusCode(order);

        verify(rabbitTemplate).convertAndSend("order-service/amq.topic","order.state.created", event);

        assertThat("Order id should be set", event.getOrderId(), is(ORDER_ID));
        assertThat("Order status should be set", event.getOrderStatusCode(), is(ORDER_STATUS_CODE_CREATED));
        assertThat("Coupon id should be set", event.getCouponId(), is(COUPON_ID));
        assertThat("Customer id should be set", event.getCustomerId(), is(CUSTOMER_ID));
        assertThat("Postal code id should be set", event.getPostalCodeId(), is(POSTAL_CODE_ID));
        assertThat("Order date should be set", event.getOrderDate(), is(orderDate));
        assertNull("Pickup date should be obtained from visits", event.getPickupDate());
        assertThat("Delivery date should be obtained from visits", event.getDeliveryDate(), is(deliveryDate));
        assertThat("Event date should be set", event.getEventDate(), is(notNullValue()));
    }

    @Test
    public void testOrderEventStatusCreatedWithoutDeliveryVisit() {
        order.setVisits(Stream.of(pickupVisit).collect(toSet()));

        orderStatus.setCode(ORDER_STATUS_CODE_CREATED);

        OrderEvent event = eventPublisherServiceImpl.sendOrderEventByOrderStatusCode(order);

        verify(rabbitTemplate).convertAndSend("order-service/amq.topic","order.state.created", event);

        assertThat("Order id should be set", event.getOrderId(), is(ORDER_ID));
        assertThat("Order status should be set", event.getOrderStatusCode(), is(ORDER_STATUS_CODE_CREATED));
        assertThat("Coupon id should be set", event.getCouponId(), is(COUPON_ID));
        assertThat("Customer id should be set", event.getCustomerId(), is(CUSTOMER_ID));
        assertThat("Postal code id should be set", event.getPostalCodeId(), is(POSTAL_CODE_ID));
        assertThat("Order date should be set", event.getOrderDate(), is(orderDate));
        assertThat("Pickup date should be obtained from visits", event.getPickupDate(), is(pickupDate));
        assertNull("Delivery date should be obtained from visits", event.getDeliveryDate());
        assertThat("Event date should be set", event.getEventDate(), is(notNullValue()));
    }

    @Test
    public void testOrderEventStatusPickup() {
        orderStatus.setCode(ORDER_STATUS_CODE_PICKUP);

        OrderEvent event = eventPublisherServiceImpl.sendOrderEventByOrderStatusCode(order);

        verify(rabbitTemplate).convertAndSend("order-service/amq.topic","order.state.pickup", event);
    }

    @Test
    public void testVisitCreated() {
        VisitEvent event = eventPublisherServiceImpl.sendVisitCreatedEvent(pickupVisit);

        verify(rabbitTemplate).convertAndSend("order-service/amq.topic","visit.state.created", event);

        assertThat("Visit id should be set", event.getVisitId(), is(VISIT_PICKUP_ID));
        assertThat("Customer id should be set", event.getCustomerId(), is(CUSTOMER_ID));
        assertThat("OrderId should be set", event.getOrderId(), is(ORDER_ID));
        assertThat("Visit type code should be set", event.getVisitTypeCode(), is(VISIT_TYPE_CODE_PICKUP));
        assertThat("Address id should be set", event.getAddressId(), is(ADDRESS_PICKUP_ID));
        assertThat("Visit date should be set", event.getDate(), is(pickupDate));
        assertThat("Visit postal code id", event.getPostalCodeId(), is(VISIT_PICKUP_POSTAL_CODE_ID));
        assertThat("Visit time slot id", event.getTimeTableTimeSlotId(), is(VISIT_TIME_TABLE_TIME_SLOT_ID));
        assertThat("Event date should be set", event.getEventDate(), is(notNullValue()));
    }

    @Test
    public void testVisitRelocated() {
        VisitEvent event = eventPublisherServiceImpl.sendVisitRelocatedEvent(pickupVisit);

        verify(rabbitTemplate).convertAndSend("order-service/amq.topic","visit.state.relocated", event);

        assertThat("Visit id should be set", event.getVisitId(), is(VISIT_PICKUP_ID));
        assertThat("Customer id should be set", event.getCustomerId(), is(CUSTOMER_ID));
        assertThat("OrderId should be set", event.getOrderId(), is(ORDER_ID));
        assertThat("Visit type code should be set", event.getVisitTypeCode(), is(VISIT_TYPE_CODE_PICKUP));
        assertThat("Address id should be set", event.getAddressId(), is(ADDRESS_PICKUP_ID));
        assertThat("Visit date should be set", event.getDate(), is(pickupDate));
        assertThat("Visit postal code id", event.getPostalCodeId(), is(VISIT_PICKUP_POSTAL_CODE_ID));
        assertThat("Visit time slot id", event.getTimeTableTimeSlotId(), is(VISIT_TIME_TABLE_TIME_SLOT_ID));
        assertThat("Event date should be set", event.getEventDate(), is(notNullValue()));
    }

}
