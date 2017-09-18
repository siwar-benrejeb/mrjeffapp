package com.mrjeffapp.provider.config;

import com.mrjeffapp.provider.domain.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

@Configuration
public class SpringDataRestConfig extends RepositoryRestConfigurerAdapter {

	@Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
                config.exposeIdsFor(Authorization.class)
                        .exposeIdsFor(AuthorizationState.class)
                        .exposeIdsFor(AuthorizationType.class)
                        .exposeIdsFor(Incident.class)
                        .exposeIdsFor(IncidentType.class)
                        .exposeIdsFor(ItemCount.class)
                        .exposeIdsFor(Product.class)
                        .exposeIdsFor(Provider.class)
                        .exposeIdsFor(User.class)
                        .exposeIdsFor(WorkOrder.class)
                        .exposeIdsFor(WorkOrderNote.class)
                        .exposeIdsFor(WorkOrderState.class)
                        .exposeIdsFor(Notification.class)
                ;
    }

    @Bean
    public SpelAwareProxyProjectionFactory projectionFactory() {
        return new SpelAwareProxyProjectionFactory();
    }

}
