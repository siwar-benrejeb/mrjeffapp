package com.mrjeffapp.provider.service.impl;

import com.mrjeffapp.event.ProductAuthorizationEvent;
import com.mrjeffapp.event.ProductsAuthorizationsEvent;
import com.mrjeffapp.provider.api.dto.ProductCreateRequest;
import com.mrjeffapp.provider.domain.Product;
import com.mrjeffapp.provider.domain.User;
import com.mrjeffapp.provider.domain.WorkOrder;
import com.mrjeffapp.provider.factory.ProductFactoryInterface;
import com.mrjeffapp.provider.repository.ProductRepository;
import com.mrjeffapp.provider.repository.UserRepository;
import com.mrjeffapp.provider.repository.WorkOrderRepository;
import com.mrjeffapp.provider.service.EventPublisherService;
import com.mrjeffapp.provider.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.mrjeffapp.provider.config.Constants.BUSINESS_EVENT_LOGGER;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductServiceImpl.class);
    private static final Logger BUSINESS_LOG = LoggerFactory.getLogger(BUSINESS_EVENT_LOGGER);

    private WorkOrderRepository workOrderRepository;
    private UserRepository userRepository;
    private ProductRepository productRepository;
    private ProductFactoryInterface productFactory;
    private EventPublisherService eventPublisherService;

    @Autowired
    public ProductServiceImpl(WorkOrderRepository workOrderRepository,
                              UserRepository userRepository,
                              ProductRepository productRepository,
                              ProductFactoryInterface productFactory,
                              EventPublisherService eventPublisherService) {

        this.workOrderRepository = workOrderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.productFactory = productFactory;
        this.eventPublisherService = eventPublisherService;
    }

    @Override
    @Transactional
    public void createProduct(ProductCreateRequest productCreateRequest) {

        LOG.debug("Creating Saas create product product: {}", productCreateRequest);

        User user = userRepository.findOne(productCreateRequest.getUserId());

        List<ProductAuthorizationEvent> productAuthorizationEvents = new ArrayList<>();

        productCreateRequest.getProducts().forEach(productCreate->{
            WorkOrder workOrder = workOrderRepository.findOne(productCreate.getWorkOrder());

            Product product = productFactory.build(productCreate.getProductId(), productCreate.getQuantity(), workOrder, user);
            workOrder.attachProduct(product);

            product = productRepository.save(product);

            BUSINESS_LOG.info("Product has been created: " + productCreate.getProductId() + " WorkOrderId: " + workOrder.getId() + " by UserId: " + user.getId());

            ProductAuthorizationEvent productEvent = new ProductAuthorizationEvent();
            productEvent.setId(product.getId());
            productEvent.setProductId(productCreate.getProductId());
            productEvent.setQuantity(productCreate.getQuantity());
            productEvent.setOrderId(workOrder.getOrderId());
            productEvent.setUserId(user.getId());
            productEvent.setWorkOrderId(workOrder.getId());

            productAuthorizationEvents.add(productEvent);
        });

        if(!productCreateRequest.getProducts().isEmpty()) {
            ProductAuthorizationEvent productAuthorizationEvent = productAuthorizationEvents.stream()
                                                                                                .findFirst()
                                                                                                .get();

            ProductsAuthorizationsEvent event = new ProductsAuthorizationsEvent();
            event.setOrderId(productAuthorizationEvent.getOrderId());
            event.setWorkOrderId(productAuthorizationEvent.getWorkOrderId());
            event.setProductAuthorizationEventList(productAuthorizationEvents);

            eventPublisherService.sendAddProductEvent(event);
        }

        LOG.debug("Finish Saas create product");
    }

}
