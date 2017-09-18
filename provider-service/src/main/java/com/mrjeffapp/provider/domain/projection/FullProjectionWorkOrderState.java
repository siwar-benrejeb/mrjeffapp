package com.mrjeffapp.provider.domain.projection;

import com.mrjeffapp.provider.domain.WorkOrderState;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "full", types = { WorkOrderState.class })
public interface FullProjectionWorkOrderState {

    String getId();

    String getCode();

    String getName();

    String getDescription();

}
