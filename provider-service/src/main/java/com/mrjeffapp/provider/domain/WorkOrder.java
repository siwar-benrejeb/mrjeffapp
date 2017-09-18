package com.mrjeffapp.provider.domain;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class WorkOrder {

    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.AUTO)
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition="CHAR(36)")
    private String id;

    @NotNull
    @Column(columnDefinition="CHAR(36)")
    private String orderId;

    @NotNull
    private String orderCode;

    @NotNull
    private String country;

    @NotNull
    private Date pickupDate;

    @NotNull
    private Date deliveryDate;

    @NotNull
    @Column(columnDefinition="CHAR(36)")
    private String customerId;

    @NotNull
    @ManyToOne
    private Provider provider;

    @NotNull
    @ManyToOne
    private WorkOrderState workOrderState;

    @OneToMany(mappedBy = "workOrder")
    private Set<ItemCount> itemCounts;

    @OneToMany(mappedBy = "workOrder")
    private Set<WorkOrderNote> workOrderNotes;

    @OneToMany(mappedBy = "workOrder")
    private Set<Incident> incidents;

    @NotNull
    @OneToMany(mappedBy = "workOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Product> products;

    @NotNull
    @CreatedDate
    private Date creationDate;

    public WorkOrder() {}

    public WorkOrder(String id) {
        this.id = id;
        this.products = new HashSet<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public WorkOrderState getWorkOrderState() {
        return workOrderState;
    }

    public void setWorkOrderState(WorkOrderState workOrderState) {
        this.workOrderState = workOrderState;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Set<WorkOrderNote> getWorkOrderNotes() {
        return workOrderNotes;
    }

    public void setWorkOrderNotes(Set<WorkOrderNote> workOrderNotes) {
        this.workOrderNotes = workOrderNotes;
    }

    public Set<Incident> getIncidents() {
        return incidents;
    }

    public void setIncidents(Set<Incident> incidents) {
        this.incidents = incidents;
    }

    public Set<ItemCount> getItemCounts() {
        return itemCounts;
    }

    public void setItemCounts(Set<ItemCount> itemCounts) {
        this.itemCounts = itemCounts;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Date getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(Date pickupDate) {
        this.pickupDate = pickupDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public void attachProduct(Product product) {
        this.products.add(product);
    }


    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
