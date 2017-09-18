package com.mrjeffapp.notification.service.impl;

import com.mrjeffapp.notification.client.CustomerServiceClient;
import com.mrjeffapp.notification.client.model.Customer;
import com.mrjeffapp.notification.model.Email;
import com.mrjeffapp.notification.service.CustomerCreatedEmailService;
import com.mrjeffapp.notification.service.EmailDestinationResolver;
import com.mrjeffapp.notification.service.EmailSenderService;
import com.mrjeffapp.notification.service.TemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by siwsiw on 12/05/2017.
 */
@Service
public class CustomerCreatedEmailServiceImpl implements CustomerCreatedEmailService {
    public static final String BODY_TEMPLATE_CUSTOMER_CREATED = "template/mail/CustomerCreation/Body";
    public static final String SUBJECT_TEMPLATE_CUSTOMER_CREATED = "template/mail/CustomerCreation/Subject";

    private final static Logger LOG = LoggerFactory.getLogger(CustomerCreatedEmailServiceImpl.class);
    private static final String EMAIL_TYPE_CODE = "CUSTOMER-CREATION";
    private static final String PROJECTION_FULL = "full";
    private static final String EMAIL_SUBJECT = "Gracias por crear tu cuenta ";
    private final CustomerServiceClient customerServiceClient;
    private final EmailDestinationResolver emailDestinationResolver;
    private final EmailSenderService emailSenderService;
    private final TemplateService templateService;

    private final String phoneNumber;
    private final String emailInfo;

    @Autowired
    public CustomerCreatedEmailServiceImpl(CustomerServiceClient customerServiceClient,
                                           EmailDestinationResolver emailDestinationResolver,
                                           EmailSenderService emailSenderService,
                                           TemplateService templateService,
                                           @Value("${mail.i18n.es.phoneNumber}") String phoneNumber,
                                           @Value("${mail.i18n.es.email}") String emailInfo) {
        this.customerServiceClient = customerServiceClient;
        this.emailDestinationResolver = emailDestinationResolver;
        this.emailSenderService = emailSenderService;
        this.templateService = templateService;
        this.phoneNumber = phoneNumber;
        this.emailInfo = emailInfo;
    }


    @Override
    public void sendCustomerMail(String customerId) {
        Customer customer = findCustomerById(customerId);
        LOG.debug("customer :{}", customer);

        Map<String, Object> parameters = buildEmailTemplateParameters(customer);
        String languageCode=customer.getLanguageCode();

        String emailContent = templateService.renderBodyTemplate(BODY_TEMPLATE_CUSTOMER_CREATED,languageCode,parameters);
        String emailDestination = emailDestinationResolver.getDestinationEmail(customer);
        String emailSubject = templateService.renderSubjectTemplate(SUBJECT_TEMPLATE_CUSTOMER_CREATED,languageCode,parameters);

        Email email = new Email(EMAIL_TYPE_CODE, emailDestination, emailSubject, emailContent);
        LOG.info("sending email :{}", email);
        emailSenderService.sendEmail(email);


    }

    private Map<String, Object> buildEmailTemplateParameters(Customer customerSignedIn) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("customerSignedIn", customerSignedIn);
        parameters.put("phoneNumber", phoneNumber);
        parameters.put("emailInfo", emailInfo);
        return parameters;

    }

    private Customer findCustomerById(String customerId) {
        Resource<Customer> customerResource = customerServiceClient.findCustomerById(customerId, PROJECTION_FULL);
        return customerResource.getContent();
    }
}
