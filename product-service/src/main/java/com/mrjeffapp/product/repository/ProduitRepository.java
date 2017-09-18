package com.mrjeffapp.product.repository;

import com.mrjeffapp.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

public interface ProduitRepository extends JpaRepository<Product, String> {
}
