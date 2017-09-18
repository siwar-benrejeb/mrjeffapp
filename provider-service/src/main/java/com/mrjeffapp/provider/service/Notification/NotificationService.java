package com.mrjeffapp.provider.service.Notification;

public interface NotificationService {

    public void generateNotificationByAuthorization(String userOperator,
                                                    String providerId,
                                                    String description,
                                                    String productSaasId);
}
