package com.mrjeffapp.order;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@EnableEurekaClient
@EnableFeignClients
@EnableRabbit
@SpringBootApplication
public class OrderServiceApp {
	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApp.class, args);
	}
}
