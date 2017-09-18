INSERT INTO `product_type` (`id`, `active`, `code`, `description`, `name`)
VALUES
	('a028f8af-efc6-440b-ae7c-7a52a721b308', 1, 'LAUNDRY', NULL, 'Lavandería'),
	('1d20aaee-3f02-4081-90bd-19a63c79de48', 1, 'DRYCLEANING', NULL, 'Tintorería'),
	('327e5d89-7882-41f0-a56d-551ca3d1759a', 1, 'OTHER', NULL, 'Otros'),
	('1fff363a-5749-41d1-8d23-a087690a996c', 1, 'OFFER', NULL, 'Ofertas'),
	('4bc3cfe9-eac0-4015-bfc0-5090082b18b0', 1, 'SUBSCRIPTION', NULL, 'Subscripción');

INSERT INTO `vat_rate` (`id`, `active`, `country_id`, `description`, `name`, `percentage`)
VALUES
	('2a093f5c-6282-477a-877b-af6f8a435abb', 1, 1, 'IVA general aplicado en España', 'IVA general', 21);

INSERT INTO `channel` (`id`, `active`, `code`, `description`, `name`)
VALUES
	('87d7de25-c300-4a16-ba5e-ce026fc14bcd', 1, 'WEBAPP', 'Ventas desde la Ecommerce app', 'Ecommerce app'),
	('34d038bd-dd11-464f-ad12-e3dfa77202fd', 1, 'ANDROID', 'Ventas desde la app Android', 'Android app'),
	('3a6f6694-5c34-4ce8-a057-020f50d827c2', 1, 'IOS', 'Ventas desde la app Android', 'IOS app'),
	('badbeb55-ec77-4140-87f6-af1158a03c28', 1, 'PHONE', 'Ventas telefonicas', 'Phone call'),
	('0b7e3f7d-deb9-4e04-9cb2-d610dfd2309e', 1, 'CHATBOT', 'Ventas desde el ChatBot', 'Chatbot app');

INSERT INTO `periodicity` (`id`, `active`, `code`, `description`, `name`)
VALUES
	('f1e0ffcb-086d-4c23-bd22-5658e40b7cd5', 1, 'NO_PERIODICITY', 'El producto no puede ser recurrente', 'No recurrente'),
	('85e2dc5d-2063-4f10-a97d-63d8c4ea5a72', 1, 'WEEKLY', 'El producto puede ser recurrente cada semana', 'Semanal'),
	('d88edbbc-5725-4ae6-a59e-d195a13f4174', 1, 'BIWEEKLY', 'El producto puede ser recurrente cada dos semana', 'Quincenal'),
	('185f5735-9945-486d-8444-3192e4809685', 1, 'MONTHLY', 'El producto puede ser recurrente cada mes', 'Mensual');
