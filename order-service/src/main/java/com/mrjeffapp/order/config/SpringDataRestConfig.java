package com.mrjeffapp.order.config;

import com.mrjeffapp.order.domain.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

@Configuration
public class SpringDataRestConfig extends RepositoryRestConfigurerAdapter {

	@Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
            config.exposeIdsFor(Channel.class)
                .exposeIdsFor(Order.class)
                .exposeIdsFor(OrderStatus.class)
                .exposeIdsFor(OrderStatusTracking.class)
                .exposeIdsFor(ProductOrder.class)
                .exposeIdsFor(Visit.class)
            ;
    }

    @Bean
    public SpelAwareProxyProjectionFactory projectionFactory() {
        return new SpelAwareProxyProjectionFactory();
    }

}
