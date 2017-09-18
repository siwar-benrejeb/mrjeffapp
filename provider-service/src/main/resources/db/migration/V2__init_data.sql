INSERT INTO `incident_type` (`id`, `active`, `code`, `description`, `name`)
VALUES
	('d60bb999-f0cb-49e3-ba03-68b6b70ad453', 1, 'ITEM_DETERIORATED', NULL, 'Prenda deteriorada'),
	('dabeed90-5435-4734-ac9d-d0ade090b077', 1, 'ITEM_STAIN', NULL, 'Manchas'),
	('773f8c3d-8e9e-4c74-a223-be5d3a58ca54', 1, 'ZIPPER_BROKEN', NULL, 'Botones - cremalleras rotos'),
	('48c53c4e-4f87-4edd-97c0-ce3b6a5dd61f', 1, 'ZIPPER_DETERIORATED', NULL, 'Botones - cremalleras deteriorados'),
	('161e73e9-2068-46be-9d91-b66788db0f5f', 1, 'ITEM_EXCESS', NULL, 'Falta de prendas. Hay más prendas que en el pedido'),
	('3a4c83d5-8006-4ccd-8914-ff2a2b1d4821', 1, 'ITEMS_DETERIORATED', NULL, 'Prendas Deterioradas'),
	('9ad2939b-0af9-4bdc-86eb-63c4cd756910', 1, 'ITEM_FRICTION', NULL, 'Prenda con algún rozamiento');

INSERT INTO `authorization_type` (`id`, `active`, `code`, `description`, `name`)
VALUES
	('a215be39-8b36-4023-8171-c0f30651ba81', 1, 'ITEM_MANUAL_CLEANING', NULL, 'Prenda lavar a mano'),
	('dae6f744-d6fe-45e5-91d3-c860bc7a34b3', 1, 'ITEM_LEATHER_ORNAMENT', NULL, 'Adornos de cuero'),
	('4d7762d0-1a5f-4151-bc59-dcc87d904a11', 1, 'ITEM_FUR', NULL, 'Prenda de piel o ante'),
	('57d15739-6814-40bd-bb42-5158a8f0e9c3', 1, 'ITEM_LEATHER', NULL, 'Prenda de cuero'),
	('dcc9db44-dd1d-4ed4-9d2d-a806aa5947a7', 1, 'ITEM_DAMAGE', NULL, 'Pueda dañar la prenda'),
	('d7d11475-18f6-4e47-8a83-0ce03189929d', 1, 'ITEM_ZIPPER_DAMAGE', NULL, 'Pueden dañarse botones o cremalleras'),
	('c1ecfb47-7403-44b7-80df-31dea898bb79', 1, 'ITEM_ADD', NULL, 'Añadir prenda');

INSERT INTO `authorization_state` (`id`, `active`, `code`, `description`, `name`)
VALUES
	('7af3d4ab-c69b-49a6-b9fd-4572caf375d1', 1, 'WAITING', NULL, 'En espera'),
	('c3536752-a8b1-4ef4-be4b-d2175bbc3cb6', 1, 'AUTHORIZED', NULL, 'Autorizada'),
	('073aef7e-5335-4736-84c8-f7036b21d162', 1, 'NO_AUTHORIZED', NULL, 'No autorizada');

INSERT INTO `work_order_state` (`id`, `active`, `code`, `description`, `name`)
VALUES
	('df0d9a0e-345f-4223-b2f2-b4e0cd9f3ee9', 1, 'INCOMING', NULL, 'Por-recibir'),
	('64d2e9e6-d4bd-409a-9d2d-4df2fd08971b', 1, 'PICKEDUP', NULL, 'Entregado-Jeff'),
	('06eaa960-921c-4fc6-8077-f8da2cab77a7', 1, 'MARKED', NULL, 'Marcado'),
	('a5b92188-7c03-4493-85cd-71b9f83ce1e5', 0, 'SEALED', NULL, 'Embolsado'),
	('e47ef739-61c4-4f63-b70d-4dd6dd346d4b', 1, 'FINISHED', NULL, 'Finalizado'),
	('8b637a64-299d-4beb-8a23-07ca60fca1ba', 1, 'DELIVERED', NULL, 'Entregado');
