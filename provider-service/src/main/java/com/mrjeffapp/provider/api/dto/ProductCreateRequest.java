package com.mrjeffapp.provider.api.dto;

import javax.validation.constraints.NotNull;
import java.util.List;

public class ProductCreateRequest {

    @NotNull
    private String userId;

    @NotNull
    private List<ProductsRequest> products;
    
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<ProductsRequest> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsRequest> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "ProductCreateRequest{" +
                "userId='" + userId + '\'' +
                ", products=" + products +
                '}';
    }
}
