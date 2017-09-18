package com.mrjeffapp.provider.service.Authorization;

import java.util.Date;

interface ToExpiredAuthorizationService {

    void toExpiredAuthorizationByDeliveryDate(Date deliveryDate, String countryCode, String userOperator);
}
