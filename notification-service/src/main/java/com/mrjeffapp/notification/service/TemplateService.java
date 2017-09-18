package com.mrjeffapp.notification.service;

import java.util.Map;

public interface TemplateService {

    String renderBodyTemplate(String templateCode, String countryCode, Map<String, Object> parameters);
    String renderSubjectTemplate(String templateCode, String countryCode, Map<String, Object> parameters);
}
