package com.mrjeffapp.order.config;

public class Constants {
	public static final String SPRING_PROFILE_TEST = "test";
	public static final String ORDER_STATUS_CREATED = "CREATED";

	public static final String VISIT_TYPE_CODE_PICKUP = "PICKUP";
	public static final String VISIT_TYPE_CODE_DELIVERY = "DELIVERY";

	public static class Event {
		public static final String SERVICE_EXCHANGE = "order-service/amq.topic";
		public static final String ROUTING_ORDER_STATE_BASE = "order.state.";
		public static final String QUEUE_PROVIDER_ASSIGNMENT = "order-service/provider-assignment.queue";
		public static final String ROUTING_VISIT_STATE_CREATED = "visit.state.created";
		public static final String ROUTING_VISIT_STATE_RELOCATED = "visit.state.relocated";
	}

}
