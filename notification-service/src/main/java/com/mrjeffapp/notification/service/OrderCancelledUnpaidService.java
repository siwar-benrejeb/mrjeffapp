package com.mrjeffapp.notification.service;


public interface OrderCancelledUnpaidService {
    public void sendOrderCancelledUnpaidEmail(String orderId);
}
