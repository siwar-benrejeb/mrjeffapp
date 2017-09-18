package com.mrjeffapp.provider.api;

import com.mrjeffapp.provider.api.dto.ProductCreateRequest;
import com.mrjeffapp.provider.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/product")
public class ProductController {

    private static final Logger LOG = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    private final ProjectionFactory projectionFactory;
    @Autowired
    public ProductController(ProductService productService, ProjectionFactory projectionFactory) {
        this.productService = productService;
        this.projectionFactory = projectionFactory;


    }

    @PostMapping("/create")
    public void create(@Valid @RequestBody ProductCreateRequest productCreateRequest) {

        LOG.debug("Creation product saas request: {}", productCreateRequest);

        productService.createProduct(
                productCreateRequest
        );

        LOG.debug("Finish Creation product saas");

    }

}
