package com.mrjeffapp.provider.domain;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Product {

    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.AUTO)
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition="CHAR(36)")
    private String id;

    @NotNull
    @Column(columnDefinition="CHAR(36)")
    private String productId;

    @NotNull
    private Integer quantity;

    @NotNull
    @ManyToOne
    private WorkOrder workOrder;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Authorization authorization;

    @NotNull
    @ManyToOne
    private User user;

    @NotNull
    @CreatedDate
    private Date creationDate;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Notification> notifications;

    public Product(){}

    public Product(String productId, Integer quantity, WorkOrder workOrder, User user) {
        this.productId = productId;
        this.quantity = quantity;
        associateWorkOrder(workOrder);
        this.user = user;
    }

    private void  associateWorkOrder(WorkOrder workOrder) {
        this.workOrder = workOrder;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public Authorization getAuthorization() {
        return authorization;
    }

    public User getUser() {
        return user;
    }

    public WorkOrder getWorkOrder() {
        return workOrder;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void associateAuthorization(Authorization auth) {
        this.authorization = auth;
    }

    public void addNotification(Notification notification) {
        this.notifications.add(notification);
    }

    public Provider getProvider(){
        return this.getWorkOrder().getProvider();
    }

}
