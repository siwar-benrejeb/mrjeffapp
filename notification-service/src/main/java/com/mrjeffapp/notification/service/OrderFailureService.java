package com.mrjeffapp.notification.service;

public interface OrderFailureService {
    public void sendFailedOrderEmail(String orderId);
}
