package com.mrjeffapp.notification.service.impl;


import com.mrjeffapp.notification.client.CustomerServiceClient;
import com.mrjeffapp.notification.client.OrderServiceClient;
import com.mrjeffapp.notification.client.model.Customer;
import com.mrjeffapp.notification.client.model.Order;
import com.mrjeffapp.notification.model.Email;
import com.mrjeffapp.notification.service.EmailDestinationResolver;
import com.mrjeffapp.notification.service.EmailSenderService;
import com.mrjeffapp.notification.service.OrderFailureService;
import com.mrjeffapp.notification.service.TemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OrderFailureServiceImpl implements OrderFailureService {
    public static final String BODY_TEMPLATE_ORDER_FAILUURE = "template/mail/OrderFailure/Body";
    public static final String SUBJECT_TEMPLATE_ORDER_FAILUURE = "template/mail/OrderFailure/Subject";

    private final static Logger LOG = LoggerFactory.getLogger(OrderFailureServiceImpl.class);
    private static final String EMAIL_TYPE_CODE = "ORDER_FAILURE";
    private static final String PROJECTION_FULL = "full";
    private static final String EMAIL_SUBJECT = " Fallo en el cobro de tu pedido : ";

    private final CustomerServiceClient customerServiceClient;
    private final EmailDestinationResolver emailDestinationResolver;
    private final EmailSenderService emailSenderService;
    private final TemplateService templateService;
    private final OrderServiceClient orderServiceClient;

    private final String phoneNumber;
    private final String emailInfo;

    public OrderFailureServiceImpl(CustomerServiceClient customerServiceClient,
                                   EmailDestinationResolver emailDestinationResolver,
                                   EmailSenderService emailSenderService,
                                   TemplateService templateService,
                                   OrderServiceClient orderServiceClient,
                                   @Value("${mail.i18n.es.phoneNumber}")String phoneNumber,
                                   @Value("${mail.i18n.es.email}")String emailInfo) {
        this.customerServiceClient = customerServiceClient;
        this.emailDestinationResolver = emailDestinationResolver;
        this.emailSenderService = emailSenderService;
        this.templateService = templateService;
        this.orderServiceClient=orderServiceClient;
        this.phoneNumber = phoneNumber;
        this.emailInfo = emailInfo;
    }

    @Override
    public void sendFailedOrderEmail(String orderId) {
        Order order = findOrderById(orderId);
        LOG.debug("order :{}", order);

        Customer customer = findCustomerById(order.getCustomerId());
        LOG.debug("customer :{}", customer);

        Map<String, Object> parameters = buildEmailTemplateParameters(order,customer);
        String languageCode=customer.getLanguageCode();


        String emailContent = templateService.renderBodyTemplate(BODY_TEMPLATE_ORDER_FAILUURE,languageCode, parameters);
        String emailDestination = emailDestinationResolver.getDestinationEmail(customer);
        String emailSubject = templateService.renderSubjectTemplate(SUBJECT_TEMPLATE_ORDER_FAILUURE,languageCode,parameters);

        Email email = new Email(EMAIL_TYPE_CODE, emailDestination, emailSubject, emailContent);
        LOG.info("sending email :{}", email);
        emailSenderService.sendEmail(email);


    }

    private Map<String, Object> buildEmailTemplateParameters(Order order,Customer customer) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("order",order);
        parameters.put("customer", customer);
        parameters.put("phoneNumber", phoneNumber);
        parameters.put("emailInfo", emailInfo);
        return parameters;

    }
    private Order findOrderById(String orderId) {
        Resource<Order> orderResource = orderServiceClient.findOrderById(orderId, PROJECTION_FULL);
        return orderResource.getContent();
    }

    private Customer findCustomerById(String customerId) {
        Resource<Customer> customerResource = customerServiceClient.findCustomerById(customerId, PROJECTION_FULL);
        return customerResource.getContent();
    }
}
