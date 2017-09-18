package com.mrjeffapp.notification.client;

import com.mrjeffapp.notification.client.model.Order;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@FeignClient(value = "order-service", decode404 = true)
public interface OrderServiceClient {

    @RequestMapping(method = GET, value = "/api/orders/{id}")
    Resource<Order> findOrderById(@PathVariable("id") String orderId, @RequestParam("projection") String projection);

}
