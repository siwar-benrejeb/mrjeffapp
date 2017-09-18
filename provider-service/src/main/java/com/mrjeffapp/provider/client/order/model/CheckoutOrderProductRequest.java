package com.mrjeffapp.provider.client.order.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Set;

public class CheckoutOrderProductRequest {

    @NotNull
    private String paymentMethodCode;

    private String paymentToken;

    @NotNull
    @Valid
    private Set<OrderProductQuantityRequest> products;


    public Set<OrderProductQuantityRequest> getProducts() {
        return products;
    }

    public void setProducts(Set<OrderProductQuantityRequest> products) {
        this.products = products;
    }

    public String getPaymentMethodCode() {
        return paymentMethodCode;
    }

    public void setPaymentMethodCode(String paymentMethodCode) {
        this.paymentMethodCode = paymentMethodCode;
    }

    public String getPaymentToken() {
        return paymentToken;
    }

    public void setPaymentToken(String paymentToken) {
        this.paymentToken = paymentToken;
    }



}
