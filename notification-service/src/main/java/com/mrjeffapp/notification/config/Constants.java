package com.mrjeffapp.notification.config;

public class Constants {
	public static final String SPRING_PROFILE_TEST = "test";

	public static class Event {
		public static final String QUEUE_ORDER_CREATION = "notification-service/order-creation.queue";
		public static final String SERVICE_EXCHANGE = "notification-service/amq.topic";
		public static final String OUEUE_CUSTOMER_CREATION = "notification-service/customer-creation.queue";
		public static final String QUEUE_RESET_PASSWORD = "notification-service/reset-password.queue";
		public static final String QUEUE_ORDER_PENDING = "notification-service/order-pending.queue";
		public static final String QUEUE_ORDER_CANCELLED_UNPAID = "notification-service/order-cancelled-unpaid.queue";
		public static final String QUEUE_ORDER_CANCELLED = "notification-service/order-cancelled.queue";
		public static final String QUEUE_REMINDER_UNPAID_ORDER_30MINUTES = "notification-service/reminder-unpaid-oder-30minutes.queue";
		public static final String QUEUE_REMINDER_UNPAID_ORDER = "notification-service/reminder-unpaid-oder.queue";
		public static final String QUEUE_CHANGING_SCHEDUELE_FREE = "notification-service/changing-scheduele-free.queue";
		public static final String QUEUE_ORDER_CONDOMINIO = "notification-service/order-condominio.queue";
		public static final String QUEUE_CHANGING_SCHEDULE_WITH_CHARGE = "notification-service/changing-schedule-with-charge.queue";
		public static final String QUEUE_ORDER_FAILURE = "notification-service/order-failure.queue";
		public static final String QUEUE_SUBSCRIPTION = "notification-service/subscription.queue";

	}
}
