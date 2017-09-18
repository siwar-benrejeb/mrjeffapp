package com.mrjeffapp.provider.domain.projection;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mrjeffapp.provider.domain.WorkOrderNote;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(name = "full", types = { WorkOrderNote.class })
public interface FullProjectionWorkOrderNote {

    String getId();

    String getText();

    FullProjectionUser getUser();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    Date getCreationDate();

}
