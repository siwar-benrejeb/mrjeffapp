INSERT INTO `customer_type` (`id`, `code`, `description`, `name`, `active`)
VALUES
	('1', 'B2C', NULL, 'B2C', true),
	('2', 'B2B', NULL, 'B2B', true);

INSERT INTO `customer` (`id`, `creation_date`, `email`, `enabled`, `last_modified_date`, `last_name`, `name`, `password`, `reset_password`, `phone_number`, `customer_type_id`, `password_migrated`, `language_code`)
VALUES
	('1', '2016-11-11 00:00:00', 'test@test.com', 1, NULL, 'last_name', 'name', 'test', false, '622200000', '1', true, 'es'),
	('2', '2016-11-11 00:00:00', 'test2@test2.com', 1, NULL, 'last_name2', 'name2', 'test', false, '622200000', '2', true, 'es');


INSERT INTO `address` (`id`, `customer_id`, `active`, `address`, `address_number`, `address_details`, `city_id`, `country_id`, `creation_date`, `alias`, `phone_number`, `postal_code`, `postal_code_id`, `region_id`, `default_pickup`, `default_delivery`, `default_billing`, `city_code`, `city_name`, `country_code`, `country_name`)
VALUES
	('1', '1', 1, 'address_line', '1', 'detail1', '1', '1', '2016-10-11 00:00:00', 'full_name', '622200000', '4600', '1', '1', 1, 1, 1, 'VLC','VALENCIA','ES','ESPAÑA'),
	('2', '1', 1, 'address_line2', '2', 'detail2', '1', '1', '2016-10-11 00:00:00', 'full_name2', '622200001', '4601', '1', '1', 1, 1, 1, 'VLC','VALENCIA','ES','ESPAÑA'),
	('3', '1', 1, 'address_line3', '3', 'detail3', '1', '1', '2016-10-11 00:00:00', 'full_name3', '622200002', '4601', '1', '1', 1, 1, 1, 'VLC','VALENCIA','ES','ESPAÑA');