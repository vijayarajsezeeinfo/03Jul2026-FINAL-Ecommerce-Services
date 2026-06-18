/*
SQLyog Community v13.3.1 (64 bit)
MySQL - 8.0.46 : Database - ecommerce_services_db
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`ecommerce_services_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `ecommerce_services_db`;

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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
  CONSTRAINT `fk_orders_namespace_id` FOREIGN KEY (`namespace_id`) REFERENCES `namespace` (`id`),
  CONSTRAINT `fk_orders_updated_by` FOREIGN KEY (`updated_by`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_orders_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
  CONSTRAINT `fk_payments_namespace_id` FOREIGN KEY (`namespace_id`) REFERENCES `namespace` (`id`),
  CONSTRAINT `fk_payments_order_id` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `fk_payments_updated_by` FOREIGN KEY (`updated_by`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
  CONSTRAINT `fk_products_brand_id` FOREIGN KEY (`brand_id`) REFERENCES `brands` (`id`),
  CONSTRAINT `fk_products_category_id` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`),
  CONSTRAINT `fk_products_namespace_id` FOREIGN KEY (`namespace_id`) REFERENCES `namespace` (`id`),
  CONSTRAINT `fk_products_updated_by` FOREIGN KEY (`updated_by`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `shipments` */

DROP TABLE IF EXISTS `shipments`;

CREATE TABLE `shipments` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(30) NOT NULL,
  `order_id` int NOT NULL,
  `tracking_number` varchar(50) NOT NULL,
  `status` tinyint NOT NULL,
  `namespace_id` int NOT NULL,
  `active_flag` tinyint NOT NULL DEFAULT '1',
  `updated_by` int NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `fk_shipments_namespace_id` (`namespace_id`),
  KEY `fk_shipments_updated_by` (`updated_by`),
  KEY `idx_shipments_tracking_status_active` (`tracking_number`,`status`,`active_flag`),
  KEY `idx_shipments_order_status` (`order_id`,`status`),
  CONSTRAINT `fk_shipments_namespace_id` FOREIGN KEY (`namespace_id`) REFERENCES `namespace` (`id`),
  CONSTRAINT `fk_shipments_order_id` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `fk_shipments_updated_by` FOREIGN KEY (`updated_by`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
  CONSTRAINT `fk_users_namespace_id` FOREIGN KEY (`namespace_id`) REFERENCES `namespace` (`id`),
  CONSTRAINT `fk_users_updated_by` FOREIGN KEY (`updated_by`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
