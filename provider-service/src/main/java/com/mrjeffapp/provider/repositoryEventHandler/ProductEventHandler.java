package com.mrjeffapp.provider.repositoryEventHandler;


import com.mrjeffapp.provider.domain.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import static com.mrjeffapp.provider.config.Constants.BUSINESS_EVENT_LOGGER;

@Component
@RepositoryEventHandler(Product.class)
public class ProductEventHandler {

    private static final Logger BUSINESS_LOG = LoggerFactory.getLogger(BUSINESS_EVENT_LOGGER);

    @HandleAfterCreate
    public void handleItemCountSave(Product product) {

        BUSINESS_LOG.info("Product: " + product.getId() +
                " has been create by: " + product.getUser().getId() + " - " + product.getUser().getEmail());
    }
}
