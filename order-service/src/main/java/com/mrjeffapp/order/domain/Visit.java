package com.mrjeffapp.order.domain;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Time;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Visit {

    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.AUTO)
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition="CHAR(36)")
    private String id;

    @NotNull
    private Date date;

    @NotNull
    private String visitTypeCode;

    @NotNull
    private Time timeSlotStart;

    @NotNull
    private Time timeSlotEnd;

    @NotNull
    private String postalCode;

    @NotNull
    @Column(columnDefinition="CHAR(36)")
    private String postalCodeId;

    @NotNull
    @Column(columnDefinition="CHAR(36)")
    private String cityId;

    @NotNull
    @Column(columnDefinition="CHAR(36)")
    private String countryId;

    @NotNull
    @Column(columnDefinition="CHAR(36)")
    private String addressId;

    @Column(columnDefinition="CHAR(36)")
    private String timeTableTimeSlotId;

    @NotNull
    @ManyToOne
    private Order order;

    @NotNull
    @CreatedDate
    private Date creationDate;

    @NotNull
    @LastModifiedDate
    private Date lastModificationDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getVisitTypeCode() {
        return visitTypeCode;
    }

    public void setVisitTypeCode(String visitTypeCode) {
        this.visitTypeCode = visitTypeCode;
    }

    public Time getTimeSlotStart() {
        return timeSlotStart;
    }

    public void setTimeSlotStart(Time timeSlotStart) {
        this.timeSlotStart = timeSlotStart;
    }

    public Time getTimeSlotEnd() {
        return timeSlotEnd;
    }

    public void setTimeSlotEnd(Time timeSlotEnd) {
        this.timeSlotEnd = timeSlotEnd;
    }

    public String getPostalCodeId() {
        return postalCodeId;
    }

    public void setPostalCodeId(String postalCodeId) {
        this.postalCodeId = postalCodeId;
    }

    public String getTimeTableTimeSlotId() {
        return timeTableTimeSlotId;
    }

    public void setTimeTableTimeSlotId(String timeTableTimeSlotId) {
        this.timeTableTimeSlotId = timeTableTimeSlotId;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastModificationDate() {
        return lastModificationDate;
    }

    public void setLastModificationDate(Date lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    @Override
    public String toString() {
        return "Visit{" +
                "id=" + id +
                ", date=" + date +
                ", visitTypeCode='" + visitTypeCode + '\'' +
                ", timeSlotStart=" + timeSlotStart +
                ", timeSlotEnd=" + timeSlotEnd +
                ", postalCode='" + postalCode + '\'' +
                ", postalCodeId=" + postalCodeId +
                ", cityId=" + cityId +
                ", countryId=" + countryId +
                ", addressId=" + addressId +
                ", timeTableTimeSlotId=" + timeTableTimeSlotId +
                ", order=" + order +
                ", creationDate=" + creationDate +
                ", lastModificationDate=" + lastModificationDate +
                '}';
    }
}
