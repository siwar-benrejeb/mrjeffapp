package com.mrjeffapp.customer.domain;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Table
@Entity
@EntityListeners(AuditingEntityListener.class)
public class CustomerNote {

    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.AUTO)
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "CHAR(36)")
    private String id;

    @NotNull
    @ManyToOne
    private Customer customer;

    @NotNull
    @Column(length = 1024)
    private String note;

    @NotNull
    @Column(columnDefinition = "CHAR(36)")
    private String userId;

    @NotNull
    @CreatedDate
    private Date creationDate;

    @NotNull
    @LastModifiedDate
    private Date lastModificationDate;

    public CustomerNote() {
    }

    public CustomerNote(Customer customer, String note, String userId) {
        this.customer = customer;
        this.note = note;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getNote() {
        return note;
    }

    public String getUserId() {
        return userId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getLastModificationDate() {
        return lastModificationDate;
    }

}
