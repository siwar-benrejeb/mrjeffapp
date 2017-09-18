package com.mrjeffapp.provider.factory;

import com.mrjeffapp.provider.domain.Notification;
import com.mrjeffapp.provider.domain.Product;

public interface NotificationFactoryInterface {

    Notification build(String userId,
                       String providerId,
                       String description,
                       String code,
                       Product product);
}
