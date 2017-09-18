package com.mrjeffapp.notification.service.impl;

import com.mrjeffapp.notification.client.CustomerServiceClient;
import com.mrjeffapp.notification.client.OrderServiceClient;
import com.mrjeffapp.notification.client.ProductServiceClient;
import com.mrjeffapp.notification.client.model.*;
import com.mrjeffapp.notification.model.Email;
import com.mrjeffapp.notification.service.EmailDestinationResolver;
import com.mrjeffapp.notification.service.EmailSenderService;
import com.mrjeffapp.notification.service.TemplateService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderCreatedEmailServiceImplTest {

    private static final String ORDER_ID = "0000a2e7-228e-465c-8bc6-ddb1e7724e5c";
    private static final String PROJECTION = "full";
    private static final String BODY_TEMPLATE_PATH = "template/mail/OrderCreation/Body";
    private static final String SUBJECT_TEMPLATE_PATH = "template/mail/OrderCreation/Subject";

    private static final String CUSTOMER_ID = "3039faa2-e73c-42be-9519-07315c1dfdae";
    private static final String CUSTOMER_LANGUAGE = "ES";
    private static final String PICKUP_ADDRESS = "PICKUP_ADDRESS";
    private static final String DELIVERY_ADDRESS = "DELIVERY_ADDRESS";
    private static final String PICKUP_VISIT = "PICKUP";
    private static final String DELIVERY_VISIT = "DELIVERY";
    private static final String PRODUCT_ID = "5298630a-f267-4d4e-b899-01388c150e2a";
    private static final Double PRICE = 1.1;
    private static final Integer QUANTITY = 12;
    private static final String EMAIL_TYPE_CODE = "ORDER-CREATION";
    private static final String EMAIL_DESTINATION = "EMAIL_DESTINATION@test.com";
    private static final String EMAIL_SUBJECT = "Recibo de tu pedido en MrJeff : ";
    private static final String EMAIL_CONTENT = "EMAIL_CONTENT";
    private static final String ORDER_CODE = "ES46239";
    private static final Double ORDER_TOTAL_PRICE = 1.1;


    @Mock
    private OrderServiceClient orderServiceClient;

    @Mock
    private CustomerServiceClient customerServiceClient;

    @Mock
    private ProductServiceClient productServiceClient;

    @Mock
    private EmailDestinationResolver emailDestinationResolver;

    @Mock
    private EmailSenderService emailSenderService;

    @Mock
    private TemplateService templateService;


    @InjectMocks
    private OrderCreationEmailServiceImpl orderCreationEmailServiceImpl;
    private Order order;
    private Customer customer;
    private Product product;
    private Address pickupAddress;
    private Address deliveryAddress;
    private Visit pickUpVisit;
    private Visit deliveryVisit;
    private ProductOrder productOrder;
    private Email email;

    @Before
    public void setUp() {
        order = new Order();
        order.setId(ORDER_ID);
        order.setCustomerId(CUSTOMER_ID);
        order.setCode(ORDER_CODE);
        order.setTotalPrice(ORDER_TOTAL_PRICE);

        customer = new Customer();
        customer.setId(order.getCustomerId());
        customer.setEmail(EMAIL_DESTINATION);
        customer.setLanguageCode(CUSTOMER_LANGUAGE);

        productOrder = new ProductOrder();
        productOrder.setProductId(PRODUCT_ID);
        productOrder.setQuantity(QUANTITY);
        Set<ProductOrder> productOrders = new HashSet<>();
        productOrders.add(productOrder);
        order.setProductsOrder(productOrders);

        product = new Product();
        product.setId(PRODUCT_ID);
        product.setPrice(PRICE);

        Set<Visit> visitSet = new HashSet<>();
        pickupAddress = new Address();
        pickupAddress.setAddress(PICKUP_ADDRESS);

        deliveryAddress = new Address();
        deliveryAddress.setAddress(DELIVERY_ADDRESS);

        pickUpVisit = new Visit();
        pickUpVisit.setVisitTypeCode(PICKUP_VISIT);
        pickUpVisit.setAddress(pickupAddress);

        deliveryVisit = new Visit();
        deliveryVisit.setVisitTypeCode(DELIVERY_VISIT);
        deliveryVisit.setAddress(deliveryAddress);

        visitSet.add(pickUpVisit);
        visitSet.add(deliveryVisit);
        order.setVisits(visitSet);

        email = new Email(EMAIL_TYPE_CODE, EMAIL_DESTINATION, SUBJECT_TEMPLATE_PATH, EMAIL_CONTENT);
    }


    @Test
    public void testValidBodyTemplatePath() {
        assertThat("the body template path should be : ", orderCreationEmailServiceImpl.BODY_TEMPLATE_ORDER_CREATED, is(BODY_TEMPLATE_PATH));
    }
    @Test
    public void testValidSubjectTemplatePath() {
        assertThat("the subject template path should be : ", orderCreationEmailServiceImpl.SUBJECT_TEMPLATE_ORDER_CREATED, is(SUBJECT_TEMPLATE_PATH));
    }

    @Test
    public void testSendOrderEmail() {
        when(orderServiceClient.findOrderById(ORDER_ID, PROJECTION)).thenReturn(new Resource<Order>(order));
        when(customerServiceClient.findCustomerById(CUSTOMER_ID, PROJECTION)).thenReturn(new Resource<Customer>(customer));
        when(productServiceClient.findProductsByIdIn(PRODUCT_ID, Integer.MAX_VALUE)).thenReturn(new PagedResources<Product>(Stream.of(product).collect(toSet()), null));
        when(templateService.renderBodyTemplate(eq(BODY_TEMPLATE_PATH), eq(CUSTOMER_LANGUAGE), any(Map.class))).thenReturn(EMAIL_CONTENT);
        when(templateService.renderSubjectTemplate(eq(SUBJECT_TEMPLATE_PATH), eq(CUSTOMER_LANGUAGE), any(Map.class))).thenReturn(SUBJECT_TEMPLATE_PATH);

        when(emailSenderService.sendEmail(email)).thenReturn(email);
        when(emailDestinationResolver.getDestinationEmail(customer)).thenReturn(EMAIL_DESTINATION);

        orderCreationEmailServiceImpl.sendMail(ORDER_ID);

        verify(emailSenderService).sendEmail(email);
    }


}
