package com.mrjeffapp.provider.client.product;

import com.mrjeffapp.provider.client.product.model.Product;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@FeignClient(name = "product-service", decode404 = true)
public interface ProductServiceClient {

	@RequestMapping(method = GET, value = "/api/products/{id}")
    Resource<Product> findOne(@PathVariable("id") String productId);

	@RequestMapping(method = GET, value = "/api/products")
    PagedResources<Product> findAll();

	@RequestMapping(method = GET, value = "/api/products/search/findByIdIn")
    PagedResources<Product> findProductsByIdIn(@RequestParam("ids") String ids, @RequestParam("size") Integer size);

}
