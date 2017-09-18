package com.mrjeffapp.provider.factory;

import com.mrjeffapp.provider.domain.Notification;
import com.mrjeffapp.provider.domain.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotificationFactory implements NotificationFactoryInterface {

    @Override
    @Transactional
    public Notification build(String userId,
                              String providerId,
                              String description,
                              String code,
                              Product product) {

        Notification notification = new Notification(
                userId,
                providerId,
                description,
                code,
                product);

        return notification;
    }
}
