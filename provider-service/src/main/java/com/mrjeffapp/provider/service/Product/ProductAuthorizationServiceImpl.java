package com.mrjeffapp.provider.service.Product;

import com.mrjeffapp.provider.domain.AuthorizationState;
import com.mrjeffapp.provider.domain.Notification;
import com.mrjeffapp.provider.domain.Product;
import com.mrjeffapp.provider.domain.User;
import com.mrjeffapp.provider.factory.NotificationFactory;
import com.mrjeffapp.provider.repository.AuthorizationStateRepository;
import com.mrjeffapp.provider.repository.ProductRepository;
import com.mrjeffapp.provider.repository.UserRepository;
import com.mrjeffapp.provider.service.impl.ProductServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.mrjeffapp.provider.config.Constants.BUSINESS_EVENT_LOGGER;
import static com.mrjeffapp.provider.domain.AuthorizationState.AUTHORIZED;
import static com.mrjeffapp.provider.domain.Notification.AUTHORIZATION_CODE;

@Service
public class ProductAuthorizationServiceImpl implements ProductAuthorizationService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductServiceImpl.class);
    private static final Logger BUSINESS_LOG = LoggerFactory.getLogger(BUSINESS_EVENT_LOGGER);

    private final ProductRepository productRepository;
    private final AuthorizationStateRepository authorizationStateRepository;
    private final UserRepository userRepository;
    private final NotificationFactory notificationFactory;

    @Autowired
    public ProductAuthorizationServiceImpl(ProductRepository productRepository,
                                           AuthorizationStateRepository authorizationStateRepository,
                                           UserRepository userRepository,
                                           NotificationFactory notificationFactory) {

        this.productRepository = productRepository;
        this.authorizationStateRepository = authorizationStateRepository;
        this.userRepository = userRepository;
        this.notificationFactory = notificationFactory;
    }

    @Transactional
    public Product productAuthorization(String id, String productId, Integer quantity, String orderId, String userId) {

        LOG.debug("Authorization product id: {}, quantity: {}, workOrderId: {}", id, orderId, userId);

        Product product = productRepository.findOne(id);
        User user = userRepository.findOne(userId);
        AuthorizationState authState = authorizationStateRepository.findByCode(AUTHORIZED);
        product.getAuthorization().changeAuthorizationState(authState, userId);

        Notification notification = notificationFactory.build(
                userId,
                user.getProvider().getId(),
                "El producto ha sido autorizado",
                AUTHORIZATION_CODE,
                product);

        product.addNotification(notification);

        product = productRepository.save(product);

        BUSINESS_LOG.info("Se ha autorizado un producto" + productId);


        LOG.debug("Authorization product finish");

        return product;
    }

}
