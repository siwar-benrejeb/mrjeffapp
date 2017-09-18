package com.mrjeffapp.provider.service.Notification;

import com.mrjeffapp.provider.domain.Notification;
import com.mrjeffapp.provider.domain.Product;
import com.mrjeffapp.provider.factory.NotificationFactory;
import com.mrjeffapp.provider.factory.NotificationFactoryInterface;
import com.mrjeffapp.provider.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.mrjeffapp.provider.domain.Notification.AUTHORIZATION_CODE;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationFactoryInterface notificationFactory;
    private final ProductRepository productRepository;

    @Autowired
    public NotificationServiceImpl(NotificationFactory notificationFactory,
                                   ProductRepository productRepository) {
        this.notificationFactory = notificationFactory;
        this.productRepository = productRepository;
    }

    @Transactional
    @Override
    public void generateNotificationByAuthorization(String userOperator,
                                                    String providerId,
                                                    String description,
                                                    String productSaasId)
    {

        Product product = productRepository.findOne(productSaasId);

        Notification notification = notificationFactory.build(
                userOperator,
                providerId,
                description,
                AUTHORIZATION_CODE,
                product);

        product.addNotification(notification);
    }
}
