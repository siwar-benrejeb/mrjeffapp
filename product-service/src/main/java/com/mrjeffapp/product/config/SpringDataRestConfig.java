package com.mrjeffapp.product.config;

import com.mrjeffapp.product.domain.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

@Configuration
public class SpringDataRestConfig extends RepositoryRestConfigurerAdapter {

	@Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
	    config.exposeIdsFor(ProductType.class)
                .exposeIdsFor(Product.class)
                .exposeIdsFor(ProductItem.class)
                .exposeIdsFor(Item.class)
                .exposeIdsFor(VATRate.class)
        	    .exposeIdsFor(Channel.class)
				.exposeIdsFor(Periodicity.class);
    }

}
