package com.mrjeffapp.notification.service.impl;

import com.mrjeffapp.notification.client.CustomerServiceClient;
import com.mrjeffapp.notification.client.OrderServiceClient;
import com.mrjeffapp.notification.client.model.Customer;
import com.mrjeffapp.notification.client.model.Order;
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
import org.springframework.hateoas.Resource;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductAddedSubscriptionImplTest {
    private static final String ORDER_ID = "0000a2e7-228e-465c-8bc6-ddb1e7724e5c";
    private static final String PROJECTION = "full";
    private static final String BODY_TEMPLATE_PRODUCT_SUBSCRIPTION = "template/mail/ProductAddedSubscription/Body";
    private static final String SUBJECT_TEMPLATE_PRODUCT_SUBSCRIPTION = "template/mail/ProductAddedSubscription/Subject";

    private static final String CUSTOMER_ID = "3039faa2-e73c-42be-9519-07315c1dfdae";
    private static final String EMAIL_TYPE_CODE = "PRODUCT_ADDED_SUBSCRIPTION";
    private static final String EMAIL_DESTINATION = "EMAIL_DESTINATION@test.com";
    private static final String EMAIL_SUBJECT = " Prendas añadidas en el pedido : ";
    private static final String EMAIL_CONTENT = "EMAIL_CONTENT";
    private static final String ORDER_CODE = "ES46239";
    private static final String LANGUAGE_CODE = "ES";



    @Mock
    private OrderServiceClient orderServiceClient;

    @Mock
    private CustomerServiceClient customerServiceClient;

    @Mock
    private TemplateService templateService;

    @Mock
    private EmailSenderService emailSenderService;

    @Mock
    private EmailDestinationResolver emailDestinationResolver;

    @InjectMocks
    private ProductAddedSubscriptionImpl productAddedSubscription;
    private Order order;
    private Customer customer;
    private Email email;

    @Before
    public void setUp() {
        order = new Order();
        order.setId(ORDER_ID);
        order.setCustomerId(CUSTOMER_ID);
        order.setCode(ORDER_CODE);

        customer = new Customer();
        customer.setId(order.getCustomerId());
        customer.setLanguageCode(LANGUAGE_CODE);

        email = new Email(EMAIL_TYPE_CODE, EMAIL_DESTINATION, SUBJECT_TEMPLATE_PRODUCT_SUBSCRIPTION, EMAIL_CONTENT);


    }


    @Test
    public void testValidBodyTemplatePath() {
        assertThat("the body template path must be", productAddedSubscription.BODY_TEMPLATE_PRODUCT_SUBSCRIPTION, is(BODY_TEMPLATE_PRODUCT_SUBSCRIPTION));


    }
    @Test
    public void testValidSubjectTemplatePath() {
        assertThat("the subject template path must be", productAddedSubscription.SUBJECT_TEMPLATE_PRODUCT_SUBSCRIPTION, is(SUBJECT_TEMPLATE_PRODUCT_SUBSCRIPTION));


    }

    @Test
    public void testSendProductAddedSubscriptionEmail() {
        when(orderServiceClient.findOrderById(ORDER_ID, PROJECTION)).thenReturn(new Resource<Order>(order));
        when(customerServiceClient.findCustomerById(CUSTOMER_ID, PROJECTION)).thenReturn(new Resource<Customer>(customer));
        when(templateService.renderBodyTemplate(eq(BODY_TEMPLATE_PRODUCT_SUBSCRIPTION),eq(LANGUAGE_CODE), any(Map.class))).thenReturn(EMAIL_CONTENT);
        when(templateService.renderSubjectTemplate(eq(SUBJECT_TEMPLATE_PRODUCT_SUBSCRIPTION),eq(LANGUAGE_CODE), any(Map.class))).thenReturn(SUBJECT_TEMPLATE_PRODUCT_SUBSCRIPTION);

        when(emailSenderService.sendEmail(email)).thenReturn(email);
        when(emailDestinationResolver.getDestinationEmail(customer)).thenReturn(EMAIL_DESTINATION);

        productAddedSubscription.sendProductAddedSubscriptionEmail(ORDER_ID);

        verify(emailSenderService).sendEmail(email);


    }
}
