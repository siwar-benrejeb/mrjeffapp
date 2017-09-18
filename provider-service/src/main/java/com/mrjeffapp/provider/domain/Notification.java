package com.mrjeffapp.provider.domain;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Notification {

    public static final String UNREAD = "UNREAD";
    public static final String READ = "READ";

    public static final String AUTHORIZATION_CODE = "AUTHORIZED";
    public static final String NO_AUTHORIZATION_CODE = "UNAUTHORIZED";

    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.AUTO)
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition="CHAR(36)")
    private String id;

    @NotNull
    @Column(columnDefinition="CHAR(36)")
    private String providerId;

    @NotNull
    @Column(columnDefinition="CHAR(36)")
    private String userId;

    @NotNull
    private String description;

    @NotNull
    private String state;

    @NotNull
    private String code;

    @ManyToOne
    private Product product;

    @NotNull
    @CreatedDate
    private Date creationDate;

    private Date readDate;

    public Notification() {}

    public Notification(
            String userId,
            String providerId,
            String description,
            String code,
            Product product) {
        this.description = description;
        this.code = code;
        this.userId = userId;
        this.providerId = providerId;
        this.state = UNREAD;
        this.product = product;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getState() {
        return state;
    }

    public String getCode() {
        return code;
    }

    public String getProviderId() {
        return providerId;
    }

    public String getUserId() {
        return userId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getReadDate() {
        return readDate;
    }

    public void read() {
        this.state = READ;
    }

    public Product getProduct() {
        return product;
    }
}
