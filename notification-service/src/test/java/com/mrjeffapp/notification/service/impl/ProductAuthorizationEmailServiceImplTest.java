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
import com.mrjeffapp.notification.service.TemplateService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;

import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ProductAuthorizationEmailServiceImplTest {
    private static final String ORDER_ID = "0000a2e7-228e-465c-8bc6-ddb1e7724e5c";
    private static final String WORK_ORDER_ID = "1";
    private static final String PRODUCT_ID = "5298630a-f267-4d4e-b899-01388c150e2a";
    private static final String CUSTOMER_ID = "3039faa2-e73c-42be-9519-07315c1dfdae";
    private static final String PROJECTION = "full";
    private static final String PRODUCT_NAME = "camisa";
    private static final String BODY_TEMPLATE_PATH = "template/mail/ProductAdded/Body";
    private static final String SUBJECT_TEMPLATE_PATH = "template/mail/ProductAdded/Subject";

    private static final String EMAIL_TYPE_CODE = "PRODUCT-ADDED";
    private static final String EMAIL_DESTINATION = "EMAIL_DESTINATION@test.com";
    private static final String EMAIL_SUBJECT = "¿Autorizas estas prendas adicionales en tu pedido ";
    private static final String EMAIL_CONTENT = "EMAIL_CONTENT";
    private static final String ORDER_CODE = "ES46239";
    private static final String LANGUAGE_CODE = "ES";

    @Mock
    private ProviderServiceClient providerServiceClient;

    @Mock
    private EmailDestinationResolver emailDestinationResolver;

    @Mock
    private EmailSenderService emailSenderService;

    @Mock
    private TemplateService templateService;

    @Mock
    private OrderServiceClient orderServiceClient;

    @Mock
    private CustomerServiceClient customerServiceClient;

    @Mock
    private ProductServiceClient productServiceClient;

    @InjectMocks
    private ProductAuthorizationEmailServiceImpl productAddedEmailService;
    private Order order;
    private Customer customer;
    private ProviderProduct providerProduct;
    private Product product;
    private Email email;
    private ProductWorkOrder productWorkOrder;


    @Before
    public void setUp() {
        order = new Order();
        order.setId(ORDER_ID);
        order.setCustomerId(CUSTOMER_ID);
        order.setCode(ORDER_CODE);

        customer = new Customer();
        customer.setId(order.getCustomerId());
        customer.setEmail(EMAIL_DESTINATION);
        customer.setLanguageCode(LANGUAGE_CODE);

        providerProduct = new ProviderProduct();
        providerProduct.setProductId(PRODUCT_ID);

        product = new Product();
        product.setId(providerProduct.getProductId());
        product.setName(PRODUCT_NAME);

        productWorkOrder = new ProductWorkOrder(product.getName());


        email = new Email(EMAIL_TYPE_CODE, EMAIL_DESTINATION, SUBJECT_TEMPLATE_PATH, EMAIL_CONTENT);
    }

    @Test
    public void testValidBodyTemplatePath() {
        assertThat("the body template path should be : ", productAddedEmailService.BODY_TEMPLATE_PRODUCT_ADDED, is(BODY_TEMPLATE_PATH));
    }
    @Test
    public void testValidSubjectTemplatePath() {
        assertThat("the subject template path should be : ", productAddedEmailService.SUBJECT_TEMPLATE_PRODUCT_ADDED, is(SUBJECT_TEMPLATE_PATH));
    }

    @Test
    public void testSendProductMail() {
        when(orderServiceClient.findOrderById(ORDER_ID, PROJECTION)).thenReturn(new Resource<Order>(order));
        when(customerServiceClient.findCustomerById(CUSTOMER_ID, PROJECTION)).thenReturn(new Resource<Customer>(customer));
        when(providerServiceClient.findByWorkOrderId(WORK_ORDER_ID, Integer.MAX_VALUE)).thenReturn(new PagedResources<ProviderProduct>(Stream.of(providerProduct).collect(toSet()), null));
        when(productServiceClient.findProductsByIdIn(PRODUCT_ID, Integer.MAX_VALUE)).thenReturn(new PagedResources<Product>(Stream.of(product).collect(toSet()), null));
        when(templateService.renderBodyTemplate(eq(BODY_TEMPLATE_PATH),eq(LANGUAGE_CODE), any(Map.class))).thenReturn(EMAIL_CONTENT);
        when(templateService.renderSubjectTemplate(eq(SUBJECT_TEMPLATE_PATH),eq(LANGUAGE_CODE), any(Map.class))).thenReturn(SUBJECT_TEMPLATE_PATH);

        when(emailSenderService.sendEmail(email)).thenReturn(email);
        when(emailDestinationResolver.getDestinationEmail(customer)).thenReturn(EMAIL_DESTINATION);

        productAddedEmailService.sendProductEmail(WORK_ORDER_ID, ORDER_ID);

        verify(emailSenderService).sendEmail(email);
    }

}
