package com.mrjeffapp.order.client.product;

import com.mrjeffapp.order.client.product.model.OrderCalculatorRequest;
import com.mrjeffapp.order.client.product.model.OrderCalculatorResponse;
import com.mrjeffapp.order.client.product.model.Product;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@FeignClient(name = "product-service", decode404 = true)
public interface ProductClient {


    @RequestMapping(method = GET, value = "/api/products/search/findByIdIn")
    Resources<Product> findProductsByIdIn(@RequestParam("ids") String ids);

    @RequestMapping(method = GET, value = "/products/findByIdInProducts")
    Resources<Product> findProductsByIdInProducts(@RequestParam("ids") String ids);

    @RequestMapping(method = GET, value = "api/products/search/findByCodeInAndActiveTrue")
    Resources<Product> findByCodeInAndActiveTrue(@RequestParam("codes") String codes);

    @RequestMapping(method = POST, value = "order/total")
    OrderCalculatorResponse calculateOrderTotal(OrderCalculatorRequest orderCalculatorRequest);

}
