package com.mrjeffapp.product.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
public class Product {

    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.AUTO)
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition="CHAR(36)")
    private String id;

    @NotNull
    private String code;

    @NotNull
    private String name;

    private String description;

    @NotNull
    private Boolean active;

    @NotNull
    private Double price;

    @NotNull
    private Double priceWithoutVat;

    @NotNull
    @Column(columnDefinition="CHAR(36)")
    private String countryId;

    @NotNull
    private String countryCode;

    @NotNull
    @ManyToOne
    private VATRate vatRate;

    @NotNull
    @ManyToOne
    private ProductType productType;

    @ManyToMany
    @JoinTable(
            name="product_channel",
            joinColumns=@JoinColumn(name="product_id", referencedColumnName="id"),
            inverseJoinColumns=@JoinColumn(name="channel_id", referencedColumnName="id"))
    private Set<Channel> channels;

    @ManyToMany
    @JoinTable(
            name="product_periodicity",
            joinColumns=@JoinColumn(name="product_id", referencedColumnName="id"),
            inverseJoinColumns=@JoinColumn(name="periodicity_id", referencedColumnName="id"))
    private Set<Periodicity> periodicity;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<ProductItem> productItems;

    @ManyToMany
    @JoinTable(
            name="product_product",
            joinColumns=@JoinColumn(name="product_id", referencedColumnName="id"),
            inverseJoinColumns=@JoinColumn(name="related_product_id", referencedColumnName="id"))
    private Set<Product> related;

    @NotNull
    private Boolean itemQuotaTracking;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Set<ProductItem> getProductItems() {
        return productItems;
    }

    public void setProductItems(Set<ProductItem> productItems) {
        this.productItems = productItems;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public VATRate getVatRate() {
        return vatRate;
    }

    public void setVatRate(VATRate vatRate) {
        this.vatRate = vatRate;
    }

    public Set<Channel> getChannels() {
        return channels;
    }

    public void setChannels(Set<Channel> channels) {
        this.channels = channels;
    }

    public Set<Periodicity> getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(Set<Periodicity> periodicity) {
        this.periodicity = periodicity;
    }

    public Set<Product> getRelated() {
        return related;
    }

    public void setRelated(Set<Product> related) {
        this.related = related;
    }

    public Boolean getItemQuotaTracking() {
        return itemQuotaTracking;
    }

    public void setItemQuotaTracking(Boolean itemQuotaTracking) {
        this.itemQuotaTracking = itemQuotaTracking;
    }

    public Double getPriceWithoutVat() {
        return priceWithoutVat;
    }

    public void setPriceWithoutVat(Double priceWithoutVat) {
        this.priceWithoutVat = priceWithoutVat;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
