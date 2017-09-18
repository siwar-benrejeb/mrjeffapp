package com.mrjeffapp.customer.client.admin;

import com.mrjeffapp.customer.client.admin.model.ValidateAddressResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@FeignClient(value = "administrative-service", decode404 = true)
public interface AdministrativeClient {

    @RequestMapping(method = GET, value = "/postalCodes/validate")
    ValidateAddressResponse validatePostalCode(@RequestParam("postalCode") String postalCode, @RequestParam("cityCode") String cityCode, @RequestParam("countryCode") String countryCode);

}
