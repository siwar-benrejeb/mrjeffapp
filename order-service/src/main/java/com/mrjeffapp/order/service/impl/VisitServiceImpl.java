package com.mrjeffapp.order.service.impl;

import com.mrjeffapp.order.client.customer.CustomerClient;
import com.mrjeffapp.order.client.customer.model.Address;
import com.mrjeffapp.order.domain.Visit;
import com.mrjeffapp.order.exception.InvalidAddressException;
import com.mrjeffapp.order.exception.InvalidVisitException;
import com.mrjeffapp.order.repository.VisitRepository;
import com.mrjeffapp.order.service.EventPublisherService;
import com.mrjeffapp.order.service.VisitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedResources;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class VisitServiceImpl implements VisitService {
    private static final Logger LOG = LoggerFactory.getLogger(OrderBuilderImpl.class);

    private final VisitRepository visitRepository;

    //private final AdministrativeClient administrativeClient;

    private final CustomerClient customerClient;

    private final EventPublisherService eventPublisherService;

    @Autowired
    public VisitServiceImpl(VisitRepository visitRepository/*, AdministrativeClient administrativeClient*/, CustomerClient customerClient, EventPublisherService eventPublisherService) {
        this.visitRepository = visitRepository;
        //this.administrativeClient = administrativeClient;
        this.customerClient = customerClient;
        this.eventPublisherService = eventPublisherService;
    }

    @Override
    @Transactional
    public Visit relocateVisit(String customerId, String visitId, Date relocationDate, String relocationTimeSlotCode) {
        Visit visit = findVisit(customerId, visitId);

        Visit relocatedVisit = relocateVisit(relocationDate, relocationTimeSlotCode, visit);
        return relocatedVisit;
    }

    private Visit findVisit(String customerId, String visitId) {
        Optional<Visit> visitOptional = visitRepository.findByOrderCustomerIdAndId(customerId, visitId);

        if(!visitOptional.isPresent()) {
            String message = "Not found visit: customerId=" + customerId + ", visitId=" + visitId;
            LOG.warn(message);
            throw new InvalidVisitException(message);
        }

        return visitOptional.get();
    }

    private Visit relocateVisit(Date relocationDate, String relocationTimeSlotCode, Visit visit) {
        String postalCodeId = visit.getPostalCodeId();

        String timeTableTypeCode = visit.getOrder().getOrderType().getCode();
        String visitTypeCode = visit.getVisitTypeCode();

//        String requestMessage = String.format("Time slot not available orderTypeCode=%s, postalCodeId=%s, date=%s, visitType=%s, timeRangeCode=%s", timeTableTypeCode, postalCodeId, relocationDate, visitTypeCode, relocationTimeSlotCode);
//        LOG.debug("AdministrativeClient.timeTableAvailability, request={}", requestMessage);
//
//        TimeTableAvailableResponse response = administrativeClient.timeTableAvailability(timeTableTypeCode, postalCodeId, relocationDate, visitTypeCode, relocationTimeSlotCode);
//        LOG.debug("AdministrativeClient.timeTableAvailability, response={}", response);
//
//        if(true) {
//            String message = String.format("Time slot not available orderTypeCode=%s, postalCodeId=%s, date=%s, visitType=%s, timeRangeCode=%s", timeTableTypeCode, postalCodeId, relocationDate, visitTypeCode, relocationTimeSlotCode);
//            LOG.warn(message);
//            // throw new InvalidTimeSlotException(message);
//        }

        visit.setDate(relocationDate);
        visit.setTimeSlotStart(visit.getTimeSlotStart());
        visit.setTimeSlotEnd(visit.getTimeSlotEnd());
        //visit.setTimeTableTimeSlotId(visit.getTimetableTimeSlotId());

        return visitRepository.save(visit);
    }

    @Override
    @Transactional
    public Visit relocateVisit(String customerId, String visitId, Date relocationDate, String relocationTimeSlotCode, String addressId) {
        PagedResources<Address> addressesResources = customerClient.findAddressesByCustomerIdAndIdInAndActiveTrue(customerId, addressId.toString());
        if(addressesResources == null) {
            String message = "Not found address: customerId=" + customerId + ", visitId=" + visitId;
            LOG.warn(message);
            throw new InvalidAddressException(message);
        }

        Address address = addressesResources.getContent().stream().findFirst().get();

        Visit visit = findVisit(customerId, visitId);
        visit.setAddressId(address.getId());
        visit.setPostalCode(address.getPostalCode());
        visit.setPostalCodeId(address.getPostalCodeId());
        visit.setCityId(address.getCityId());
        visit.setCountryId(address.getCountryId());

        Visit relocatedVisit = relocateVisit(relocationDate, relocationTimeSlotCode, visit);

        eventPublisherService.sendVisitRelocatedEvent(visit);

        return relocatedVisit;
    }

}
