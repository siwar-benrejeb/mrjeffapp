package com.mrjeffapp.customer.config;

public class Constants {
	public static final String SPRING_PROFILE_TEST = "test";
	public static final String CUSTOMER_TYPE_PRIVATE_CODE = "PRIVATE";
	public static final String CUSTOMER_TYPE_BUSINESS_CODE = "BUSINESS";

	public static class Event {
		public static final String SERVICE_EXCHANGE = "customer-service/amq.topic";
		public static final String ROUTING_CUSTOMER_CREATED = "customer.created";
		public static final String ROUTING_CUSTOMER_EMAIL_UPDATED = "customer.email.updated";
		public static final String ROUTING_ADDRESS_CREATED = "address.created";
		public static final String BUSINESS_EVENT_LOGGER = "BUSINESS_EVENT";
		public static final String OUEUE_CUSTOMER_CREATION = "notification-service/customer-creation.queue";

	}

}
