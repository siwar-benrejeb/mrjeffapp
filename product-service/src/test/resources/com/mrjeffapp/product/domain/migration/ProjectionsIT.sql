insert into vat_rate (id, active, country_id, description, name, percentage) values ('1', 1, 1, 'IVA general aplicado en España', 'IVA general', 21);
insert into product_type (id, active, code, description, name) values ('1', 1, 'LAUNDRY', 'LAUNDRY_DESC', 'LAUNDRY_NAME');

INSERT INTO `product` (`id`, `active`, `code`, `description`, `name`, `price`, `price_without_vat`, `product_type_id`, `vat_rate_id`, `country_id`, `country_code`, `item_quota_tracking`)
VALUES
	('1', 1, 'bolsa-8-kg', 'Máximo 8 Kg por bolsa. Lavado a 30º, secado y doblado.', 'Bolsa 8 kg', 15, 15, '1', '1', '1', 'ES', 0);


insert into item (id, code, name, description, active) values ('1', 'ITEM', 'ITEM1', 'DESC_ITEM1', true);
insert into product_item (id, quantity, product_id, item_id) values ('1', 1, 1, 1);
insert into product_product (product_id, related_product_id) values ('1', 1);
