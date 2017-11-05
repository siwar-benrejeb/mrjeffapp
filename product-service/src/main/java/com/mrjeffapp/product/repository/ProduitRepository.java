package com.mrjeffapp.product.repository;

import com.mrjeffapp.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProduitRepository extends JpaRepository<Product, String> {
}
