package com.mrjeffapp.customer.domain.projection;

import com.mrjeffapp.customer.domain.CustomerNote;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(name = "full", types = { CustomerNote.class })
public interface FullProjectionCustomerNote {

    String getId();
    String getNote();
    String getUserId();
    Date getCreationDate();
    Date getLastModificationDate();

}
