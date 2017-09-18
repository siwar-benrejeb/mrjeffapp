CREATE TABLE `customer_type` (
  `id` char(36) NOT NULL,
  `code` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `active` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `customer` (
  `id` char(36) NOT NULL,
  `creation_date` datetime NOT NULL,
  `email` varchar(255) NOT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `last_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `reset_password` bit(1) NOT NULL,
  `name` varchar(255),
  `customer_type_id` char(36) NOT NULL,
  KEY `FKaxpcc11kb45j9kjf2qol0pmva` (`customer_type_id`),
  CONSTRAINT `FKaxpcc11kb45j9kjf2qol0pmva` FOREIGN KEY (`customer_type_id`) REFERENCES `customer_type` (`id`),
  PRIMARY KEY (`id`)
);

CREATE TABLE `customer_business_details` (
  `id` char(36) NOT NULL,
  `business_id` varchar(255) NOT NULL,
  `business_name` varchar(255) NOT NULL,
  `customer_id` char(36) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK93c3js0e22ll1xlu21nvrhhbb` (`customer_id`),
  CONSTRAINT `FK93c3js0e22ll1xlu21nvrhhbb` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
);

CREATE TABLE `address` (
  `id` char(36) NOT NULL,
  `customer_id` char(36) NOT NULL,
  `active` bit(1) NOT NULL,
  `address` varchar(255) NOT NULL,
  `address_number` varchar(255) NOT NULL,
  `address_details` varchar(255),
  `city_id` char(36) NOT NULL,
  `city_code` varchar(255) NOT NULL,
  `city_name` varchar(255) NOT NULL,
  `country_id` char(36) NOT NULL,
  `country_code` varchar(255) NOT NULL,
  `country_name` varchar(255) NOT NULL,
  `postal_code_id` char(36) NOT NULL,
  `creation_date` datetime NOT NULL,
  `alias` varchar(255) NOT NULL,
  `phone_number` varchar(255) NOT NULL,
  `postal_code` varchar(255) NOT NULL,
  `region_id` char(36) NOT NULL,
  `default_pickup` bit(1) NOT NULL,
  `default_delivery` bit(1) NOT NULL,
  `default_billing` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK93c3js0e22ll1xlu21nvrhqgg` (`customer_id`),
  CONSTRAINT `FK93c3js0e22ll1xlu21nvrhqgg` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
);