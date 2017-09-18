package com.mrjeffapp.notification.service;


public interface OrderCancelledService {
    public void sendOrderCancelledUnpaidEmail(String orderId);
}
