package com.mrjeffapp.provider.config;

import com.mrjeffapp.provider.config.feign.DateFormatter;
import org.springframework.cloud.netflix.feign.FeignFormatterRegistrar;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;

@Configuration
public class FeignFormatterRegister implements FeignFormatterRegistrar {

    @Override
    public void registerFormatters(FormatterRegistry registry) {
        registry.addFormatter(new DateFormatter());
    }
}