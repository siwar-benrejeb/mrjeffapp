package com.mrjeffapp.provider.domain;

    import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Authorization {

    public static final String ITEM_ADD = "ITEM_ADD";

    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.AUTO)
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition="CHAR(36)")
    private String id;

    @NotNull
    @ManyToOne
    private AuthorizationState authorizationState;

    @NotNull
    @ManyToOne
    private AuthorizationType authorizationType;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(columnDefinition="CHAR(36)")
    private String userOperator;

    @NotNull
    @CreatedDate
    private Date creationDate;

    public Authorization() {}

    public Authorization(Product product, AuthorizationState authorizationState, AuthorizationType authorizationType) {
        this.product = product;
        associateProduct(product);
        this.authorizationState = authorizationState;
        this.authorizationType = authorizationType;
    }

    public Authorization(String id, Product product, AuthorizationState authorizationState, AuthorizationType authorizationType) {
        this.id = id;
        this.product = product;
        associateProduct(product);
        this.authorizationState = authorizationState;
        this.authorizationType = authorizationType;
    }

    private void associateProduct(Product product) {
        this.product = product;
        product.associateAuthorization(this);

    }
    public String getId() {
        return id;
    }

    public AuthorizationState getAuthorizationState() {
        return authorizationState;
    }

    public AuthorizationType getAuthorizationType() {
        return authorizationType;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Product getProduct() {
        return product;
    }

    public void changeAuthorizationState(AuthorizationState auth, String userOperator) {
        this.authorizationState = auth;
        this.userOperator = userOperator;
    }

    public Provider getProvider()
    {
        return this.getProduct().getProvider();
    }


}
