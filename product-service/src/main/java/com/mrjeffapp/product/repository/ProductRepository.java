package com.mrjeffapp.product.repository;

import com.mrjeffapp.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Collection;

@RepositoryRestResource
public interface ProductRepository extends PagingAndSortingRepository<Product, String> {

    Collection<Product> findByIdIn(@Param("ids") Collection<String> ids);

    Collection<Product> findByCodeInAndActiveTrue(@Param("codes") Collection<String> codes);

    Page<Product> findByIdIn(@Param("ids") Collection<String> ids, Pageable pageable);


    @Query("select p from Product p where p.active = true and p.countryId = :countryId")
    Page<Product> findAll(@Param("countryId") String countryId, Pageable pageable);

    @Query("select p from Product p "
            + "join p.channels c "
            + "where p.active = true "
            + "and p.productType.code = :productType "
            + "and p.countryId = :countryId "
            + "and c.code = :channel "
    )
    Page<Product> findByProductType(@Param("productType") String productType, @Param("channel") String channel, @Param("countryId") String countryId, Pageable pageable);

    Collection<Product> findByCodeIn(@Param("codes") Collection<String> codes);

    Collection<Product> findByNameContainingIgnoreCaseAndActiveTrue(@Param("name") String name);

    Page<Product> findByCountryCodeAndActiveTrue(@Param("countryCode") String countryCode, Pageable pageable);


}
