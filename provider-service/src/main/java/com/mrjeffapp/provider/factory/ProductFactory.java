package com.mrjeffapp.provider.factory;

import com.mrjeffapp.provider.domain.*;
import com.mrjeffapp.provider.repository.AuthorizationStateRepository;
import com.mrjeffapp.provider.repository.AuthorizationTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.mrjeffapp.provider.domain.Authorization.ITEM_ADD;
import static com.mrjeffapp.provider.domain.AuthorizationState.WAITING;

@Service
public class ProductFactory implements ProductFactoryInterface {

    private static final Logger LOG = LoggerFactory.getLogger(ProductFactory.class);

    private final AuthorizationStateRepository authorizationStateRepository;
    private final AuthorizationTypeRepository authorizationTypeRepository;

    @Autowired
    public ProductFactory(AuthorizationStateRepository authorizationStateRepository,
                          AuthorizationTypeRepository authorizationTypeRepository) {

        this.authorizationStateRepository = authorizationStateRepository;
        this.authorizationTypeRepository = authorizationTypeRepository;
    }

    @Override
    @Transactional
    public Product build(String productId, Integer quantity, WorkOrder workOrder, User user) {

        LOG.debug("Start product factory build");

        Product product = new Product(productId, quantity, workOrder, user);

        AuthorizationType addProductAuth = authorizationTypeRepository.findByCode(ITEM_ADD);
        AuthorizationState waitingStateAuth = authorizationStateRepository.findByCode(WAITING);

        Authorization auth = new Authorization(product, waitingStateAuth, addProductAuth);

        LOG.debug("Finish product factory build");

        return product;
    }
}
