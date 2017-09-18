package com.mrjeffapp.provider.service.Product;

import com.mrjeffapp.provider.domain.Product;

public interface ProductAuthorizationService {

    Product productAuthorization(String id, String productId, Integer quantity, String orderId, String userId);
}
