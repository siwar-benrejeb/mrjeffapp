CREATE TABLE `customer_note` (
  `id` char(36) NOT NULL,
  `customer_id` char(36) NOT NULL,
  `user_id` char(36) NOT NULL,
  `note` varchar(1024) NOT NULL,
  `creation_date` datetime NOT NULL,
  `last_modification_date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKcustomer_id_customer_note` (`customer_id`),
  CONSTRAINT `FKcustomer_id_customer_note` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
);