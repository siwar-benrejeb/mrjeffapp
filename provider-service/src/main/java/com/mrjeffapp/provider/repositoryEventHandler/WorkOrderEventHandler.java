package com.mrjeffapp.provider.repositoryEventHandler;

import com.mrjeffapp.provider.domain.WorkOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import static com.mrjeffapp.provider.config.Constants.BUSINESS_EVENT_LOGGER;

@Component
@RepositoryEventHandler(WorkOrder.class)
public class WorkOrderEventHandler {

    private static final Logger BUSINESS_LOG = LoggerFactory.getLogger(BUSINESS_EVENT_LOGGER);

    @HandleAfterSave
    public void handleWorkOrderSave(WorkOrder workOrder) {

        BUSINESS_LOG.info("WorkOrder: " + workOrder.getId() +
                " has been update: " + workOrder.getWorkOrderState().getName());
    }
}
