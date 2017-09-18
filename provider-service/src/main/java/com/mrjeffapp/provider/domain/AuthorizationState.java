package com.mrjeffapp.provider.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class AuthorizationState {

    public static final String WAITING = "WAITING";
    public static final String AUTHORIZED = "AUTHORIZED";
    public static final String NO_AUTHORIZED = "NO_AUTHORIZED";
    public static final String EXPIRED = "EXPIRED";

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

}
