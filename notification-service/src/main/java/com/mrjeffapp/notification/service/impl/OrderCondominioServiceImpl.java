package com.mrjeffapp.notification.service.impl;

import com.mrjeffapp.notification.client.CustomerServiceClient;
import com.mrjeffapp.notification.client.OrderServiceClient;
import com.mrjeffapp.notification.client.model.Customer;
import com.mrjeffapp.notification.client.model.Order;
import com.mrjeffapp.notification.client.model.Visit;
import com.mrjeffapp.notification.model.Email;
import com.mrjeffapp.notification.service.EmailDestinationResolver;
import com.mrjeffapp.notification.service.EmailSenderService;
import com.mrjeffapp.notification.service.OrderCondominioService;
import com.mrjeffapp.notification.service.TemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OrderCondominioServiceImpl implements OrderCondominioService {
    public static final String BODY_TEMPLATE_ORDER_CONDOMINIO = "template/mail/OrderCondominio/Body";
    public static final String SUBJECT_TEMPLATE_ORDER_CONDOMINIO = "template/mail/OrderCondominio/Subject";

    private static final Logger LOG = LoggerFactory.getLogger(OrderCondominioServiceImpl.class);
    private static final String EMAIL_TYPE_CODE = "ORDER_CONDOMINIO";
    private static final String PROJECTION_FULL = "full";
    private static final String EMAIL_SUBJECT = "Hemos recibido tu pedido : ";

    private final OrderServiceClient orderServiceClient;
    private final CustomerServiceClient customerServiceClient;
    private final TemplateService templateService;
    private final EmailSenderService emailSenderService;
    private final EmailDestinationResolver emailDestinationResolver;

    private final String phoneNumber;
    private final String emailInfo;

@Autowired
    public OrderCondominioServiceImpl(OrderServiceClient orderServiceClient,
                                      CustomerServiceClient customerServiceClient,
                                      TemplateService templateService,
                                      EmailSenderService emailSenderService,
                                      EmailDestinationResolver emailDestinationResolver,
                                      @Value("${mail.i18n.mx.phoneNumber}")String phoneNumber,
                                      @Value("${mail.i18n.mx.email}")String emailInfo) {
        this.orderServiceClient = orderServiceClient;
        this.customerServiceClient = customerServiceClient;
        this.templateService = templateService;
        this.emailSenderService = emailSenderService;
        this.emailDestinationResolver = emailDestinationResolver;
        this.phoneNumber = phoneNumber;
        this.emailInfo = emailInfo;
    }

    @Override
    public void sendOrderCondominioEmail(String orderId) {

        Order order = findOrderById(orderId);
        LOG.debug("order :{}", order);

        Customer customer = findCustomerById(order.getCustomerId());
        LOG.debug("customer :{}", customer);

        Map<String, Object> parameters = buildEmailTemplateParameters(order, customer);
        String languageCode=customer.getLanguageCode();


        String emailContent = templateService.renderBodyTemplate(BODY_TEMPLATE_ORDER_CONDOMINIO,languageCode,parameters);
        String emailDestination = emailDestinationResolver.getDestinationEmail(customer);
        String emailSubject = templateService.renderSubjectTemplate(SUBJECT_TEMPLATE_ORDER_CONDOMINIO,languageCode,parameters);

        Email email = new Email(EMAIL_TYPE_CODE, emailDestination, emailSubject, emailContent);

        LOG.info("Sending email :{}", email);
        emailSenderService.sendEmail(email);
    }
    private Map<String, Object> buildEmailTemplateParameters(Order order, Customer customer) {

        Visit pickUpVisit = order.getPickupVisit();
        LOG.debug("the pickUp Visit :{}", pickUpVisit);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("order", order);
        parameters.put("customer", customer);
        parameters.put("pickUpVisit", pickUpVisit);
        parameters.put("phoneNumber", phoneNumber);
        parameters.put("emailInfo", emailInfo);
        return parameters;
    }

    private Customer findCustomerById(String customerId) {
        Resource<Customer> customerResource = customerServiceClient.findCustomerById(customerId, PROJECTION_FULL);
        return customerResource.getContent();
    }

    private Order findOrderById(String orderId) {
        Resource<Order> orderResource = orderServiceClient.findOrderById(orderId, PROJECTION_FULL);
        return orderResource.getContent();
    }






}
