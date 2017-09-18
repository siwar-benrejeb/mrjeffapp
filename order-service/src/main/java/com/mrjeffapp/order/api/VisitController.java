package com.mrjeffapp.order.api;

import com.mrjeffapp.order.api.dto.VisitRelocateAddressRequest;
import com.mrjeffapp.order.api.dto.VisitRelocateRequest;
import com.mrjeffapp.order.domain.Visit;
import com.mrjeffapp.order.domain.projection.FullProjectionVisit;
import com.mrjeffapp.order.service.VisitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/visits")
public class VisitController {
    private static final Logger LOG = LoggerFactory.getLogger(VisitController.class);

    private final VisitService visitService;

    private final ProjectionFactory projectionFactory;

    @Autowired
    public VisitController(VisitService visitService, ProjectionFactory projectionFactory) {
        this.visitService = visitService;
        this.projectionFactory = projectionFactory;
    }

    @PatchMapping("{visitId}/relocate")
    public FullProjectionVisit relocateVisit(@PathVariable("visitId") String visitId, @Valid @RequestBody VisitRelocateRequest visitRelocateRequest) {
        LOG.debug("relocate visit: visitId={}, request={}", visitId, visitRelocateRequest);

        String timeSlotCode = visitRelocateRequest.getTimeSlotCode();
        Date relocationDate = visitRelocateRequest.getDate();
        String customerId = visitRelocateRequest.getCustomerId();

        Visit visit = visitService.relocateVisit(customerId, visitId, relocationDate, timeSlotCode);

        LOG.info("Updated order: visitId={}, userId={}", visitId, visitRelocateRequest.getCustomerId());

        FullProjectionVisit visitProjection = projectionFactory.createProjection(FullProjectionVisit.class, visit);
        return visitProjection;
    }

    @PatchMapping("{visitId}/relocate/address")
    public FullProjectionVisit relocateVisitToAddress(@PathVariable("visitId") String visitId, @Valid @RequestBody VisitRelocateAddressRequest visitRelocateAddressRequestRequest) {
        LOG.debug("relocate visit: visitId={}, request={}", visitId, visitRelocateAddressRequestRequest);

        String timeSlotCode = visitRelocateAddressRequestRequest.getTimeSlotCode();
        Date relocationDate = visitRelocateAddressRequestRequest.getDate();
        String customerId = visitRelocateAddressRequestRequest.getCustomerId();
        String addressId = visitRelocateAddressRequestRequest.getAddressId();

        Visit visit = visitService.relocateVisit(customerId, visitId, relocationDate, timeSlotCode, addressId);

        LOG.info("Updated order: visitId={}, userId={}", visitId, visitRelocateAddressRequestRequest.getCustomerId());

        FullProjectionVisit visitProjection = projectionFactory.createProjection(FullProjectionVisit.class, visit);
        return visitProjection;
    }

}
