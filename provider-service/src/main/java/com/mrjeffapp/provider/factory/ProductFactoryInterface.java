package com.mrjeffapp.provider.factory;

import com.mrjeffapp.provider.domain.Product;
import com.mrjeffapp.provider.domain.User;
import com.mrjeffapp.provider.domain.WorkOrder;

public interface ProductFactoryInterface {

    Product build(String productId, Integer quantity, WorkOrder workOrder, User user);
}
