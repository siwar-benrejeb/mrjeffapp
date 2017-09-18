package com.mrjeffapp.notification.service.impl;

import com.mrjeffapp.notification.client.CustomerServiceClient;
import com.mrjeffapp.notification.client.OrderServiceClient;
import com.mrjeffapp.notification.client.ProductServiceClient;
import com.mrjeffapp.notification.client.ProviderServiceClient;
import com.mrjeffapp.notification.client.model.Customer;
import com.mrjeffapp.notification.client.model.Order;
import com.mrjeffapp.notification.client.model.Product;
import com.mrjeffapp.notification.client.model.ProviderProduct;
import com.mrjeffapp.notification.model.Email;
import com.mrjeffapp.notification.model.ProductWorkOrder;
import com.mrjeffapp.notification.service.EmailDestinationResolver;
import com.mrjeffapp.notification.service.EmailSenderService;
import com.mrjeffapp.notification.service.ProductAuthorizationEmailService;
import com.mrjeffapp.notification.service.TemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;

@Service
public class ProductAuthorizationEmailServiceImpl implements ProductAuthorizationEmailService {
    public static final String BODY_TEMPLATE_PRODUCT_ADDED = "template/mail/ProductAdded/Body";
    public static final String SUBJECT_TEMPLATE_PRODUCT_ADDED = "template/mail/ProductAdded/Subject";

    private final static Logger LOG = LoggerFactory.getLogger(ProductAuthorizationEmailServiceImpl.class);
    private static final String EMAIL_TYPE_CODE = "PRODUCT-ADDED";
    private static final String PROJECTION_FULL = "full";
    private static final String JOIN_DELIMITER = ",";

    private final String EMAIL_SUBJECT = "¿Autorizas estas prendas adicionales en tu pedido? ";

    private final ProviderServiceClient providerServiceClient;
    private final EmailDestinationResolver emailDestinationResolver;
    private final EmailSenderService emailSenderService;
    private final TemplateService templateService;
    private final OrderServiceClient orderServiceClient;
    private final CustomerServiceClient customerServiceClient;
    private final ProductServiceClient productServiceClient;

    private final String phoneNumber;
    private final String emailInfo;

    @Autowired
    public ProductAuthorizationEmailServiceImpl(ProviderServiceClient providerServiceClient,
                                                EmailDestinationResolver emailDestinationResolver,
                                                EmailSenderService emailSenderService,
                                                TemplateService templateService,
                                                OrderServiceClient orderServiceClient,
                                                CustomerServiceClient customerServiceClient,
                                                ProductServiceClient productServiceClient,
                                                @Value("${mail.i18n.es.phoneNumber}") String phoneNumber,
                                                @Value("${mail.i18n.es.email}") String emailInfo) {
        this.providerServiceClient = providerServiceClient;
        this.emailDestinationResolver = emailDestinationResolver;
        this.emailSenderService = emailSenderService;
        this.templateService = templateService;
        this.orderServiceClient = orderServiceClient;
        this.customerServiceClient = customerServiceClient;
        this.productServiceClient = productServiceClient;
        this.phoneNumber = phoneNumber;
        this.emailInfo = emailInfo;
    }

    @Override
    public void sendProductEmail(String workOrderId, String orderId) {
        Collection<ProviderProduct> providerProducts = findProductByWorkOrderId(workOrderId);
        LOG.debug("list of products :{}", providerProducts);

        Order order = findOrderById(orderId);
        LOG.debug("order :{}", order);

        Customer customer = findCustomerById(order.getCustomerId());
        LOG.debug("customer :{}", customer);
        Map<String, Object> parameters = buildEmailTemplateParameters(workOrderId, customer);
        String languageCode=customer.getLanguageCode();


        String emailContent = templateService.renderBodyTemplate(BODY_TEMPLATE_PRODUCT_ADDED,languageCode,parameters);
        String emailDestination = emailDestinationResolver.getDestinationEmail(customer);
        String emailSubject = templateService.renderSubjectTemplate(SUBJECT_TEMPLATE_PRODUCT_ADDED,languageCode,parameters);

        Email email = new Email(EMAIL_TYPE_CODE, emailDestination, emailSubject, emailContent);

        LOG.info("Sending email :{}", email);
        emailSenderService.sendEmail(email);
    }

    private Map<String, Object> buildEmailTemplateParameters(String workOrderId, Customer customer) {
        List<ProductWorkOrder> productWorkOrders = buildProducts(workOrderId);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("productWorkOrders", productWorkOrders);
        parameters.put("customer", customer);
        parameters.put("phoneNumber", phoneNumber);
        parameters.put("emailInfo", emailInfo);
        return parameters;
    }

    private List<ProductWorkOrder> buildProducts(String workOrderId) {
        Collection<ProviderProduct> providerProducts = findProductByWorkOrderId(workOrderId);
        String productsIds = providerProducts.stream()
                .map(ProviderProduct::getProductId)
                .collect(joining(JOIN_DELIMITER));

        Map<String, Product> productsMap = findProductsByIds(productsIds);
        return buildListOfProduct(workOrderId, productsMap);
    }


    private List<ProductWorkOrder> buildListOfProduct(String workOrderId, Map<String, Product> productMap) {
        Collection<ProviderProduct> providerProducts = findProductByWorkOrderId(workOrderId);
        List<ProductWorkOrder> productWorkOrders = new ArrayList<>();
        for (ProviderProduct providerProduct : providerProducts) {
            Product product = productMap.get(providerProduct.getProductId());
            ProductWorkOrder productWorkOrder = new ProductWorkOrder(product.getName());
            LOG.debug("ProductWorkOrder :{}", productWorkOrder);
            productWorkOrders.add(productWorkOrder);
        }
        return productWorkOrders;


    }


    private Order findOrderById(String orderId) {
        Resource<Order> orderResource = orderServiceClient.findOrderById(orderId, PROJECTION_FULL);
        return orderResource.getContent();
    }

    private Customer findCustomerById(String customerId) {
        Resource<Customer> customerResource = customerServiceClient.findCustomerById(customerId, PROJECTION_FULL);
        return customerResource.getContent();
    }

    private Map<String, Product> findProductsByIds(String productsIds) {
        PagedResources<Product> productsResources = productServiceClient.findProductsByIdIn(productsIds, Integer.MAX_VALUE);
        return productsResources.getContent().stream().collect(toMap(p -> p.getId(), Function.identity()));
    }

    private Collection<ProviderProduct> findProductByWorkOrderId(String workOrderId) {
        PagedResources<ProviderProduct> providerProducts = providerServiceClient.findByWorkOrderId(workOrderId, Integer.MAX_VALUE);
        return providerProducts.getContent();
    }


}
