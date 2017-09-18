package com.mrjeffapp.provider.service;

import com.mrjeffapp.provider.domain.Provider;

public interface ProviderAssignmentService {

    Provider findProviderAssigned(String cityId);

}
