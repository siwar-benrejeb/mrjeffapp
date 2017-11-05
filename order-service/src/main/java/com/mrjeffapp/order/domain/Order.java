package com.mrjeffapp.order.domain;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Table(name = "[order]")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Order {

    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.AUTO)
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition="CHAR(36)")
    private String id;

@NotNull
private String code;

@NotNull
@Column(columnDefinition="CHAR(36)")
private String customerId;

@NotNull
private Date orderDate;

@NotNull
@ManyToOne
private OrderType orderType;

@NotNull
@ManyToOne
private OrderStatus orderStatus;

@NotNull
@ManyToOne
private Channel channel;

@NotNull
private String countryCode;

@NotNull
@Column(columnDefinition="CHAR(36)")
private String countryId;

@NotNull
@Column(columnDefinition="CHAR(36)")
private String postalCodeId;

@Column(columnDefinition="CHAR(36)")
private String headquarterId;

@Column(columnDefinition="CHAR(36)")
private String providerId;

@Column(columnDefinition="CHAR(36)")
private String couponId;

private String note;

@NotNull
private Double totalPrice;

@NotNull
private Double totalPriceWithoutDiscount;

@NotNull
private Double totalPriceDiscount;

@NotNull
private String paymentMethodCode;

@NotNull
@Column(columnDefinition="CHAR(36)")
private String billingAddressId;

@NotNull
@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
private Set<ProductOrder> productsOrder;

@NotNull
@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
private Set<Visit> visits;

@NotNull
@CreatedDate
private Date creationDate;

@NotNull
@LastModifiedDate
private Date lastModifiedDate;

public String getId() {
    return id;
}

public void setId(String id) {
    this.id = id;
}

public String getCode() {
    return code;
}

public void setCode(String code) {
    this.code = code;
}

public String getCustomerId() {
    return customerId;
}

public void setCustomerId(String customerId) {
    this.customerId = customerId;
}

public Date getOrderDate() {
    return orderDate;
}

public void setOrderDate(Date orderDate) {
    this.orderDate = orderDate;
}

public Channel getChannel() {
    return channel;
}

public void setChannel(Channel channel) {
    this.channel = channel;
}

public String getHeadquarterId() {
    return headquarterId;
}

public void setHeadquarterId(String headquarterId) {
    this.headquarterId = headquarterId;
}

public String getProviderId() {
    return providerId;
}

public void setProviderId(String providerId) {
    this.providerId = providerId;
}

public String getNote() {
    return note;
}

public void setNote(String note) {
    this.note = note;
}

public Double getTotalPrice() {
    return totalPrice;
}

public void setTotalPrice(Double totalPrice) {
    this.totalPrice = totalPrice;
}

public Date getCreationDate() {
    return creationDate;
}

public void setCreationDate(Date creationDate) {
    this.creationDate = creationDate;
}

public Date getLastModifiedDate() {
    return lastModifiedDate;
}

public void setLastModifiedDate(Date lastModifiedDate) {
    this.lastModifiedDate = lastModifiedDate;
}

public Set<ProductOrder> getProductsOrder() {
    return productsOrder;
}

public void setProductsOrder(Set<ProductOrder> productsOrder) {
    this.productsOrder = productsOrder;
}

public String getCouponId() {
    return couponId;
}

public void setCouponId(String couponId) {
    this.couponId = couponId;
}

public Set<Visit> getVisits() {
    return visits;
}

public void setVisits(Set<Visit> visits) {
    this.visits = visits;
}

public OrderStatus getOrderStatus() {
    return orderStatus;
}

public void setOrderStatus(OrderStatus orderStatus) {
    this.orderStatus = orderStatus;
}

public Double getTotalPriceWithoutDiscount() {
    return totalPriceWithoutDiscount;
}

public void setTotalPriceWithoutDiscount(Double totalPriceWithoutDiscount) {
    this.totalPriceWithoutDiscount = totalPriceWithoutDiscount;
}

public Double getTotalPriceDiscount() {
    return totalPriceDiscount;
}

public void setTotalPriceDiscount(Double totalPriceDiscount) {
    this.totalPriceDiscount = totalPriceDiscount;
}

public String getPostalCodeId() {
    return postalCodeId;
}

public void setPostalCodeId(String postalCodeId) {
    this.postalCodeId = postalCodeId;
}

public String getCountryId() {
    return countryId;
}

public void setCountryId(String countryId) {
    this.countryId = countryId;
}

public String getCountryCode() {
    return countryCode;
}

public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
}

public OrderType getOrderType() {
    return orderType;
}

public void setOrderType(OrderType orderType) {
    this.orderType = orderType;
}

public String getPaymentMethodCode() {
    return paymentMethodCode;
}

public void setPaymentMethodCode(String paymentMethodCode) {
    this.paymentMethodCode = paymentMethodCode;
}

public String getBillingAddressId() {
    return billingAddressId;
}

public void setBillingAddressId(String billingAddressId) {
    this.billingAddressId = billingAddressId;
}

@Override
public String toString() {
    return "Order{" +
            "id='" + id + '\'' +
            ", code='" + code + '\'' +
            ", customerId='" + customerId + '\'' +
            ", orderDate=" + orderDate +
            ", orderType=" + orderType +
            ", orderStatus=" + orderStatus +
            ", channel=" + channel +
            ", countryId='" + countryId + '\'' +
            ", postalCodeId='" + postalCodeId + '\'' +
            ", headquarterId='" + headquarterId + '\'' +
            ", providerId='" + providerId + '\'' +
            ", couponId='" + couponId + '\'' +
            ", note='" + note + '\'' +
            ", totalPrice=" + totalPrice +
            ", totalPriceWithoutDiscount=" + totalPriceWithoutDiscount +
            ", totalPriceDiscount=" + totalPriceDiscount +
            ", paymentMethodCode='" + paymentMethodCode + '\'' +
            ", billingAddressId='" + billingAddressId + '\'' +
            ", productsOrder=" + productsOrder +
            ", visits=" + visits +
            ", creationDate=" + creationDate +
            ", lastModifiedDate=" + lastModifiedDate +
            '}';
}
}
