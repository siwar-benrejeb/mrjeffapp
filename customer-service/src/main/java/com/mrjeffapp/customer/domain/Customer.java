package com.mrjeffapp.customer.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Email;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Customer {

    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.AUTO)
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition="CHAR(36)")
    private String id;

    @JsonIgnore
    @NotNull
    private String password;

    @NotNull
    private String name;

    @NotNull
    private String lastName;

    @Email
    @NotNull
    private String email;

    private String phoneNumber;

    @NotNull
    private Boolean enabled;

    @NotNull
    private Boolean resetPassword;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Address> addresses;

    @NotNull
    @ManyToOne
    private CustomerType customerType;

    @OneToOne(mappedBy = "customer", optional = true, cascade = CascadeType.ALL)
    private CustomerBusinessDetails customerBusinessDetails;

    @NotNull
    @CreatedDate
    private Date creationDate;

    @LastModifiedDate
    private Date lastModifiedDate;

    @NotNull
    private Boolean passwordMigrated;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<CustomerNote> customerNote;

   // @NotNull
   private String languageCode;

    public Customer() {
    }

    public Customer(String email, String password, String name, String lastName, String phoneNumber, CustomerType customerType) {
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.customerType = customerType;
        this.customerNote = new HashSet<>();
        this.addCustomerType(customerType);
        this.enabled = true;
        this.resetPassword = false;
        this.passwordMigrated = true;
    }

    private void addCustomerType(CustomerType customerType) {
        this.customerType = customerType;
        customerType.addCustomer(this);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
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

    public Boolean getResetPassword() {
        return resetPassword;
    }

    public void setResetPassword(Boolean resetPassword) {
        this.resetPassword = resetPassword;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public CustomerBusinessDetails getCustomerBusinessDetails() {
        return customerBusinessDetails;
    }

    public void setCustomerBusinessDetails(CustomerBusinessDetails customerBusinessDetails) {
        this.customerBusinessDetails = customerBusinessDetails;
    }

    public void appendNote(CustomerNote note) {
        this.customerNote.add(note);
    }

    public Set<CustomerNote> getCustomerNote() {
        return customerNote;
    }

    public Boolean getPasswordMigrated() {
        return passwordMigrated;
    }

    public void setPasswordMigrated(Boolean passwordMigrated) {
        this.passwordMigrated = passwordMigrated;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }
}
