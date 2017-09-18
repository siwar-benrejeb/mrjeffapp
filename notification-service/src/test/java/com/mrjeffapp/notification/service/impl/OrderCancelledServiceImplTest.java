package com.mrjeffapp.notification.service.impl;

import com.mrjeffapp.notification.client.CustomerServiceClient;
import com.mrjeffapp.notification.client.OrderServiceClient;
import com.mrjeffapp.notification.client.model.Customer;
import com.mrjeffapp.notification.client.model.Order;
import com.mrjeffapp.notification.client.model.Visit;
import com.mrjeffapp.notification.model.Email;
import com.mrjeffapp.notification.service.EmailDestinationResolver;
import com.mrjeffapp.notification.service.EmailSenderService;
import com.mrjeffapp.notification.service.TemplateService;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.hateoas.Resource;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class OrderCancelledServiceImplTest {
    private static final String ORDER_ID = "0000a2e7-228e-465c-8bc6-ddb1e7724e5c";
    private static final String PROJECTION = "full";
    private static final String BODY_TEMPLATE_ORDER_CANCELLED = "template/mail/OrderCancelled/Body";
    private static final String SUBJECT_TEMPLATE_ORDER_CANCELLED = "template/mail/OrderCancelled/Subject";

    private static final String CUSTOMER_ID = "3039faa2-e73c-42be-9519-07315c1dfdae";
    private static final String EMAIL_TYPE_CODE = "ORDER-CANCELLED";
    private static final String EMAIL_DESTINATION = "EMAIL_DESTINATION@test.com";
    private static final String EMAIL_CONTENT = "EMAIL_CONTENT";
    private static final String PICKUP_VISIT = "PICKUP";
    private static final String EMAIL_SUBJECT1 = "Pedido ";
    private static final String EMAIL_SUBJECT2 = " cancelado ";
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
    private OrderCancelledServiceImpl orderCancelledService;

    private Order order;
    private Customer customer;
    private Visit pickUpVisit;
    private Email email;
    private Date pickupDate;

    @Before
    public void setUp() throws ParseException {
        order = new Order();
        order.setId(ORDER_ID);
        order.setCustomerId(CUSTOMER_ID);
        order.setCode(ORDER_CODE);

        pickupDate = DateUtils.parseDate("2017-02-19", new String[]{"yyyy-MM-dd"});
        pickUpVisit = new Visit();
        pickUpVisit.setDate(pickupDate);
        pickUpVisit.setVisitTypeCode(PICKUP_VISIT);

        order.setVisits(Stream.of(pickUpVisit).collect(toSet()));

        customer = new Customer();
        customer.setId(order.getCustomerId());
        customer.setLanguageCode(LANGUAGE_CODE);

        email = new Email(EMAIL_TYPE_CODE, EMAIL_DESTINATION, SUBJECT_TEMPLATE_ORDER_CANCELLED, EMAIL_CONTENT);
    }

    @Test
    public void testValidBodyTemplatePath() {
        assertThat("the body template path must be", orderCancelledService.BODY_TEMPLATE_ORDER_CANCELLED, is(BODY_TEMPLATE_ORDER_CANCELLED));
    }
    @Test
    public void testValidSubjectTemplatePath(){
        assertThat("the body template path must be", orderCancelledService.SUBJECT_TEMPLATE_ORDER_CANCELLED, is(SUBJECT_TEMPLATE_ORDER_CANCELLED));


    }

    @Test
    public void testSendOrderCancelledUnpaidEmail() {
        when(orderServiceClient.findOrderById(ORDER_ID, PROJECTION)).thenReturn(new Resource<Order>(order));
        when(customerServiceClient.findCustomerById(CUSTOMER_ID, PROJECTION)).thenReturn(new Resource<Customer>(customer));
        when(templateService.renderBodyTemplate(eq(BODY_TEMPLATE_ORDER_CANCELLED),eq(LANGUAGE_CODE), any(Map.class))).thenReturn(EMAIL_CONTENT);
        when(templateService.renderSubjectTemplate(eq(SUBJECT_TEMPLATE_ORDER_CANCELLED),eq(LANGUAGE_CODE), any(Map.class))).thenReturn(SUBJECT_TEMPLATE_ORDER_CANCELLED);

        when(emailSenderService.sendEmail(email)).thenReturn(email);
        when(emailDestinationResolver.getDestinationEmail(customer)).thenReturn(EMAIL_DESTINATION);

        orderCancelledService.sendOrderCancelledUnpaidEmail(ORDER_ID);

        verify(emailSenderService).sendEmail(email);
    }

}
