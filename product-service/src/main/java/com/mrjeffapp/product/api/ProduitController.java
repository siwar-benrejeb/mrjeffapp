package com.mrjeffapp.product.api;

import com.mrjeffapp.product.domain.Product;
import com.mrjeffapp.product.domain.ProductType;
import com.mrjeffapp.product.domain.VATRate;
import com.mrjeffapp.product.repository.ProductRepository;
import com.mrjeffapp.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by siwsiw on 19/08/2017.
 */
@RestController
public class ProduitController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductService produitService;

    @RequestMapping(value = "/products/save", method = RequestMethod.POST)
    @ResponseBody
    public Product saveProduct(@RequestBody Product product){
        ProductType productType = new ProductType();
        productType.setId("956a65f8-610e-4eed-b33c-f0e4b6a0cd11");
        product.setProductType(productType);
        VATRate vatRate = new VATRate();
        vatRate.setId("2a093f5c-6282-477a-877b-af6f8a435abb");
        product.setVatRate(vatRate);
        product.setCountryId("090efc78-7d02-4a46-909b-f7c3fc635f24");

        return productRepository.save(product);
    }

    @RequestMapping(value = "/products/all", method = RequestMethod.GET)
    @ResponseBody
    public List<Product> allProduct(){
        return produitService.findAll();
    }


    @RequestMapping(value = "/products/findById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Product findById(@PathVariable("id") String id){
        return produitService.findById(id);
    }

}
