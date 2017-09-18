ALTER TABLE address MODIFY postal_code_id char(36) NULL;
ALTER TABLE address MODIFY city_id char(36) NULL;
ALTER TABLE address MODIFY city_code varchar(255) NULL;
ALTER TABLE address MODIFY region_id char(36) NULL;
ALTER TABLE address MODIFY country_id char(36) NULL;
ALTER TABLE address MODIFY region_id char(36) NULL;
ALTER TABLE address MODIFY phone_number varchar(255) NULL;