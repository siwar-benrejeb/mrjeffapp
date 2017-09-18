CREATE TABLE `channel` (
  `id` char(36) NOT NULL,
  `active` bit(1) NOT NULL,
  `code` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `order_status` (
  `id` char(36) NOT NULL,
  `code` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `active` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `order_type` (
  `id` char(36) NOT NULL,
  `code` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `active` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `order` (
  `id` char(36) NOT NULL,
  `code` varchar(255) NOT NULL,
  `coupon_id` char(36) DEFAULT NULL,
  `creation_date` datetime NOT NULL,
  `country_id` char(36) NOT NULL,
  `country_code` varchar(255) NOT NULL,
  `postal_code_id` char(36) NOT NULL,
  `headquarter_id` char(36) DEFAULT NULL,
  `last_modified_date` datetime NOT NULL,
  `note` varchar(255) DEFAULT NULL,
  `order_date` datetime NOT NULL,
  `provider_id` char(36) DEFAULT NULL,
  `total_price` double NOT NULL,
  `total_price_without_discount` double NOT NULL,
  `total_price_discount` double NOT NULL,
  `payment_method_code` varchar(255) NOT NULL,
  `customer_id` char(36) NOT NULL,
  `order_status_id` char(36) NOT NULL,
  `order_type_id` char(36) NOT NULL,
  `billing_address_id` char(36) NOT NULL,
  `channel_id` char(36) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKcxpcc11kb45j9kjf2qol0pmvm` (`order_status_id`),
  KEY `FKbb6hfbshjs4n0j90o3udnke65` (`channel_id`),
  KEY `FKaxpcc11kb45j9kjf2qol0pmva` (`order_type_id`),
  CONSTRAINT `FKbb6hfbshjs4n0j90o3udnke65` FOREIGN KEY (`channel_id`) REFERENCES `channel` (`id`),
  CONSTRAINT `FKcxpcc11kb45j9kjf2qol0pmvm` FOREIGN KEY (`order_status_id`) REFERENCES `order_status` (`id`),
  CONSTRAINT `FKaxpcc11kb45j9kjf2qol0pmva` FOREIGN KEY (`order_type_id`) REFERENCES `order_type` (`id`)
);

CREATE TABLE `order_status_tracking` (
  `id` char(36) NOT NULL,
  `creation_date` datetime NOT NULL,
  `order_id` char(36) NOT NULL,
  `order_status_id` char(36) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK83vsue9ascr10weq944pu9sf0` (`order_id`),
  KEY `FKsge38mpusjt3wipeja5c2b0hh` (`order_status_id`),
  CONSTRAINT `FK83vsue9ascr10weq944pu9sf0` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`),
  CONSTRAINT `FKsge38mpusjt3wipeja5c2b0hh` FOREIGN KEY (`order_status_id`) REFERENCES `order_status` (`id`)
);

CREATE TABLE `product_order` (
  `id` char(36) NOT NULL,
  `creation_date` datetime NOT NULL,
  `product_id` char(36) NOT NULL,
  `order_id` char(36) NOT NULL,
  `quantity` integer NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK36n97v8q6uhp9ie532wgj5eln` (`order_id`),
  CONSTRAINT `FK36n97v8q6uhp9ie532wgj5eln` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`)
);

CREATE TABLE `visit` (
  `id` char(36) NOT NULL,
  `address_id` char(36) NOT NULL,
  `date` datetime NOT NULL,
  `postal_code_id` char(36) NOT NULL,
  `postal_code` varchar(255) NOT NULL,
  `city_id` char(36) NOT NULL,
  `country_id` char(36) NOT NULL,
  `visit_type_code` varchar(255) NOT NULL,
  `time_table_time_slot_id` char(36) DEFAULT NULL,
  `time_slot_start` time NOT NULL,
  `time_slot_end` time NOT NULL,
  `order_id` char(36) NOT NULL,
  `creation_date` datetime NOT NULL,
  `last_modification_date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKooopp9jy9nodwvmrno8a6i8on` (`order_id`),
  CONSTRAINT `FKooopp9jy9nodwvmrno8a6i8on` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`)
);

CREATE TABLE `product_order_item_barcode` (
  `id` char(36) NOT NULL,
  `barcode` varchar(255) NOT NULL,
  `creation_date` datetime NOT NULL,
  `product_order_id` char(36) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKa24hu3jq5btroosskoidjnew1` (`product_order_id`),
  CONSTRAINT `FKa24hu3jq5btroosskoidjnew1` FOREIGN KEY (`product_order_id`) REFERENCES `product_order` (`id`)
);

CREATE TABLE `headquarter_assignment_configuration` (
  `id` char(36) NOT NULL,
  `active` bit(1) NOT NULL,
  `postal_code_id` char(36) NOT NULL,
  `headquarter_id` char(36) NOT NULL,
  PRIMARY KEY (`id`)
);