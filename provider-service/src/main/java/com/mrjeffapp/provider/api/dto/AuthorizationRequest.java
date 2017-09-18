package com.mrjeffapp.provider.api.dto;

import javax.validation.constraints.NotNull;
import java.util.List;

public class AuthorizationRequest {

    @NotNull
    List<String> authorizationList;

    @NotNull
    private String userOperator;


    public List<String> getAuthorizationList() {
        return authorizationList;
    }

    public void setAuthorizationList(List<String> authorizationList) {
        this.authorizationList = authorizationList;
    }

    public String getUserOperator() {
        return userOperator;
    }

    public void setUserOperator(String userOperator) {
        this.userOperator = userOperator;
    }
}
