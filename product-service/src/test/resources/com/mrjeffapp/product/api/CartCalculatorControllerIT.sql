insert into vat_rate (id, active, country_id, description, name, percentage) values ('1', 1, 1, 'IVA general aplicado en España', 'IVA general', 21);
insert into product_type (id, active, code, description, name) values ('1', 1, 'LAUNDRY', 'LAUNDRY_DESC', 'LAUNDRY_NAME');
insert into product (id, code, name, description, active, product_type_id, price, price_without_vat, country_id, vat_rate_id, item_quota_tracking, country_code) values ('1', 'PRODUCT', 'PRODUCT1', 'DESC_PRODUCT1', true, '1', 1, 1, '1', '1', 1, 'ES');
insert into item (id, code, name, description, active) values ('1', 'ITEM', 'ITEM1', 'DESC_ITEM1', true);
insert into product_item (id, quantity, product_id, item_id) values ('1', 1, 1, 1);
insert into product_product (product_id, related_product_id) values ('1', 1);
