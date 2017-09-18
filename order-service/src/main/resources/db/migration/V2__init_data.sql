INSERT INTO `order_type` (`id`, `code`, `description`, `name`, `active`)
VALUES
	('01508489-293c-4a76-bdeb-f042636549e5', 'B2C', 'Pedido B2C', 'B2C', 1),
	('80b7691e-9baa-4316-bf06-da997d73a927', 'B2B', 'Pedido B2B', 'B2B', 1),
    ('89def8c6-aa6a-440c-898a-bd108842e8f5', 'INCIDENT', 'Pedido de incidencia', 'INCIDENT', 1);


INSERT INTO `order_status` (`id`, `code`, `description`, `name`, `active`)
VALUES
	('f3175aa3-17fb-4a90-879c-ee0ca1987aa1', 'CREATED', 'Nuevo pedido', 'Pedido', 1),
	('ca96832a-b7d9-4143-b67e-a383ec920a15', 'PICKUP', 'Pedido en recogida', 'Recogida', 1),
	('c39a9cbe-91b2-4de4-b5d8-873d8a20016d', 'WASHING', 'Lavando pedido', 'Lavando', 1),
	('181e12e5-ca22-4d60-b2af-74b2557fa445', 'DELIVERY', 'Pedido en entrega', 'Entrega', 1),
	('62454974-e19b-421b-a733-903134836636', 'COMPLETED', 'Pedido entregado', 'Entregado', 1),
	('bbeddbcb-d444-4e37-9186-9fc60d9cec41', 'CANCELLED', 'Pedido cancelado', 'Cancelado', 1);

INSERT INTO `channel` (`id`, `active`, `code`, `description`, `name`)
VALUES
	('282ecfcd-d878-41be-9177-3bd67083f3fb', 1, 'ECOMMERCE', 'Ventas desde la Ecommerce app', 'Ecommerce app'),
	('7e39a83c-bea4-4c0d-8fac-39b838d59092', 1, 'SAAS', 'Ventas desde el SAAS', 'SAAS app'),
	('2aee1eaf-90cb-4dff-8fe6-2c43431601a8', 1, 'ANDROID', 'Ventas desde la app Android', 'Android app'),
	('1f4e4cc1-f945-46cb-b961-fefa466f4ea3', 1, 'IOS', 'Ventas desde la app Android', 'IOS app'),
	('279ed0eb-71e3-478d-a356-d7f308e138df', 1, 'PHONE', 'Ventas telefonicas', 'Phone call'),
	('d5d87498-da68-4edd-9af3-f086bec42ac1', 1, 'CHATBOT', 'Ventas desde el ChatBot', 'Chatbot app'),
	('2dd7ee18-3c41-4b22-9068-4a4cf366b3a7', 1, 'BACKOFFICE', 'Ventas desde el BackOffice', 'BackOffice app');
