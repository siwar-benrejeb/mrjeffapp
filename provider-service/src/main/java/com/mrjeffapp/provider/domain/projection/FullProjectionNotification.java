package com.mrjeffapp.provider.domain.projection;

import com.mrjeffapp.provider.domain.Notification;
import com.mrjeffapp.provider.domain.Product;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(name = "full", types = { Notification.class })
public interface FullProjectionNotification {

    String getId();

    String getUserId();

    String getProviderId();

    String getDescription();

    String getCode();

    String getState();

    Product getProduct();

    Date getCreationDate();

}
