CREATE TABLE `product_type` (
  `id` char(36) NOT NULL,
  `active` bit(1) NOT NULL,
  `code` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `channel` (
  `id` char(36) NOT NULL,
  `active` bit(1) NOT NULL,
  `code` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `vat_rate` (
  `id` char(36) NOT NULL,
  `active` bit(1) NOT NULL,
  `country_id` char(36) NOT NULL,
  `description` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `percentage` double NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `periodicity` (
  `id` char(36) NOT NULL,
  `active` bit(1) NOT NULL,
  `code` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `product` (
  `id` char(36) NOT NULL,
  `active` bit(1) NOT NULL,
  `code` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `price` double NOT NULL,
  `price_without_vat` double NOT NULL,
  `product_type_id` char(36) NOT NULL,
  `vat_rate_id` char(36) NOT NULL,
  `country_id` char(36) NOT NULL,
  `country_code` varchar(255) NOT NULL,
  `item_quota_tracking` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK73b7klsfha8f1vucojm2yrjkj` (`vat_rate_id`),
  CONSTRAINT `FK73b7klsfha8f1vucojm2yrjkj` FOREIGN KEY (`vat_rate_id`) REFERENCES `vat_rate` (`id`),
  KEY `FKlabq3c2e90ybbxk58rc48byqo` (`product_type_id`),
  CONSTRAINT `FKlabq3c2e90ybbxk58rc48byqo` FOREIGN KEY (`product_type_id`) REFERENCES `product_type` (`id`)
);

CREATE TABLE `product_product` (
  `product_id` char(36) NOT NULL,
  `related_product_id` char(36) NOT NULL,
  PRIMARY KEY (`product_id`,`related_product_id`),
  KEY `FKge780nhy5j2k3vyhw915iox73` (`related_product_id`),
  CONSTRAINT `FKge780nhy5j2k3vyhw915iox73` FOREIGN KEY (`related_product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FKnie61pbiukoqbd1v9prk1ftr7` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
);

CREATE TABLE `product_channel` (
  `product_id` char(36) NOT NULL,
  `channel_id` char(36) NOT NULL,
  PRIMARY KEY (`product_id`,`channel_id`),
  KEY `FKqtrl9i6u57ktc6m5klxempkrw` (`channel_id`),
  CONSTRAINT `FKqg0ptk7etiuixm1ie87qd6v58` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FKqtrl9i6u57ktc6m5klxempkrw` FOREIGN KEY (`channel_id`) REFERENCES `channel` (`id`)
);

CREATE TABLE `product_periodicity` (
  `product_id` char(36) NOT NULL,
  `periodicity_id` char(36) NOT NULL,
  PRIMARY KEY (`product_id`,`periodicity_id`),
  KEY `FKmea05b4mgk3nb1t8hb1fy0hpm` (`periodicity_id`),
  CONSTRAINT `FK3sfc08dguxu1nrv3e3f13rbu4` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FKmea05b4mgk3nb1t8hb1fy0hpm` FOREIGN KEY (`periodicity_id`) REFERENCES `periodicity` (`id`)
);

CREATE TABLE `item` (
  `id` char(36) NOT NULL,
  `active` bit(1) NOT NULL,
  `code` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `product_item` (
  `id` char(36) NOT NULL,
  `quantity` int(11) NOT NULL,
  `product_id` char(36) NOT NULL,
  `item_id` char(36) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKa9mjpi98ark8eovbtnnreygbb` (`product_id`),
  KEY `FKji2m4cy5kl9glilamkfmswl6w` (`item_id`),
  CONSTRAINT `FKa9mjpi98ark8eovbtnnreygbb` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FKji2m4cy5kl9glilamkfmswl6w` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`)
);
