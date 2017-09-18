package com.mrjeffapp.provider.domain.event;

import java.util.List;

public class AuthorizationAuthorizedEvent {

    private List<String> authorizationsAuthorized;
    private String userOperator;
    private final static String description = "El producto ha sido autorizado";

    public AuthorizationAuthorizedEvent(List<String> authorizationsAuthorized, String userOperator) {
        this.authorizationsAuthorized = authorizationsAuthorized;
        this.userOperator = userOperator;
    }

    public List<String> getAuthorizationsAuthorized() {
        return authorizationsAuthorized;
    }

    public String getUserOperator() {
        return userOperator;
    }

    public String getDescription() {
        return description;
    }
}
