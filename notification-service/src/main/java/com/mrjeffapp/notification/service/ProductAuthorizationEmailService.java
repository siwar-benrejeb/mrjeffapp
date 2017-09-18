package com.mrjeffapp.notification.service;


public interface ProductAuthorizationEmailService {

    public void sendProductEmail(String workOrderId, String orderId);
}
