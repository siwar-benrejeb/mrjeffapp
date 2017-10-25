package com.mrjeffapp.product.service.impl;

import com.mrjeffapp.product.domain.Product;
import com.mrjeffapp.product.repository.ProduitRepository;
import com.mrjeffapp.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * Created by siwsiw on 23/08/2017.
 */
@Service
public class ProduitServiceImpl implements ProductService {

    @Autowired
    ProduitRepository produitRepository;
    @Override
    public List<Product> findAll(){
        return produitRepository.findAll();
    }

    @Override
    public Product findById(String id) {
        return produitRepository.findOne(id);
    }

    @Override
    public Collection<Product> findByIdInProducts(@Param("ids") Collection<String> ids) {
        return produitRepository.findAll(ids);
    }


}
