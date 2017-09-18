package com.mrjeffapp.provider.service;

import com.mrjeffapp.event.ProductsAuthorizationsEvent;
import com.mrjeffapp.event.ProviderAssignmentEvent;

public interface EventPublisherService {
    ProviderAssignmentEvent sendProviderOrderAssignmentEvent(String orderId, String providerId);

    void sendAddProductEvent(ProductsAuthorizationsEvent productAuthorizationEvents);


}
