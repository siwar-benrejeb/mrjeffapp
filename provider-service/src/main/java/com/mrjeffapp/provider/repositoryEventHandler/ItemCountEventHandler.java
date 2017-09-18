package com.mrjeffapp.provider.repositoryEventHandler;

import com.mrjeffapp.provider.domain.ItemCount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import static com.mrjeffapp.provider.config.Constants.BUSINESS_EVENT_LOGGER;

@Component
@RepositoryEventHandler(ItemCount.class)
public class ItemCountEventHandler {

    private static final Logger BUSINESS_LOG = LoggerFactory.getLogger(BUSINESS_EVENT_LOGGER);

    @HandleAfterCreate
    public void handleItemCountSave(ItemCount itemCount) {

        BUSINESS_LOG.info("Product: " + itemCount.getProductId() +
                " has been validate: " + itemCount.getId() +
                " by: " + itemCount.getUser().getId() + " - " + itemCount.getUser().getEmail());
    }
}
