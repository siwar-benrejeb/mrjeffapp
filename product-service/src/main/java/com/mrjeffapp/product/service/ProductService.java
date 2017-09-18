package com.mrjeffapp.product.service;

import com.mrjeffapp.product.domain.Product;

import java.util.List;

/**
 * Created by siwsiw on 24/08/2017.
 */
public interface ProductService {
    public List<Product> findAll();
    public Product findById(String id);
}
