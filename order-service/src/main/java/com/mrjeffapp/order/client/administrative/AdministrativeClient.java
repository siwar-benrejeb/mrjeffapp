package com.mrjeffapp.order.client.administrative;

import com.mrjeffapp.order.client.administrative.model.TimeTableAvailableResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@FeignClient(name = "administrative-service", decode404 = true)
public interface AdministrativeClient {

    @RequestMapping(method = GET, value = "/timetables/available")
    TimeTableAvailableResponse timeTableAvailability(@RequestParam("timeTableTypeCode") String timeTableTypeCode,
                                                     @RequestParam("postalCodeId") String postalCodeId,
                                                     @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
                                                     @RequestParam("visitTypeCode") String visitTypeCode,
                                                     @RequestParam("timeSlotCode") String timeSlotCode);

}
