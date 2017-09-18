package com.mrjeffapp.notification.client;

import com.mrjeffapp.notification.client.model.ProviderProduct;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.hateoas.PagedResources;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.web.bind.annotation.RequestMethod.GET;


@FeignClient(value = "provider-service", decode404 = true)
public interface ProviderServiceClient {

    @RequestMapping(method = GET, value = "/api/products/search/findByWorkOrderId")
    PagedResources<ProviderProduct> findByWorkOrderId(@RequestParam("workOrderId") String workOrderId, @RequestParam("size") Integer size);


}
