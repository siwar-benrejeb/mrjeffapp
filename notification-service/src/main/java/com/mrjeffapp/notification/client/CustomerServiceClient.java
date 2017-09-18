package com.mrjeffapp.notification.client;

import com.mrjeffapp.notification.client.model.Customer;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@FeignClient(value = "customer-service", decode404 = true)
public interface CustomerServiceClient {

    @RequestMapping(method = GET, value = "/api/customers/{id}")
    Resource<Customer> findCustomerById(@PathVariable("id") String customerId, @RequestParam("projection") String projection);

}