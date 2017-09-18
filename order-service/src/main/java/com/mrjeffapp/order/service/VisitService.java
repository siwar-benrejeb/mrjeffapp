package com.mrjeffapp.order.service;

import com.mrjeffapp.order.domain.Visit;

import java.util.Date;

public interface VisitService {

    Visit relocateVisit(String customerId, String visitId, Date relocationDate, String relocationTimeSlotCode);

    Visit relocateVisit(String customerId, String visitId, Date relocationDate, String relocationTimeSlotCode, String addressId);

}
