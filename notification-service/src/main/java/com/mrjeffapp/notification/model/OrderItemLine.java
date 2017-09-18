package com.mrjeffapp.notification.model;

public class OrderItemLine {
    private String name;
    private Integer quantity;
    private String price;


    public OrderItemLine(String name, Integer quantity, String price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public OrderItemLine() {

    }

    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "OrderItemLine{" +
                "name='" + name + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }

}
