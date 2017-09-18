package com.mrjeffapp.notification.service.impl;

import com.mrjeffapp.notification.client.CustomerServiceClient;
import com.mrjeffapp.notification.client.model.Customer;
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
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)

public class CustomerCreatedEmailServiceImplTest {
    private static final String PROJECTION = "full";
    private static final String BODY_TEMPLATE_PATH = "template/mail/CustomerCreation/Body";
    private static final String SUBJECT_TEMPLATE_PATH = "template/mail/CustomerCreation/Subject";

    private static final String CUSTOMER_ID = "3039faa2-e73c-42be-9519-07315c1dfdae";
    private static final String EMAIL_TYPE_CODE = "CUSTOMER-CREATION";
    private static final String EMAIL_DESTINATION = "EMAIL_DESTINATION@test.com";
    private static final String EMAIL_SUBJECT = "Gracias por crear tu cuenta ";
    private static final String EMAIL_CONTENT = "EMAIL_CONTENT";
    private static final String CUSTOMER_NAME="JC ROOMS PUERTA DEL SOL";
    private static final String LANGUAGE_CODE = "ES";



    @Mock
    private CustomerServiceClient customerServiceClient;

    @Mock
    private EmailDestinationResolver emailDestinationResolver;

    @Mock
    private EmailSenderService emailSenderService;

    @Mock
    private TemplateService templateService;


    @InjectMocks
    private CustomerCreatedEmailServiceImpl customerCreatedEmailService;
    private Customer customer;
    private Email email;

    @Before
    public void setUp() {
        customer = new Customer();
        customer.setId(CUSTOMER_ID);
        customer.setEmail(EMAIL_DESTINATION);
        customer.setName(CUSTOMER_NAME);
        customer.setLanguageCode(LANGUAGE_CODE);

        email = new Email(EMAIL_TYPE_CODE, EMAIL_DESTINATION,SUBJECT_TEMPLATE_PATH, EMAIL_CONTENT);

    }


    @Test
    public void testValidBodyTemplatePath() {
        assertThat("the body template path should be : ", customerCreatedEmailService.BODY_TEMPLATE_CUSTOMER_CREATED, is(BODY_TEMPLATE_PATH));
    }
    @Test
    public void testValidSubjectTemplatePath(){
        assertThat("the subject template path should be : ", customerCreatedEmailService.SUBJECT_TEMPLATE_CUSTOMER_CREATED, is(SUBJECT_TEMPLATE_PATH));


    }
    @Test
    public void testCreatCustomerEmail() {
        when(customerServiceClient.findCustomerById(CUSTOMER_ID, PROJECTION)).thenReturn(new Resource<Customer>(customer));
        when(templateService.renderBodyTemplate(eq(BODY_TEMPLATE_PATH),eq(LANGUAGE_CODE), any(Map.class))).thenReturn(EMAIL_CONTENT);
        when(templateService.renderSubjectTemplate(eq(SUBJECT_TEMPLATE_PATH),eq(LANGUAGE_CODE), any(Map.class))).thenReturn(SUBJECT_TEMPLATE_PATH);

        when(emailSenderService.sendEmail(email)).thenReturn(email);
        when(emailDestinationResolver.getDestinationEmail(customer)).thenReturn(EMAIL_DESTINATION);

        customerCreatedEmailService.sendCustomerMail(CUSTOMER_ID);
        verify(emailSenderService).sendEmail(email);


    }


}









