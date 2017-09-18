package com.mrjeffapp.notification.client;

import com.mrjeffapp.notification.client.model.Product;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.hateoas.PagedResources;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@FeignClient(value = "product-service", decode404 = true)
public interface ProductServiceClient {

    @RequestMapping(method = GET, value = "/api/products/search/findByIdIn")
    PagedResources<Product> findProductsByIdIn(@RequestParam("ids") String ids, @RequestParam("size") Integer size);

}
