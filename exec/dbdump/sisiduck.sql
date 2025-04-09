-- MySQL dump 10.13  Distrib 8.0.41, for Linux (x86_64)
--
-- Host: localhost    Database: sisiduck
-- ------------------------------------------------------
-- Server version	8.0.41

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `accounts`
--

DROP TABLE IF EXISTS `accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `accounts` (
  `account_pw` int NOT NULL,
  `account_type_code` varchar(3) COLLATE utf8mb4_unicode_ci NOT NULL,
  `amount` decimal(50,0) NOT NULL,
  `daily_transfer_limit` decimal(38,2) NOT NULL,
  `one_time_transfer_limit` decimal(38,2) NOT NULL,
  `account_expiry_date` datetime(6) NOT NULL,
  `account_id` bigint NOT NULL AUTO_INCREMENT,
  `bank_id` bigint NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `currency` varchar(8) COLLATE utf8mb4_unicode_ci NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `last_transaction_date` datetime(6) NOT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  `user_id` bigint NOT NULL,
  `account_name` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `account_type_unique_no` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `status` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `account_description` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `account_no` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accounts`
--

LOCK TABLES `accounts` WRITE;
/*!40000 ALTER TABLE `accounts` DISABLE KEYS */;
INSERT INTO `accounts` VALUES (1234,'1',0,5000000.00,1000000.00,'2030-04-09 10:09:14.917868',2,1,'2025-04-09 10:09:14.938606','KRW','2025-04-09 10:37:52.000000','2025-04-09 10:09:14.918834','2025-04-09 10:09:14.938606',4,'더미데이터','GENERAL001','ACTIVE','기본 계좌 설명','0018037473008926'),(1234,'1',0,5000000.00,1000000.00,'2030-04-09 10:25:08.540451',3,1,'2025-04-09 10:25:08.542558','KRW','2025-04-09 10:38:07.000000','2025-04-09 10:25:08.540499','2025-04-09 10:25:08.542558',1,'연예인 적금','GENERAL001','ACTIVE','기본 계좌 설명','0019874931954311'),(1234,'1',0,5000000.00,1000000.00,'2030-04-09 10:40:03.544273',4,1,'2025-04-09 10:40:03.563178','KRW',NULL,'2025-04-09 10:40:03.544317','2025-04-09 10:40:03.563178',1,'연예인 적금','GENERAL001','ACTIVE','기본 계좌 설명','0019874931954311');
/*!40000 ALTER TABLE `accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `banks`
--

DROP TABLE IF EXISTS `banks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `banks` (
  `bank_id` bigint NOT NULL AUTO_INCREMENT,
  `bank_code` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `bank_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`bank_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `banks`
--

LOCK TABLES `banks` WRITE;
/*!40000 ALTER TABLE `banks` DISABLE KEYS */;
INSERT INTO `banks` VALUES (1,'001','한국은행'),(2,'002','산업은행'),(3,'003','기업은행'),(4,'004','국민은행'),(5,'011','농협은행'),(6,'020','우리은행'),(7,'023','SC제일은행'),(8,'027','시티은행'),(9,'032','대구은행'),(10,'034','광주은행'),(11,'035','제주은행'),(12,'037','전북은행'),(13,'039','경남은행'),(14,'045','새마을금고'),(15,'081','KEB하나은행'),(16,'088','신한은행'),(17,'090','카카오뱅크'),(18,'999','싸피은행');
/*!40000 ALTER TABLE `banks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `deposit_transactions`
--

DROP TABLE IF EXISTS `deposit_transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `deposit_transactions` (
  `transaction_type` varchar(1) COLLATE utf8mb4_unicode_ci NOT NULL,
  `transaction_balance` bigint NOT NULL,
  `transaction_date` datetime(6) NOT NULL,
  `transaction_id` bigint NOT NULL AUTO_INCREMENT,
  `transaction_type_name` varchar(8) COLLATE utf8mb4_unicode_ci NOT NULL,
  `transaction_unique_no` bigint NOT NULL,
  `deposit_account_no` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL,
  `withdrawal_account_no` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL,
  `deposit_transaction_summary` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `status` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `withdrawal_transaction_summary` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`transaction_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `deposit_transactions`
--

LOCK TABLES `deposit_transactions` WRITE;
/*!40000 ALTER TABLE `deposit_transactions` DISABLE KEYS */;
/*!40000 ALTER TABLE `deposit_transactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `entertainer_pictures`
--

DROP TABLE IF EXISTS `entertainer_pictures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `entertainer_pictures` (
  `created_at` datetime(6) NOT NULL,
  `entertainer_id` bigint NOT NULL,
  `entertainer_picture_id` bigint NOT NULL AUTO_INCREMENT,
  `modified_at` datetime(6) DEFAULT NULL,
  `entertainer_picture_url` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`entertainer_picture_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entertainer_pictures`
--

LOCK TABLES `entertainer_pictures` WRITE;
/*!40000 ALTER TABLE `entertainer_pictures` DISABLE KEYS */;
/*!40000 ALTER TABLE `entertainer_pictures` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `entertainer_savings_accounts`
--

DROP TABLE IF EXISTS `entertainer_savings_accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `entertainer_savings_accounts` (
  `amount` decimal(38,2) NOT NULL,
  `interest_rate` double NOT NULL,
  `max_subscription_balance` decimal(38,2) NOT NULL,
  `min_subscription_balance` decimal(38,2) NOT NULL,
  `bank_id` bigint NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `duration` bigint NOT NULL,
  `entertainer_id` bigint NOT NULL,
  `id` bigint NOT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  `user_id` bigint NOT NULL,
  `withdrawal_account_id` bigint NOT NULL,
  `account_no` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `image_url` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `product_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entertainer_savings_accounts`
--

LOCK TABLES `entertainer_savings_accounts` WRITE;
/*!40000 ALTER TABLE `entertainer_savings_accounts` DISABLE KEYS */;
INSERT INTO `entertainer_savings_accounts` VALUES (0.00,0.05,10000000.00,1000.00,1,'2025-04-09 10:20:07.604640',NULL,60,1,1,'2025-04-09 10:20:07.604640',4,2,'0010096498423784','example.com','IU'),(0.00,0.05,10000000.00,1000.00,1,'2025-04-09 10:25:22.911799','2025-04-09 10:37:52.000000',60,1,2,'2025-04-09 10:25:22.911799',1,3,'0014051821782361','example.com','IU');
/*!40000 ALTER TABLE `entertainer_savings_accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `entertainer_savings_accounts_seq`
--

DROP TABLE IF EXISTS `entertainer_savings_accounts_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `entertainer_savings_accounts_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entertainer_savings_accounts_seq`
--

LOCK TABLES `entertainer_savings_accounts_seq` WRITE;
/*!40000 ALTER TABLE `entertainer_savings_accounts_seq` DISABLE KEYS */;
INSERT INTO `entertainer_savings_accounts_seq` VALUES (101);
/*!40000 ALTER TABLE `entertainer_savings_accounts_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `entertainer_savings_transaction_details`
--

DROP TABLE IF EXISTS `entertainer_savings_transaction_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `entertainer_savings_transaction_details` (
  `amount` decimal(38,2) NOT NULL,
  `transaction_balance` decimal(38,2) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `deposit_account_id` bigint NOT NULL,
  `id` bigint NOT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  `transaction_unique_no` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `withdrawal_account_id` bigint NOT NULL,
  `image_url` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `message` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entertainer_savings_transaction_details`
--

LOCK TABLES `entertainer_savings_transaction_details` WRITE;
/*!40000 ALTER TABLE `entertainer_savings_transaction_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `entertainer_savings_transaction_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `entertainer_savings_transaction_details_seq`
--

DROP TABLE IF EXISTS `entertainer_savings_transaction_details_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `entertainer_savings_transaction_details_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entertainer_savings_transaction_details_seq`
--

LOCK TABLES `entertainer_savings_transaction_details_seq` WRITE;
/*!40000 ALTER TABLE `entertainer_savings_transaction_details_seq` DISABLE KEYS */;
INSERT INTO `entertainer_savings_transaction_details_seq` VALUES (1);
/*!40000 ALTER TABLE `entertainer_savings_transaction_details_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `entertainers`
--

DROP TABLE IF EXISTS `entertainers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `entertainers` (
  `entertainer_id` bigint NOT NULL AUTO_INCREMENT,
  `entertainer_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `fandom_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `entertainer_profile_url` varchar(1024) COLLATE utf8mb4_unicode_ci NOT NULL,
  `birth_date` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`entertainer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entertainers`
--

LOCK TABLES `entertainers` WRITE;
/*!40000 ALTER TABLE `entertainers` DISABLE KEYS */;
INSERT INTO `entertainers` VALUES (1,'IU','IUFAN','https://example.com/profiles/iu','1993-05-16'),(2,'BTS','ARMY','https://example.com/profiles/bts','2013-06-13'),(3,'BLACKPINK','BLINK','https://example.com/profiles/blackpink','2016-08-08');
/*!40000 ALTER TABLE `entertainers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `funding_groups`
--

DROP TABLE IF EXISTS `funding_groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `funding_groups` (
  `funding_expiry_date` date NOT NULL,
  `account_id` bigint NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `entertainer_id` bigint NOT NULL,
  `goal_amount` bigint NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `modified_at` datetime(6) DEFAULT NULL,
  `funding_description` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `funding_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `status` enum('CANCELED','FAILED','INPROGRESS','SUCCESS','TERMINATED') COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `funding_groups`
--

LOCK TABLES `funding_groups` WRITE;
/*!40000 ALTER TABLE `funding_groups` DISABLE KEYS */;
/*!40000 ALTER TABLE `funding_groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `funding_pending_transactions`
--

DROP TABLE IF EXISTS `funding_pending_transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `funding_pending_transactions` (
  `account_id` bigint NOT NULL,
  `balance` bigint NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `funding_id` bigint NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `modified_at` datetime(6) DEFAULT NULL,
  `user_id` bigint NOT NULL,
  `content` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `deposit_user_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `status` enum('FAILED','PENDING','SKIPPERD','SUCCESS') COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `funding_pending_transactions`
--

LOCK TABLES `funding_pending_transactions` WRITE;
/*!40000 ALTER TABLE `funding_pending_transactions` DISABLE KEYS */;
/*!40000 ALTER TABLE `funding_pending_transactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_board_amounts`
--

DROP TABLE IF EXISTS `group_board_amounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `group_board_amounts` (
  `amount` bigint DEFAULT NULL,
  `board_id` bigint DEFAULT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_board_amounts`
--

LOCK TABLES `group_board_amounts` WRITE;
/*!40000 ALTER TABLE `group_board_amounts` DISABLE KEYS */;
/*!40000 ALTER TABLE `group_board_amounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_board_images`
--

DROP TABLE IF EXISTS `group_board_images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `group_board_images` (
  `board_id` bigint DEFAULT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `image_url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_board_images`
--

LOCK TABLES `group_board_images` WRITE;
/*!40000 ALTER TABLE `group_board_images` DISABLE KEYS */;
/*!40000 ALTER TABLE `group_board_images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_boards`
--

DROP TABLE IF EXISTS `group_boards`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `group_boards` (
  `created_at` datetime(6) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `funding_id` bigint NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `modified_at` datetime(6) DEFAULT NULL,
  `content` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_boards`
--

LOCK TABLES `group_boards` WRITE;
/*!40000 ALTER TABLE `group_boards` DISABLE KEYS */;
/*!40000 ALTER TABLE `group_boards` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_users`
--

DROP TABLE IF EXISTS `group_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `group_users` (
  `created_at` datetime(6) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `funding_group_id` bigint NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `modified_at` datetime(6) DEFAULT NULL,
  `user_id` bigint NOT NULL,
  `role` enum('ADMIN','USER') COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_users`
--

LOCK TABLES `group_users` WRITE;
/*!40000 ALTER TABLE `group_users` DISABLE KEYS */;
/*!40000 ALTER TABLE `group_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `saving_transactions`
--

DROP TABLE IF EXISTS `saving_transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `saving_transactions` (
  `transaction_type` varchar(1) COLLATE utf8mb4_unicode_ci NOT NULL,
  `saving_count` bigint NOT NULL,
  `transaction_balance` bigint NOT NULL,
  `transaction_date` datetime(6) NOT NULL,
  `transaction_id` bigint NOT NULL AUTO_INCREMENT,
  `transaction_type_name` varchar(8) COLLATE utf8mb4_unicode_ci NOT NULL,
  `transaction_unique_no` bigint NOT NULL,
  `deposit_account_no` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL,
  `withdrawal_account_no` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL,
  `deposit_transaction_summary` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `status` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `withdrawal_transaction_summary` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`transaction_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `saving_transactions`
--

LOCK TABLES `saving_transactions` WRITE;
/*!40000 ALTER TABLE `saving_transactions` DISABLE KEYS */;
/*!40000 ALTER TABLE `saving_transactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `savings_accounts`
--

DROP TABLE IF EXISTS `savings_accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `savings_accounts` (
  `account_expiry_date` datetime(6) NOT NULL,
  `balance` bigint NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `modified_at` datetime(6) DEFAULT NULL,
  `user_id` bigint NOT NULL,
  `account_nickname` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `account_no` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `account_type_unique_no` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `savings_accounts`
--

LOCK TABLES `savings_accounts` WRITE;
/*!40000 ALTER TABLE `savings_accounts` DISABLE KEYS */;
/*!40000 ALTER TABLE `savings_accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `savings_items`
--

DROP TABLE IF EXISTS `savings_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `savings_items` (
  `interest_rate` decimal(38,2) NOT NULL,
  `bank_id` bigint NOT NULL,
  `savings_item_id` bigint NOT NULL AUTO_INCREMENT,
  `account_description` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `account_type_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `account_type_unique_no` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `interest_rate_description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `savings_item_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`savings_item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `savings_items`
--

LOCK TABLES `savings_items` WRITE;
/*!40000 ALTER TABLE `savings_items` DISABLE KEYS */;
/*!40000 ALTER TABLE `savings_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `skipped_transactions`
--

DROP TABLE IF EXISTS `skipped_transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `skipped_transactions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `transaction_balance` bigint DEFAULT NULL,
  `deposit_account_no` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `deposit_transaction_summary` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `user_email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `withdrawal_account_no` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `withdrawal_transaction_summary` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `skipped_transactions`
--

LOCK TABLES `skipped_transactions` WRITE;
/*!40000 ALTER TABLE `skipped_transactions` DISABLE KEYS */;
/*!40000 ALTER TABLE `skipped_transactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `birth_date` datetime(6) DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `entertainer_id` bigint DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  `represent_account_id` bigint DEFAULT NULL,
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `credit_point` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `member_role` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `phone_number` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `social_email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `user_key` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `social_type` enum('SSAFY') COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('2000-01-01 00:00:00.000000','2025-04-09 09:47:24.000000',NULL,NULL,'2025-04-09 09:47:24.000000',NULL,1,'Example Address','0','GENERAL','권민채','010-0000-0000','minchae075@naver.com','bc528a94-b677-47a8-9d61-a00b30f2acac','SSAFY'),('2000-01-01 00:00:00.000000','2025-04-09 09:47:25.000000',NULL,NULL,'2025-04-09 09:47:25.000000',NULL,2,'Example Address','0','GENERAL','강진주','010-0000-0000','rkdwlswn227@gmail.com','a720273d-4e7f-4ae2-8c9a-10d39dbd1473','SSAFY'),('2000-01-01 00:00:00.000000','2025-04-09 09:47:30.000000',NULL,NULL,'2025-04-09 09:47:30.000000',NULL,3,'Example Address','0','GENERAL','이재홍','010-0000-0000','jaehong9809@naver.com','64e3c30d-0423-43b5-82c3-47afb859cd18','SSAFY'),('2000-01-01 00:00:00.000000','2025-04-09 09:58:30.000000',NULL,NULL,'2025-04-09 09:58:30.000000',NULL,4,'Example Address','0','GENERAL','김예진','010-0000-0000','kimyejin0044@gmail.com','03f4e5d6-4daa-4c6c-8346-0351ca1fc897','SSAFY');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-09 10:52:59
