package com.mrjeffapp.order.client.customer;

import com.mrjeffapp.order.client.customer.model.Address;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.hateoas.PagedResources;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@FeignClient(name = "customer-service", decode404 = true)
public interface CustomerClient {

    @RequestMapping(method = GET, value = "/api/addresses/search/findByIdIn")
    PagedResources<Address> findAddressByIdIn(@RequestParam("ids") String ids);

    @RequestMapping(method = GET, value = "/api/addresses/search/findByCustomerIdAndIdInAndActiveTrue")
    PagedResources<Address> findAddressesByCustomerIdAndIdInAndActiveTrue(@RequestParam("customerId") String customerId, @RequestParam("ids") String ids);

}
