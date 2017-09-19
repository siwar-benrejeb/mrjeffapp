INSERT INTO `order_type` (`id`, `code`, `description`, `name`, `active`)
VALUES
	('1', 'B2C', 'Pedido B2C', 'B2C', 1);

INSERT INTO `channel` (`id`, `active`, `code`, `description`, `name`)
VALUES
	('1', 1, 'ECOMMERCE', 'Ventas desde la Ecommerce app', 'Ecommerce app');

INSERT INTO `order_status` (`id`, `code`, `description`, `name`, `active`)
VALUES
	('1', 'CREATED', 'Nuevo pedido', 'Pedido', 1);

INSERT INTO "order" (`id`, `code`, `coupon_id`, `country_id`, `country_code`, `creation_date`, `postal_code_id`, `headquarter_id`, `last_modified_date`, `note`, `order_date`, `provider_id`, `total_price`, `total_price_without_discount`, `total_price_discount`, `customer_id`, `order_status_id`, `channel_id`, `order_type_id`, `payment_method_code`, `billing_address_id`)
VALUES
	('1', 'ES12345', '1', '1', 'ES', '2017-02-09 16:08:51', '8023', '1', '2017-02-09 16:08:51', 'TEST NOTE', '2016-12-12 16:08:50', '1', 100, 90, 0, '118', '1', '1', '1', 'PAYPAL', '1');


INSERT INTO `product_order` (`id`, `creation_date`, `product_id`, "order_id", `quantity`)
VALUES
	('1', '2017-02-09 13:49:06', '1', '1', 10);


INSERT INTO `visit` (`id`, `address_id`, `date`, `postal_code_id`, `postal_code`, `city_id`, `country_id`, `visit_type_code`, `time_table_time_slot_id`, `time_slot_start`, `time_slot_end`, "order_id", `creation_date`, `last_modification_date`)
VALUES
	('1', '1', '2017-02-09 13:49:05', 1, '1', 1, 1, 'PICKUP', 1, '11:00:00', '12:00:00', 1, '2017-02-09 13:49:06', '2017-02-09 13:49:06'),
	('2', '2', '2017-02-09 13:49:05', 1, '1', 1, 1, 'DELIVERY', 1, '11:00:00', '12:00:00', 1, '2017-02-09 13:49:06', '2017-02-09 13:49:06');
