/*
SQLyog Community v13.3.1 (64 bit)
MySQL - 8.0.46 : Database - ecommerce_services
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`ecommerce_services` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `ecommerce_services`;

/*Table structure for table `address` */

DROP TABLE IF EXISTS `address`;

CREATE TABLE `address` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(30) NOT NULL,
  `door_no` varchar(10) NOT NULL,
  `street` varchar(100) DEFAULT NULL,
  `place` varchar(100) NOT NULL,
  `city` varchar(50) NOT NULL,
  `state` varchar(50) NOT NULL,
  `country` varchar(30) NOT NULL,
  `pincode` int NOT NULL,
  `user_id` int NOT NULL,
  `namespace_id` int NOT NULL,
  `active_flag` tinyint NOT NULL DEFAULT '1',
  `updated_by` int NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `fk_addresses_namespace_id` (`namespace_id`),
  KEY `fk_addresses_updated_by` (`updated_by`),
  KEY `idx_addresses_user_namespace_active` (`user_id`,`namespace_id`,`active_flag`),
  CONSTRAINT `fk_addresses_namespace_id` FOREIGN KEY (`namespace_id`) REFERENCES `namespace` (`id`),
  CONSTRAINT `fk_addresses_updated_by` FOREIGN KEY (`updated_by`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_addresses_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `address` */

insert  into `address`(`id`,`code`,`door_no`,`street`,`place`,`city`,`state`,`country`,`pincode`,`user_id`,`namespace_id`,`active_flag`,`updated_by`,`updated_at`) values 
(6,'ADDR11407','7','malligai puram main road','irudayapur, kilapudur','tiruchirappalli','tamilnadu','india',620001,24,2,1,24,'2026-06-25 12:36:30'),
(7,'ADDR16199','88','bazar street','thuraiyur','tiruchirappalli','tamilnadu','india',620011,26,3,1,1,'2026-06-17 20:36:21'),
(8,'ADDR56522','10','bazar street','t nagar, chennai','chennai','tamilnadu','india',620028,34,3,1,26,'2026-06-18 12:09:12'),
(9,'ADDR25059','10','bazar street','t nagar, chennai','chennai','tamilnadu','india',620028,34,3,1,24,'2026-06-23 17:37:19');

/*Table structure for table `brands` */

DROP TABLE IF EXISTS `brands`;

CREATE TABLE `brands` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(30) NOT NULL,
  `name` varchar(50) NOT NULL,
  `namespace_id` int NOT NULL,
  `active_flag` tinyint NOT NULL DEFAULT '1',
  `updated_by` int NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `fk_brands_updated_by` (`updated_by`),
  KEY `idx_brands_namespace_active` (`namespace_id`,`active_flag`),
  CONSTRAINT `fk_brands_namespace_id` FOREIGN KEY (`namespace_id`) REFERENCES `namespace` (`id`),
  CONSTRAINT `fk_brands_updated_by` FOREIGN KEY (`updated_by`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `brands` */

insert  into `brands`(`id`,`code`,`name`,`namespace_id`,`active_flag`,`updated_by`,`updated_at`) values 
(1,'BRAND22697','samsung',2,1,1,'2026-06-11 13:29:04'),
(2,'BRAND68503','sony',2,1,1,'2026-06-15 21:07:04'),
(3,'BRAND99516','mi',2,1,24,'2026-06-25 12:38:14');

/*Table structure for table `cart` */

DROP TABLE IF EXISTS `cart`;

CREATE TABLE `cart` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(30) NOT NULL,
  `user_id` int NOT NULL,
  `namespace_id` int NOT NULL,
  `active_flag` tinyint NOT NULL DEFAULT '1',
  `updated_by` int NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  UNIQUE KEY `user_id` (`user_id`),
  KEY `fk_carts_namespace_id` (`namespace_id`),
  KEY `fk_carts_updated_by` (`updated_by`),
  KEY `idx_carts_user_namespace_active` (`user_id`,`namespace_id`,`active_flag`),
  CONSTRAINT `fk_carts_namespace_id` FOREIGN KEY (`namespace_id`) REFERENCES `namespace` (`id`),
  CONSTRAINT `fk_carts_updated_by` FOREIGN KEY (`updated_by`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_carts_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `cart` */

insert  into `cart`(`id`,`code`,`user_id`,`namespace_id`,`active_flag`,`updated_by`,`updated_at`) values 
(3,'CRT62904',24,2,1,24,'2026-06-15 20:39:57'),
(4,'CRT57796',25,3,1,1,'2026-06-15 13:49:39'),
(5,'CRT67619',26,3,1,1,'2026-06-15 14:12:42'),
(6,'CRT99877',27,2,1,24,'2026-06-25 12:20:59'),
(7,'CRT78031',28,2,1,1,'2026-06-15 19:48:26'),
(8,'CRT39034',29,2,1,24,'2026-06-15 20:26:36'),
(9,'CRT90094',30,2,1,24,'2026-06-15 20:41:15'),
(10,'CRT64467',31,2,1,24,'2026-06-16 13:43:48'),
(11,'CRT21743',32,2,1,1,'2026-06-17 20:00:00'),
(12,'CRT94728',33,2,1,1,'2026-06-17 20:01:08'),
(13,'CRT25218',34,3,1,26,'2026-06-18 12:05:15'),
(14,'CRT52383',38,2,1,24,'2026-06-23 20:02:02');

/*Table structure for table `cart_items` */

DROP TABLE IF EXISTS `cart_items`;

CREATE TABLE `cart_items` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(30) NOT NULL,
  `cart_id` int NOT NULL,
  `product_id` int NOT NULL,
  `quantity` int NOT NULL,
  `namespace_id` int NOT NULL,
  `active_flag` tinyint NOT NULL DEFAULT '1',
  `updated_by` int NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `fk_cart_items_namespace_id` (`namespace_id`),
  KEY `fk_cart_items_updated_by` (`updated_by`),
  KEY `idx_cart_items_cart_product` (`cart_id`,`product_id`),
  KEY `idx_cart_items_product_active` (`product_id`,`active_flag`),
  CONSTRAINT `fk_cart_items_cart_id` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`id`),
  CONSTRAINT `fk_cart_items_namespace_id` FOREIGN KEY (`namespace_id`) REFERENCES `namespace` (`id`),
  CONSTRAINT `fk_cart_items_product_id` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `fk_cart_items_updated_by` FOREIGN KEY (`updated_by`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `cart_items` */

insert  into `cart_items`(`id`,`code`,`cart_id`,`product_id`,`quantity`,`namespace_id`,`active_flag`,`updated_by`,`updated_at`) values 
(1,'CITEM32216',14,4,1,2,1,24,'2026-06-22 22:17:52'),
(2,'CITEM56579',14,5,1,2,1,24,'2026-06-22 22:18:32'),
(3,'CITEM74078',14,5,1,2,1,24,'2026-06-23 20:18:17'),
(4,'CITEM80565',14,5,1,2,1,27,'2026-06-24 14:59:45'),
(5,'CITEM92549',3,5,1,2,1,24,'2026-06-25 12:41:32'),
(6,'CITEM44296',3,5,1,2,1,24,'2026-06-25 12:42:22');

/*Table structure for table `categories` */

DROP TABLE IF EXISTS `categories`;

CREATE TABLE `categories` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(30) NOT NULL,
  `name` varchar(30) NOT NULL,
  `namespace_id` int NOT NULL,
  `active_flag` tinyint NOT NULL DEFAULT '1',
  `updated_by` int NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `fk_categories_updated_by` (`updated_by`),
  KEY `idx_categories_namespace_active` (`namespace_id`,`active_flag`),
  CONSTRAINT `fk_categories_namespace_id` FOREIGN KEY (`namespace_id`) REFERENCES `namespace` (`id`),
  CONSTRAINT `fk_categories_updated_by` FOREIGN KEY (`updated_by`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `categories` */

insert  into `categories`(`id`,`code`,`name`,`namespace_id`,`active_flag`,`updated_by`,`updated_at`) values 
(1,'CAT78431','electronics',2,1,1,'2026-06-11 13:09:40'),
(2,'CAT67744','food',2,1,24,'2026-06-25 12:45:13'),
(5,'CAT82344','food',3,1,25,'2026-06-24 15:06:51');

/*Table structure for table `namespace` */

DROP TABLE IF EXISTS `namespace`;

CREATE TABLE `namespace` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(30) NOT NULL,
  `name` varchar(100) NOT NULL,
  `active_flag` tinyint NOT NULL DEFAULT '1',
  `updated_by` int NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `idx_namespace_name_active` (`name`,`active_flag`),
  KEY `fk_namespace_updated_by` (`updated_by`),
  CONSTRAINT `fk_namespace_updated_by` FOREIGN KEY (`updated_by`) REFERENCES `namespace` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `namespace` */

insert  into `namespace`(`id`,`code`,`name`,`active_flag`,`updated_by`,`updated_at`) values 
(1,'SUPER000','super admin namespace',1,1,'2026-06-10 12:52:31'),
(2,'AMZN001','amazon',1,1,'2026-06-10 16:33:57'),
(3,'FLPKT002','flipkart',1,1,'2026-06-10 16:37:39'),
(4,'NMSPC68858','meesho',2,1,'2026-06-11 13:58:38'),
(5,'NS35601','ajio',1,1,'2026-06-17 19:59:34'),
(23,'NS18438','snapdeal',1,1,'2026-06-17 20:00:33'),
(24,'NS93913','big basket',1,1,'2026-06-26 20:39:50'),
(25,'NS70673','shopify',1,24,'2026-06-20 22:32:05');

/*Table structure for table `order_items` */

DROP TABLE IF EXISTS `order_items`;

CREATE TABLE `order_items` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(30) NOT NULL,
  `order_id` int NOT NULL,
  `product_id` int NOT NULL,
  `quantity` int NOT NULL,
  `price` decimal(12,2) NOT NULL,
  `namespace_id` int NOT NULL,
  `active_flag` tinyint NOT NULL DEFAULT '1',
  `updated_by` int NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `fk_order_items_namespace_id` (`namespace_id`),
  KEY `fk_order_items_updated_by` (`updated_by`),
  KEY `idx_order_items_order_product` (`order_id`,`product_id`),
  KEY `idx_order_items_product_active` (`product_id`,`active_flag`),
  CONSTRAINT `fk_order_items_namespace_id` FOREIGN KEY (`namespace_id`) REFERENCES `namespace` (`id`),
  CONSTRAINT `fk_order_items_order_id` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `fk_order_items_product_id` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `fk_order_items_updated_by` FOREIGN KEY (`updated_by`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `order_items` */

insert  into `order_items`(`id`,`code`,`order_id`,`product_id`,`quantity`,`price`,`namespace_id`,`active_flag`,`updated_by`,`updated_at`) values 
(1,'OITEM86531',16,4,2,40000.00,2,1,24,'2026-06-20 16:43:11'),
(2,'OITEM47021',17,4,2,40000.00,2,1,24,'2026-06-20 16:51:01'),
(3,'OITEM98082',17,5,1,46000.00,2,1,24,'2026-06-20 16:51:01'),
(4,'OITEM67163',18,4,2,40000.00,2,1,24,'2026-06-20 17:50:46'),
(5,'OITEM62543',19,4,2,40000.00,2,1,24,'2026-06-20 18:11:22'),
(6,'OITEM96553',19,5,1,46000.00,2,1,24,'2026-06-20 18:11:23'),
(7,'OITEM23193',20,4,7,40000.00,2,1,24,'2026-06-20 18:40:00'),
(8,'OITEM41815',20,5,1,46000.00,2,1,24,'2026-06-20 18:40:00'),
(9,'OITEM91251',21,5,1,46000.00,2,1,24,'2026-06-20 19:54:39'),
(10,'OITEM36814',22,5,1,46000.00,2,1,25,'2026-06-22 14:08:37'),
(11,'OITEM66323',23,5,1,46000.00,2,1,24,'2026-06-22 14:22:31'),
(12,'OITEM33078',23,5,1,46000.00,2,1,24,'2026-06-22 14:24:30'),
(13,'OITEM95659',24,5,1,46000.00,2,1,24,'2026-06-23 17:07:00'),
(14,'OITEM82231',25,5,1,48000.00,2,1,25,'2026-06-24 15:47:40'),
(15,'OITEM83077',26,5,1,48000.00,2,1,25,'2026-06-24 16:04:42'),
(16,'OITEM62114',27,5,1,48000.00,2,1,27,'2026-06-24 16:32:06'),
(17,'OITEM17988',28,5,1,48000.00,2,1,27,'2026-06-25 12:58:39'),
(18,'OITEM54823',29,5,1,48000.00,2,1,27,'2026-06-25 15:48:59'),
(19,'OITEM38330',29,5,1,48000.00,2,1,27,'2026-06-25 15:51:43'),
(20,'OITEM48992',29,5,1,48000.00,2,1,27,'2026-06-25 15:54:58'),
(21,'OITEM32947',30,5,1,48000.00,2,1,27,'2026-06-25 16:19:23'),
(22,'OITEM51637',31,5,1,48000.00,2,1,27,'2026-06-25 20:20:50'),
(23,'OITEM14057',32,5,1,48000.00,2,1,27,'2026-06-26 10:49:26'),
(24,'OITEM26455',33,5,1,48000.00,2,1,27,'2026-06-26 11:06:25'),
(25,'OITEM52353',34,5,1,48000.00,2,1,27,'2026-06-26 19:58:31'),
(26,'OITEM23778',35,5,1,48000.00,2,1,27,'2026-06-26 20:11:49'),
(27,'OITEM45931',36,5,1,48000.00,2,1,27,'2026-06-26 20:17:18'),
(28,'OITEM45340',37,5,1,48000.00,2,1,27,'2026-06-26 20:34:49');

/*Table structure for table `orders` */

DROP TABLE IF EXISTS `orders`;

CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(30) NOT NULL,
  `user_id` int NOT NULL,
  `order_status` tinyint NOT NULL,
  `total_amount` decimal(12,2) NOT NULL,
  `order_date` datetime NOT NULL,
  `namespace_id` int NOT NULL,
  `active_flag` tinyint NOT NULL DEFAULT '1',
  `updated_by` int NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `fk_orders_namespace_id` (`namespace_id`),
  KEY `fk_orders_updated_by` (`updated_by`),
  KEY `idx_orders_user_status_active` (`user_id`,`order_status`,`active_flag`),
  KEY `idx_orders_date_status` (`order_date`,`order_status`),
  KEY `idx_orders_user_date` (`user_id`,`order_date`),
  KEY `idx_orders_namespace_active` (`namespace_id`,`active_flag`),
  CONSTRAINT `fk_orders_namespace_id` FOREIGN KEY (`namespace_id`) REFERENCES `namespace` (`id`),
  CONSTRAINT `fk_orders_updated_by` FOREIGN KEY (`updated_by`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_orders_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `orders` */

insert  into `orders`(`id`,`code`,`user_id`,`order_status`,`total_amount`,`order_date`,`namespace_id`,`active_flag`,`updated_by`,`updated_at`) values 
(1,'ORDR24010',24,2,40000.00,'2026-06-20 14:00:00',2,1,24,'2026-06-20 15:45:01'),
(2,'ORDR25267',24,2,40000.00,'2026-06-20 14:00:00',2,1,24,'2026-06-20 15:48:07'),
(3,'ORDR61620',24,2,40000.00,'2026-06-20 14:00:00',2,1,24,'2026-06-20 15:52:50'),
(4,'ORDR26392',24,2,40000.00,'2026-06-20 14:00:00',2,1,24,'2026-06-20 15:55:31'),
(5,'ORDR76932',24,2,40000.00,'2026-06-20 14:00:00',2,1,24,'2026-06-20 15:57:15'),
(6,'ORDR71307',24,2,40000.00,'2026-06-20 14:00:00',2,1,24,'2026-06-20 15:59:26'),
(7,'ORDR50149',24,2,80000.00,'2026-06-20 14:00:00',2,1,24,'2026-06-20 16:11:10'),
(8,'ORDR46989',24,2,80000.00,'2026-06-20 14:00:00',2,1,24,'2026-06-20 16:14:40'),
(9,'ORDR50365',24,2,80000.00,'2026-06-20 14:00:00',2,1,24,'2026-06-20 16:21:59'),
(10,'ORDR94623',24,2,80000.00,'2026-06-20 14:00:00',2,1,24,'2026-06-20 16:25:34'),
(11,'ORDR53134',24,2,80000.00,'2026-06-20 14:00:00',2,1,24,'2026-06-20 16:26:25'),
(12,'ORDR32650',24,2,80000.00,'2026-06-20 14:00:00',2,1,24,'2026-06-20 16:29:09'),
(13,'ORDR23743',24,2,80000.00,'2026-06-20 14:00:00',2,1,24,'2026-06-20 16:31:32'),
(14,'ORDR42401',24,2,80000.00,'2026-06-20 14:00:00',2,1,24,'2026-06-20 16:35:45'),
(15,'ORDR32074',24,2,80000.00,'2026-06-20 14:00:00',2,1,24,'2026-06-20 16:38:25'),
(16,'ORDR67542',24,2,80000.00,'2026-06-20 14:00:00',2,1,24,'2026-06-20 16:43:11'),
(17,'ORDR91290',24,2,126000.00,'2026-06-20 14:00:00',2,1,24,'2026-06-20 16:51:01'),
(18,'ORDR35486',24,2,126000.00,'2026-06-20 14:00:00',2,1,24,'2026-06-20 17:50:43'),
(19,'ORDR23129',24,2,126000.00,'2026-06-20 14:00:00',2,1,24,'2026-06-20 18:11:22'),
(20,'ORDR82783',24,2,326000.00,'2026-06-20 14:00:00',2,1,24,'2026-06-20 18:40:00'),
(21,'ORDR15756',24,2,46000.00,'2026-06-20 14:00:00',2,1,24,'2026-06-20 19:54:39'),
(22,'ORDR15633',24,2,46000.00,'2026-06-20 14:00:00',2,1,25,'2026-06-22 14:08:37'),
(23,'ORDR44052',24,6,46000.00,'2026-06-20 14:00:00',2,1,24,'2026-06-22 14:24:30'),
(24,'ORDR90918',24,2,46000.00,'2026-06-20 14:00:00',2,1,24,'2026-06-23 17:07:00'),
(25,'ORDR41631',24,2,48000.00,'2026-06-20 14:00:00',2,1,25,'2026-06-24 15:47:40'),
(26,'ORDR79121',24,2,48000.00,'2026-06-20 14:00:00',2,1,25,'2026-06-24 16:04:42'),
(27,'ORDR22402',27,2,48000.00,'2026-06-20 14:00:00',2,1,27,'2026-06-24 16:31:55'),
(28,'ORDR38838',27,2,48000.00,'2026-06-20 14:00:00',2,1,27,'2026-06-25 12:58:38'),
(29,'ORDR71384',27,5,48000.00,'2026-06-20 14:00:00',2,1,27,'2026-06-25 16:04:44'),
(30,'ORDR76017',27,6,48000.00,'2026-06-20 14:00:00',2,1,27,'2026-06-25 20:18:31'),
(31,'ORDR20299',27,6,48000.00,'2026-06-20 14:00:00',2,1,27,'2026-06-25 20:20:50'),
(32,'ORDR66239',27,2,48000.00,'2026-06-26 10:00:00',2,1,27,'2026-06-26 10:49:26'),
(33,'ORDR84379',27,2,48000.00,'2026-06-26 10:00:00',2,1,27,'2026-06-26 11:06:25'),
(34,'ORDR78495',27,2,48000.00,'2026-06-26 10:00:00',2,1,27,'2026-06-26 19:58:30'),
(35,'ORDR13027',27,2,48000.00,'2026-06-26 10:00:00',2,1,27,'2026-06-26 20:11:47'),
(36,'ORDR32258',27,2,48000.00,'2026-06-26 10:00:00',2,1,27,'2026-06-26 20:17:18'),
(37,'ORDR79476',27,2,48000.00,'2026-06-26 10:00:00',2,1,27,'2026-06-26 20:34:48');

/*Table structure for table `payments` */

DROP TABLE IF EXISTS `payments`;

CREATE TABLE `payments` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(30) NOT NULL,
  `order_id` int NOT NULL,
  `payment_mode` tinyint NOT NULL,
  `total_amount_to_pay` decimal(12,2) NOT NULL,
  `paid_amount` decimal(12,2) NOT NULL,
  `balance_amount` decimal(12,2) NOT NULL,
  `billing_status` tinyint NOT NULL,
  `transaction_id` varchar(100) NOT NULL,
  `remarks` varchar(150) DEFAULT NULL,
  `namespace_id` int NOT NULL,
  `active_flag` tinyint NOT NULL DEFAULT '1',
  `updated_by` int NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `fk_payments_namespace_id` (`namespace_id`),
  KEY `fk_payments_updated_by` (`updated_by`),
  KEY `idx_payments_order_status_active` (`order_id`,`billing_status`,`active_flag`),
  KEY `idx_payments_transaction_id` (`transaction_id`),
  KEY `idx_payments_mode_status` (`payment_mode`,`billing_status`),
  KEY `idx_payments_namespace_active` (`namespace_id`,`active_flag`),
  CONSTRAINT `fk_payments_namespace_id` FOREIGN KEY (`namespace_id`) REFERENCES `namespace` (`id`),
  CONSTRAINT `fk_payments_order_id` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `fk_payments_updated_by` FOREIGN KEY (`updated_by`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `payments` */

insert  into `payments`(`id`,`code`,`order_id`,`payment_mode`,`total_amount_to_pay`,`paid_amount`,`balance_amount`,`billing_status`,`transaction_id`,`remarks`,`namespace_id`,`active_flag`,`updated_by`,`updated_at`) values 
(1,'PYMT87286',14,3,80000.00,80000.00,0.00,3,'TRSCN7000','No',2,1,24,'2026-06-20 16:35:45'),
(2,'PYMT76319',15,3,80000.00,80000.00,0.00,3,'TRSCN7000','No',2,1,24,'2026-06-20 16:38:25'),
(3,'PYMT88511',16,3,80000.00,80000.00,0.00,3,'TRSCN7000','No',2,1,24,'2026-06-20 16:43:11'),
(4,'PYMT40685',17,3,126000.00,127000.00,1000.00,3,'TRSCN7000','No',2,1,24,'2026-06-20 16:51:01'),
(5,'PYMT74586',18,3,126000.00,127000.00,1000.00,3,'TRSCN7000','No',2,1,24,'2026-06-20 17:50:45'),
(6,'PYMT37834',19,3,126000.00,127000.00,1000.00,3,'TRSCN7000','No',2,1,24,'2026-06-20 18:11:22'),
(7,'PYMT68667',20,3,326000.00,327000.00,1000.00,3,'TRSCN7000','No',2,1,24,'2026-06-20 18:40:00'),
(8,'PYMT38195',21,3,46000.00,327000.00,281000.00,3,'TRSCN7000','No',2,1,24,'2026-06-20 19:54:39'),
(9,'PYMT35576',22,3,46000.00,327000.00,281000.00,3,'TRSCN7000','No',2,1,25,'2026-06-22 14:08:37'),
(10,'PYMT71155',23,3,46000.00,327000.00,281000.00,3,'TRSCN7000','No',2,1,24,'2026-06-22 14:22:31'),
(11,'PYMT85683',23,3,46000.00,327000.00,281000.00,3,'TRSCN7000','No',2,1,24,'2026-06-22 14:24:30'),
(12,'PYMT95789',24,3,46000.00,327000.00,281000.00,3,'TRSCN7000','No',2,1,24,'2026-06-23 17:07:00'),
(13,'PYMT25494',25,3,48000.00,327000.00,279000.00,3,'TRSCN7000','No',2,1,25,'2026-06-24 15:47:40'),
(14,'PYMT70493',26,3,48000.00,100000.00,52000.00,3,'TRSCN7000','No',2,1,25,'2026-06-24 16:04:42'),
(15,'PYMT65590',27,3,48000.00,100000.00,52000.00,3,'TRSCN7000','No',2,1,27,'2026-06-24 16:32:01'),
(16,'PYMT38630',28,3,48000.00,100000.00,52000.00,3,'TRSCN7000','No',2,1,27,'2026-06-25 12:58:39'),
(17,'PYMT35065',29,3,48000.00,100000.00,52000.00,3,'TRSCN7000','No',2,1,27,'2026-06-25 15:48:59'),
(18,'PYMT16845',29,3,48000.00,100000.00,52000.00,3,'TRSCN7000','No',2,1,27,'2026-06-25 15:51:43'),
(19,'PYMT83537',29,3,48000.00,100000.00,52000.00,3,'TRSCN7000','No',2,1,27,'2026-06-25 15:54:58'),
(20,'PYMT81236',30,3,48000.00,100000.00,52000.00,3,'TRSCN7000','No',2,1,27,'2026-06-25 16:19:23'),
(21,'PYMT15771',31,3,48000.00,100000.00,52000.00,3,'TRSCN7000','No',2,1,27,'2026-06-25 20:20:50'),
(22,'PYMT22778',32,3,48000.00,48000.00,0.00,3,'TRSCN7001','No',2,1,27,'2026-06-26 10:49:26'),
(23,'PYMT70492',33,3,48000.00,48000.00,0.00,3,'TRSCN7001','No',2,1,27,'2026-06-26 11:06:25'),
(24,'PYMT50460',34,3,48000.00,48000.00,0.00,3,'TRSCN7001','No',2,1,27,'2026-06-26 19:58:31'),
(25,'PYMT15420',35,3,48000.00,48000.00,0.00,3,'TRSCN7001','No',2,1,27,'2026-06-26 20:11:48'),
(26,'PYMT50669',36,3,48000.00,48000.00,0.00,3,'TRSCN7001','No',2,1,27,'2026-06-26 20:17:18'),
(27,'PYMT30170',37,3,48000.00,48000.00,0.00,3,'TRSCN7001','No',2,1,27,'2026-06-26 20:34:48');

/*Table structure for table `product_inventory` */

DROP TABLE IF EXISTS `product_inventory`;

CREATE TABLE `product_inventory` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(30) NOT NULL,
  `product_id` int NOT NULL,
  `available_quantity` int NOT NULL,
  `namespace_id` int NOT NULL,
  `active_flag` tinyint NOT NULL DEFAULT '1',
  `updated_by` int NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `fk_product_inventory_updated_by` (`updated_by`),
  KEY `idx_product_inventory_namespace_active` (`namespace_id`,`active_flag`),
  KEY `idx_product_inventory_product_active` (`product_id`,`active_flag`),
  CONSTRAINT `fk_product_inventory_namespace_id` FOREIGN KEY (`namespace_id`) REFERENCES `namespace` (`id`),
  CONSTRAINT `fk_product_inventory_product_id` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `fk_product_inventory_updated_by` FOREIGN KEY (`updated_by`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `product_inventory` */

insert  into `product_inventory`(`id`,`code`,`product_id`,`available_quantity`,`namespace_id`,`active_flag`,`updated_by`,`updated_at`) values 
(1,'PINV53820',2,10,2,1,1,'2026-06-11 13:49:33'),
(2,'PINV13622',4,0,2,1,24,'2026-06-23 17:36:00'),
(3,'PINV99999',5,9,2,1,24,'2026-06-26 20:34:49');

/*Table structure for table `products` */

DROP TABLE IF EXISTS `products`;

CREATE TABLE `products` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(30) NOT NULL,
  `name` varchar(50) NOT NULL,
  `description` varchar(300) NOT NULL,
  `price` decimal(12,2) NOT NULL,
  `brand_id` int NOT NULL,
  `category_id` int NOT NULL,
  `namespace_id` int NOT NULL,
  `active_flag` tinyint NOT NULL DEFAULT '1',
  `updated_by` int NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `fk_products_brand_id` (`brand_id`),
  KEY `fk_products_namespace_id` (`namespace_id`),
  KEY `fk_products_updated_by` (`updated_by`),
  KEY `idx_products_category_brand_active` (`category_id`,`brand_id`,`active_flag`),
  KEY `idx_products_name_active` (`name`,`active_flag`),
  KEY `idx_products_namespace_active` (`namespace_id`,`active_flag`),
  CONSTRAINT `fk_products_brand_id` FOREIGN KEY (`brand_id`) REFERENCES `brands` (`id`),
  CONSTRAINT `fk_products_category_id` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`),
  CONSTRAINT `fk_products_namespace_id` FOREIGN KEY (`namespace_id`) REFERENCES `namespace` (`id`),
  CONSTRAINT `fk_products_updated_by` FOREIGN KEY (`updated_by`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `products` */

insert  into `products`(`id`,`code`,`name`,`description`,`price`,`brand_id`,`category_id`,`namespace_id`,`active_flag`,`updated_by`,`updated_at`) values 
(1,'PRODUCT90194','Galaxy s24 ultra','can your phone do this?',180000.00,1,1,2,1,1,'2026-06-11 13:37:34'),
(2,'PRODUCT12880','Galaxy s24 Pro ultra ','can your phone do this?',190000.00,1,1,2,1,1,'2026-06-11 13:41:58'),
(3,'PRODUCT55653','Galaxy s25','can your phone do this?',20000.00,1,1,2,1,24,'2026-06-16 13:33:31'),
(4,'PRODUCT22634','xperia','real phone',40000.00,2,1,2,1,31,'2026-06-18 19:51:42'),
(5,'PRODUCT31177','xperia 2','real phone',48000.00,2,1,2,1,24,'2026-06-24 15:24:31'),
(6,'PRODUCT38227','bravia','real tv to bring theater for your home.',55000.00,2,1,2,1,24,'2026-06-26 19:09:27');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(30) NOT NULL,
  `username` varchar(100) NOT NULL,
  `namespace_id` int NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(150) NOT NULL,
  `mobile` varchar(20) NOT NULL,
  `role` tinyint NOT NULL,
  `active_flag` tinyint NOT NULL DEFAULT '1',
  `updated_by` int NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `mobile` (`mobile`),
  KEY `fk_users_updated_by` (`updated_by`),
  KEY `idx_users_role_active` (`role`,`active_flag`),
  KEY `idx_users_namespace_active` (`namespace_id`,`active_flag`),
  KEY `idx_user_username_namespace` (`username`,`namespace_id`),
  CONSTRAINT `fk_users_namespace_id` FOREIGN KEY (`namespace_id`) REFERENCES `namespace` (`id`),
  CONSTRAINT `fk_users_updated_by` FOREIGN KEY (`updated_by`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `user` */

insert  into `user`(`id`,`code`,`username`,`namespace_id`,`password`,`email`,`mobile`,`role`,`active_flag`,`updated_by`,`updated_at`) values 
(1,'SUPER000','super admin',1,'$2a$10$EMi2txPAI3mpUw1oCMFVc.hd8xTOHw8NhU/Mxc2KOvw7Jb4cCdUdC','superadmin@gmail.com','+919361595275',0,1,1,'2026-06-15 12:50:50'),
(24,'USR54604','prakash',2,'$2a$10$2Chf9JcJNzBQYR.dQfbzaePNtTx8qu84fnQLmcroxRof.dP6aFQji','prakash@gmail.com','+918428578348',1,1,24,'2026-06-15 20:39:57'),
(25,'USR63991','vicky',3,'$2a$10$8AP5SHu1NvmSxxxwJnQB/OU5h5sT15K29FAyKfTv0AYyM/ToOsV56','vicky@gmail.com','+918610279793',1,1,1,'2026-06-15 13:49:39'),
(26,'USR18655','kabilan',3,'$2a$10$KBy.iKwaAh9rpa1WJZP9DOss9jJybRBm2Bmk/6jReODf6.e5cWrUK','kabilan@gmail.com','+919750160405',1,1,1,'2026-06-15 14:12:42'),
(27,'USR68598','john',2,'$2a$10$k.tyE0Glg5XOjKQPYgWc2e2Ioq8xK7BVSc0Yrsb37bF24TznIBmXi','john@gmail.com','+918428578349',2,1,24,'2026-06-25 12:20:59'),
(28,'USR26399','ram',2,'$2a$10$QFRbnFrHRb0IN5Jv.9H4zODoQnV.tzdj3RUdXodRc2mtsu.fO8j06','ram@gmail.com','+918428578340',2,1,1,'2026-06-15 19:48:26'),
(29,'USR86047','raju',2,'$2a$10$Zti9teD4VSkvu.0OSl.HveWrkkjfKCG0c4.7rV.qsdsyzcYeE5ufa','raju@gmail.com','+918428578343',2,1,24,'2026-06-15 20:26:36'),
(30,'USR18649','sam',2,'$2a$10$x2ajdW.kig626EI1txfldebA8XP7W3fMo.CIMKSaLpZQ7p3J0nZH.','sam@gmail.com','+918428578341',2,1,24,'2026-06-15 20:41:15'),
(31,'USR65561','askar',2,'$2a$10$XqK07hpx06lfDx.MpGnATOogEcbjr3dS8c/DEX70UXTXipKGgt8I.','askar@gmail.com','+918428578346',2,1,24,'2026-06-16 13:43:48'),
(32,'USR66109','vinoth',2,'$2a$10$NXin0nnLUcLa.wruQQ.tFexAJR0oaPI2KaWfybAmA5ZEStWktrf4.','vinoth@gmail.com','+918428578354',3,1,1,'2026-06-17 20:00:00'),
(33,'USR77996','aravind',2,'$2a$10$PK34rMbf3QSpFCAKv4wIVOXpHArMJLRBq5H3B2DoY6g.wb9qMVPyC','aravind@gmail.com','+919428578354',3,1,1,'2026-06-17 20:01:08'),
(34,'USR35171','arya',3,'$2a$10$V30fRKpUWPFC3Op9jGdGvOT4.F/iWxaw8aHBoR.J9gWGS8PkuWhIy','arya@gmail.com','+917428578354',3,1,26,'2026-06-18 12:05:15'),
(38,'USR52150','manoje',2,'$2a$10$yGtT2NfGS6HGN5bUUSVooetuhjpzncjFyRLTz6BSt834L.Cd1sYvW','manoju@gmail.com','+917428578311',3,1,24,'2026-06-23 20:02:02');
