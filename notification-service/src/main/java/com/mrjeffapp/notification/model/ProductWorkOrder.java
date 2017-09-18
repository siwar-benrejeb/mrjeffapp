package com.mrjeffapp.notification.model;


public class ProductWorkOrder {
    private String name;

    public ProductWorkOrder(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ProductWorkOrder{" +
                "name='" + name + '\'' +
                '}';
    }
}
