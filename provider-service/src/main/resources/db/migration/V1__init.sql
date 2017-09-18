CREATE TABLE `authorization_state` (
  `id` char(36) NOT NULL,
  `active` bit(1) NOT NULL,
  `code` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `authorization_type` (
  `id` char(36) NOT NULL,
  `active` bit(1) NOT NULL,
  `code` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `incident_type` (
  `id` char(36) NOT NULL,
  `active` bit(1) NOT NULL,
  `code` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `work_order_state` (
  `id` char(36) NOT NULL,
  `active` bit(1) NOT NULL,
  `code` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `provider` (
  `id` char(36) NOT NULL,
  `active` bit(1) NOT NULL,
  `code` varchar(255) NOT NULL,
  `creation_date` datetime NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `country_id` char(36) NOT NULL,
  `franchise` bit(1) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `user` (
  `id` char(36) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `active` bit(1) NOT NULL,
  `provider_id` char(36) NOT NULL,
  `creation_date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKn6etho5ib8edbe8siyh915vcn` (`provider_id`),
  CONSTRAINT `FKn6etho5ib8edbe8siyh915vcn` FOREIGN KEY (`provider_id`) REFERENCES `provider` (`id`)
);

CREATE TABLE `role` (
  `id` char(36) NOT NULL,
  `code` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `user_role` (
  `user_id` char(36) NOT NULL,
  `role_id` char(36) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
  CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
);

CREATE TABLE `work_order` (
  `id` char(36) NOT NULL,
  `customer_id` char(36) NOT NULL,
  `creation_date` datetime NOT NULL,
  `order_id` char(36) NOT NULL,
  `order_code` varchar(255) NOT NULL,
  `pickup_date` datetime NOT NULL,
  `delivery_date` datetime NOT NULL,
  `provider_id` char(36) NOT NULL,
  `work_order_state_id` char(36) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKq371niwkrty8dhf76bq4bmfls` (`provider_id`),
  KEY `FKgokagtcn6hntnote2dojscjnh` (`work_order_state_id`),
  CONSTRAINT `FKgokagtcn6hntnote2dojscjnh` FOREIGN KEY (`work_order_state_id`) REFERENCES `work_order_state` (`id`),
  CONSTRAINT `FKq371niwkrty8dhf76bq4bmfls` FOREIGN KEY (`provider_id`) REFERENCES `provider` (`id`)
);

CREATE TABLE `work_order_note` (
  `id` char(36) NOT NULL,
  `creation_date` datetime NOT NULL,
  `text` varchar(255) NOT NULL,
  `user_id` char(36) NOT NULL,
  `work_order_id` char(36) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK322yxfj468henpqlsaruredkx` (`user_id`),
  KEY `FK2tkx1aanp65re7vrco1hvtoij` (`work_order_id`),
  CONSTRAINT `FK2tkx1aanp65re7vrco1hvtoij` FOREIGN KEY (`work_order_id`) REFERENCES `work_order` (`id`),
  CONSTRAINT `FK322yxfj468henpqlsaruredkx` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);

create table notification (id char(36) not null, code varchar(255) not null, creation_date datetime not null, description varchar(255) not null, provider_id char(36) not null, read_date datetime, state varchar(255) not null, user_id char(36) not null, product_id char(36), primary key (id));

CREATE TABLE `product` (
  `id` char(36) NOT NULL,
  `product_id` char(36) NOT NULL,
  `user_id` char(36) NOT NULL,
  `work_order_id` char(36) NOT NULL,
  `quantity` integer NOT NULL,
  `creation_date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK979liw4xk18ncpl87u4tygx2u` (`user_id`),
  KEY `FKayymucmf20ts44khjhegs779n` (`work_order_id`),
  CONSTRAINT `FK979liw4xk18ncpl87u4tygx2u` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKayymucmf20ts44khjhegs779n` FOREIGN KEY (`work_order_id`) REFERENCES `work_order` (`id`)
);

CREATE TABLE `authorization` (
  `id` char(36) NOT NULL,
  `creation_date` datetime NOT NULL,
  `authorization_state_id` char(36) NOT NULL,
  `authorization_type_id` char(36) NOT NULL,
  `product_id` char(36) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKi2nyn77y1njopa5elrth6a45m` (`authorization_state_id`),
  KEY `FKblwp04yrm7wld5lp9kgs1ttj7` (`authorization_type_id`),
  KEY `FKproduct_id` (`product_id`),
  CONSTRAINT `FKblwp04yrm7wld5lp9kgs1ttj7` FOREIGN KEY (`authorization_type_id`) REFERENCES `authorization_type` (`id`),
  CONSTRAINT `FKi2nyn77y1njopa5elrth6a45m` FOREIGN KEY (`authorization_state_id`) REFERENCES `authorization_state` (`id`),
  CONSTRAINT `FKproduct_id` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
);

CREATE TABLE `incident` (
  `id` char(36) NOT NULL,
  `cause` varchar(255) NOT NULL,
  `incident_type_id` char(36) NOT NULL,
  `user_id` char(36) NOT NULL,
  `work_order_id` char(36) NOT NULL,
  `creation_date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKj8ceq1dm9co96dtaa5fbr5kqh` (`incident_type_id`),
  KEY `FK8bqewpr8w6nc8leoue11rmuew` (`user_id`),
  KEY `FKquv8kerem3cfs9j28kt8tgiu0` (`work_order_id`),
  CONSTRAINT `FK8bqewpr8w6nc8leoue11rmuew` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKj8ceq1dm9co96dtaa5fbr5kqh` FOREIGN KEY (`incident_type_id`) REFERENCES `incident_type` (`id`),
  CONSTRAINT `FKquv8kerem3cfs9j28kt8tgiu0` FOREIGN KEY (`work_order_id`) REFERENCES `work_order` (`id`)
);

CREATE TABLE `item_count` (
  `id` char(36) NOT NULL,
  `item_id` char(36) NOT NULL,
  `product_id` char(36) NOT NULL,
  `quantity` double NOT NULL,
  `user_id` char(36) NOT NULL,
  `work_order_id` char(36) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfiwyi8oeal7hjom9i2nxrcoo` (`user_id`),
  KEY `FKaa6o8cdi8kb54w119qminbpi4` (`work_order_id`),
  CONSTRAINT `FKaa6o8cdi8kb54w119qminbpi4` FOREIGN KEY (`work_order_id`) REFERENCES `work_order` (`id`),
  CONSTRAINT `FKfiwyi8oeal7hjom9i2nxrcoo` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);

CREATE TABLE `provider_assignment_configuration` (
  `id` char(36) NOT NULL,
  `active` bit(1) NOT NULL,
  `postal_code_id` char(36) NOT NULL,
  `provider_id` char(36) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKen15gbqjn2isvafkkiednpcx4` (`provider_id`),
  CONSTRAINT `FKen15gbqjn2isvafkkiednpcx4` FOREIGN KEY (`provider_id`) REFERENCES `provider` (`id`)
);

alter table notification add constraint FKbv8kwwu9ebnqglt5lj6mf8gsm foreign key (product_id) references product (id);