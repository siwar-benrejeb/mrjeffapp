package com.mrjeffapp.notification.service.impl;

import com.mrjeffapp.notification.client.CustomerServiceClient;
import com.mrjeffapp.notification.client.OrderServiceClient;
import com.mrjeffapp.notification.client.ProductServiceClient;
import com.mrjeffapp.notification.client.model.*;
import com.mrjeffapp.notification.model.Email;
import com.mrjeffapp.notification.model.OrderItemLine;
import com.mrjeffapp.notification.service.EmailDestinationResolver;
import com.mrjeffapp.notification.service.EmailSenderService;
import com.mrjeffapp.notification.service.OrderCreatedEmailService;
import com.mrjeffapp.notification.service.TemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;


@Service
public class OrderCreationEmailServiceImpl implements OrderCreatedEmailService {
    public static final String BODY_TEMPLATE_ORDER_CREATED = "template/mail/OrderCreation/Body";
    public static final String SUBJECT_TEMPLATE_ORDER_CREATED = "template/mail/OrderCreation/Subject";

    private final static Logger LOG = LoggerFactory.getLogger(OrderCreationEmailServiceImpl.class);
    private static final String EMAIL_TYPE_CODE = "ORDER-CREATION";
    private static final String PROJECTION_FULL = "full";
    private static final String JOIN_DELIMITER = ",";
    private final String EMAIL_SUBJECT = "Recibo de tu pedido en MrJeff : ";

    private final OrderServiceClient orderServiceClient;

    private final CustomerServiceClient customerServiceClient;

    private final ProductServiceClient productServiceClient;

    private final EmailDestinationResolver emailDestinationResolver;

    private final EmailSenderService emailSenderService;

    private final TemplateService templateService;

    private final String phoneNumber;

    private final String emailInfo;

    @Autowired
    public OrderCreationEmailServiceImpl(OrderServiceClient orderServiceClient,
                                         CustomerServiceClient customerServiceClient,
                                         ProductServiceClient productServiceClient,
                                         EmailDestinationResolver emailDestinationResolver,
                                         EmailSenderService emailSenderService,
                                         TemplateService templateService,
                                         @Value("${mail.i18n.es.phoneNumber}") String phoneNumber,
                                         @Value("${mail.i18n.es.email}") String emailInfo) {

        this.orderServiceClient = orderServiceClient;
        this.customerServiceClient = customerServiceClient;
        this.productServiceClient = productServiceClient;
        this.emailDestinationResolver = emailDestinationResolver;
        this.emailSenderService = emailSenderService;
        this.templateService = templateService;
        this.phoneNumber = phoneNumber;
        this.emailInfo = emailInfo;
    }

    @Override
    public void sendMail(String orderId) {
        Order order = findOrderById(orderId);
        LOG.debug("order :{}", order);

        Customer customer = findCustomerById(order.getCustomerId());
        LOG.debug("customer :{}", customer);

        Map<String, Object> parameters = buildEmailTemplateParameters(order, customer);
        String languageCode=customer.getLanguageCode();


        String emailContent = templateService.renderBodyTemplate(BODY_TEMPLATE_ORDER_CREATED,languageCode, parameters);
        String emailDestination = emailDestinationResolver.getDestinationEmail(customer);
        String emailSubject = templateService.renderSubjectTemplate(SUBJECT_TEMPLATE_ORDER_CREATED,languageCode,parameters);

        Email email = new Email(EMAIL_TYPE_CODE, emailDestination, emailSubject, emailContent);

        LOG.info("Sending email :{}", email);
        emailSenderService.sendEmail(email);

    }

    private Map<String, Object> buildEmailTemplateParameters(Order order, Customer customer) {
        List<OrderItemLine> orderItemLines = buildOrderItemsLines(customer.getLanguageCode(), order);

        Locale customerLocale = Locale.forLanguageTag(customer.getLanguageCode());
        NumberFormat numberFormatter = NumberFormat.getNumberInstance(customerLocale);
        String totalPrice = numberFormatter.format(order.getTotalPrice());

        Address pickupAddress = order.getPickupVisit().getAddress();
        LOG.debug("pickupAddress={}", pickupAddress);

        Address deliveryAddress = order.getDeliveryVisit().getAddress();
        LOG.debug("deliveryAddress={}", deliveryAddress);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("customer", customer);
        parameters.put("order", order);
        parameters.put("totalPrice", totalPrice);
        parameters.put("orderItemLines", orderItemLines);
        parameters.put("pickUpVisit", order.getPickupVisit());
        parameters.put("deliveryVisit", order.getDeliveryVisit());
        parameters.put("pickUpAddress", pickupAddress);
        parameters.put("deliveryAddress", deliveryAddress);
        parameters.put("phoneNumber", phoneNumber);
        parameters.put("emailInfo", emailInfo);
        return parameters;
    }

    private List<OrderItemLine> buildOrderItemsLines(String localeCode, Order order) {
        Set<ProductOrder> productOrders = order.getProductsOrder();

        String productsIds = order.getProductsOrder().stream()
                .map(ProductOrder::getProductId)
                .collect(joining(JOIN_DELIMITER));

        Map<String, Product> productsMap = findProductsByIds(productsIds);

        return buildProductLines(localeCode, productOrders, productsMap);
    }

    private List<OrderItemLine> buildProductLines(String localeCode, Set<ProductOrder> productOrders, Map<String, Product> productsMap) {
        List<OrderItemLine> orderItemLines = new ArrayList<>();

        Locale customerLocale = Locale.forLanguageTag(localeCode);
        NumberFormat numberFormatter = NumberFormat.getNumberInstance(customerLocale);
        for (ProductOrder productOrder : productOrders) {
            Product product = productsMap.get(productOrder.getProductId());

            Double totalItemsPrice = productOrder.getQuantity() * product.getPrice();
            String totalItemsPriceValue = numberFormatter.format(totalItemsPrice);

            OrderItemLine orderItemLine = new OrderItemLine(product.getName(), productOrder.getQuantity(), totalItemsPriceValue);

            LOG.debug("orderItemLine :{}", orderItemLine);
            orderItemLines.add(orderItemLine);
        }

        Collections.sort(orderItemLines, (e1, e2) -> e1.getName().compareTo(e2.getName()));

        return orderItemLines;
    }

    private Map<String, Product> findProductsByIds(String productsIds) {
        PagedResources<Product> productsResources = productServiceClient.findProductsByIdIn(productsIds, Integer.MAX_VALUE);
        return productsResources.getContent().stream().collect(toMap(p -> p.getId(), Function.identity()));
    }

    private Customer findCustomerById(String customerId) {
        Resource<Customer> customerResource = customerServiceClient.findCustomerById(customerId, PROJECTION_FULL);
        return customerResource.getContent();
    }

    private Order findOrderById(String orderId) {
        Resource<Order> orderResource = orderServiceClient.findOrderById(orderId, PROJECTION_FULL);
        return orderResource.getContent();
    }

    private Visit findVisitByVisitType(Set<Visit> visits, String visitTypeCode) {
        Visit visit = visits.stream().filter(v -> v.getVisitTypeCode().equals(visitTypeCode))
                .findFirst().get();

        return visit;
    }

}

