-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: products_schema
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `basket_item`
--

DROP TABLE IF EXISTS `basket_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `basket_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_user` int DEFAULT NULL,
  `id_product` int DEFAULT NULL,
  `count` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id_basket_idx` (`id_user`),
  KEY `basket_id_usert_idx` (`id_product`),
  CONSTRAINT `basket_id_usert` FOREIGN KEY (`id_product`) REFERENCES `product` (`id`),
  CONSTRAINT `user_id_basket` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `basket_item`
--

LOCK TABLES `basket_item` WRITE;
/*!40000 ALTER TABLE `basket_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `basket_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `path` varchar(150) DEFAULT NULL,
  `id_classification` int DEFAULT NULL,
  `status` tinyint DEFAULT NULL,
  `description` varchar(450) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fr_classification_category_idx` (`id_classification`),
  CONSTRAINT `fr_classification_category` FOREIGN KEY (`id_classification`) REFERENCES `classification` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (5,'цкцккццк212','/photos/68749d7a-7cd4-42d7-9543-1b37c03e8e1d.retro-film-production-canva-photo.xc6cf828a.png',6,1,'цкцккццк212'),(33,'Питайя','/photos/52a6534b-fd1a-4400-a08f-7fe3afbe3c27.top_20_maloizvestnykh_no_ochen_interesnykh_ekzoticheskikh_fruktov.jpg',3,0,'кцкц'),(70,'wrrw','/photos/5450fe31-b140-4201-971e-5be98f53b381.PtGfIimPZKY.jpg',2,1,'wr'),(73,'айа','/photos/47ba13ca-d5f9-4722-9599-13317021a08f.retro-film-production-canva-photo.xc6cf828a.png',4,1,'йаай');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category_product_entity_list`
--

DROP TABLE IF EXISTS `category_product_entity_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category_product_entity_list` (
  `category_entity_id` int NOT NULL,
  `product_entity_list_id` int NOT NULL,
  UNIQUE KEY `UK_if28t7hh25tx7ya8tbptp8d9l` (`product_entity_list_id`),
  KEY `FK5qvge56ktpdq1aqjxhcfhwpqf` (`category_entity_id`),
  CONSTRAINT `FK5qvge56ktpdq1aqjxhcfhwpqf` FOREIGN KEY (`category_entity_id`) REFERENCES `category` (`id`),
  CONSTRAINT `FKj5wurv0tsmvdx8y0ox0vgqw60` FOREIGN KEY (`product_entity_list_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category_product_entity_list`
--

LOCK TABLES `category_product_entity_list` WRITE;
/*!40000 ALTER TABLE `category_product_entity_list` DISABLE KEYS */;
/*!40000 ALTER TABLE `category_product_entity_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `classification`
--

DROP TABLE IF EXISTS `classification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `classification` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `path` varchar(150) DEFAULT NULL,
  `status` tinyint DEFAULT NULL,
  `description` varchar(450) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `classification`
--

LOCK TABLES `classification` WRITE;
/*!40000 ALTER TABLE `classification` DISABLE KEYS */;
INSERT INTO `classification` VALUES (2,'Оwewe','/photos/bda0c384-36e7-422d-b858-b44bb45a72e8.kakie_frukty_i_ovoshchi_samye_poleznye.jpg',1,'Это овощи, покупайте их в нашем магазине.'),(3,'Фрукты','/photos/d4980a7a-860b-4ad7-9b07-302d4559f87c.87796040-78f6-11ed-8ced-6f58a6938111.jpg',1,'Это фрукты, они очень вкусные'),(4,'new','/photos/4aa0adf3-08c5-46d4-b99f-0b96f62ef977.retro-film-production-canva-photo.xc6cf828a.png',1,'ww'),(6,'wewe','/photos/b7f8e2cc-2a22-409e-8f01-0d20418245df.Screenshot_2.png',1,'wewe'),(7,'wrwrrwwrrsf','/photos/c140e3fb-4a61-4c3d-b5f2-3e9aafe72002.rBEeJGKxjXyAfuaLAALDc3CS9iQ25.jpeg',0,'wrrwrw');
/*!40000 ALTER TABLE `classification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `classification_category_entity_list`
--

DROP TABLE IF EXISTS `classification_category_entity_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `classification_category_entity_list` (
  `classification_entity_id` int NOT NULL,
  `category_entity_list_id` int NOT NULL,
  UNIQUE KEY `UK_oj2c3nudt342x5qvfwx8lssh6` (`category_entity_list_id`),
  KEY `FK3otkf92oql3fhno731t30w2a8` (`classification_entity_id`),
  CONSTRAINT `FK3otkf92oql3fhno731t30w2a8` FOREIGN KEY (`classification_entity_id`) REFERENCES `classification` (`id`),
  CONSTRAINT `FKgf1gnwtshtvvyyacpsotsvu4p` FOREIGN KEY (`category_entity_list_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `classification_category_entity_list`
--

LOCK TABLES `classification_category_entity_list` WRITE;
/*!40000 ALTER TABLE `classification_category_entity_list` DISABLE KEYS */;
/*!40000 ALTER TABLE `classification_category_entity_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `favorite_product`
--

DROP TABLE IF EXISTS `favorite_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `favorite_product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `status` bit(1) DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2lg2xrrb5c14y0s8hwoyn783n` (`product_id`),
  KEY `FK77loqyqxss454wdhhyhuatyfx` (`user_id`),
  CONSTRAINT `FK2lg2xrrb5c14y0s8hwoyn783n` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FK77loqyqxss454wdhhyhuatyfx` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `favorite_product`
--

LOCK TABLES `favorite_product` WRITE;
/*!40000 ALTER TABLE `favorite_product` DISABLE KEYS */;
INSERT INTO `favorite_product` VALUES (2,_binary '',51,21),(3,_binary '',28,21),(4,_binary '',42,21),(5,_binary '',43,21),(6,_binary '',41,21),(7,_binary '',44,21),(8,_binary '',28,22),(9,_binary '',41,22),(10,_binary '',42,22),(11,_binary '',43,22),(12,_binary '',28,23),(15,_binary '',50,21),(16,_binary '',52,21),(17,_binary '',53,21),(18,_binary '',42,23),(20,_binary '',41,23);
/*!40000 ALTER TABLE `favorite_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order` (
  `id` int NOT NULL,
  `id_user` int DEFAULT NULL,
  `status` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fr_order_user_idx` (`id_user`),
  CONSTRAINT `fr_order_user` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_table`
--

DROP TABLE IF EXISTS `order_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_table` (
  `id` int NOT NULL AUTO_INCREMENT,
  `comment` varchar(255) DEFAULT NULL,
  `date_time` datetime(6) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `id_user` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8mv2cvu8jyyggn6cmdt3gcgwn` (`id_user`),
  CONSTRAINT `FK8mv2cvu8jyyggn6cmdt3gcgwn` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_table`
--

LOCK TABLES `order_table` WRITE;
/*!40000 ALTER TABLE `order_table` DISABLE KEYS */;
INSERT INTO `order_table` VALUES (1,'цккц','2023-08-21 11:09:41.858914',_binary '',21),(2,'цккц','2023-08-21 11:09:51.064549',_binary '',22),(3,'цккц','2023-08-21 11:10:02.588810',_binary '',21),(4,'цккц','2023-08-21 11:10:05.342174',_binary '',21),(5,'цккццкце','2023-08-21 11:10:08.633202',_binary '',21),(6,'цккццкце','2023-08-21 11:10:15.317513',NULL,21),(7,'цккццкце','2023-08-21 11:10:18.898315',NULL,21),(8,'цккццкце','2023-08-21 11:10:31.053714',NULL,21),(9,'цккц','2023-08-21 11:14:15.971196',_binary '\0',21),(10,'twtt','2023-08-23 11:56:44.611116',_binary '\0',21),(11,'ляляля','2023-08-26 12:06:40.632509',NULL,23),(12,'еууее','2023-08-26 12:07:24.275192',_binary '\0',23),(13,'fwf','2023-08-30 00:53:13.347656',NULL,23),(14,'gygy','2023-08-30 13:39:56.389194',_binary '\0',23);
/*!40000 ALTER TABLE `order_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_table_product_by_order_entity`
--

DROP TABLE IF EXISTS `order_table_product_by_order_entity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_table_product_by_order_entity` (
  `order_table_entity_id` int NOT NULL,
  `product_by_order_entity_id` int NOT NULL,
  UNIQUE KEY `UK_lki1qp370oce8erqa0x0jdkup` (`product_by_order_entity_id`),
  KEY `FKcipkeme0mj58jrglwcxtg8kye` (`order_table_entity_id`),
  CONSTRAINT `FKcipkeme0mj58jrglwcxtg8kye` FOREIGN KEY (`order_table_entity_id`) REFERENCES `order_table` (`id`),
  CONSTRAINT `FKs4ghq0djyccelffb8tfx5hvxi` FOREIGN KEY (`product_by_order_entity_id`) REFERENCES `product_by_order` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_table_product_by_order_entity`
--

LOCK TABLES `order_table_product_by_order_entity` WRITE;
/*!40000 ALTER TABLE `order_table_product_by_order_entity` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_table_product_by_order_entity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `id_category` int DEFAULT NULL,
  `description` varchar(450) DEFAULT NULL,
  `path` varchar(150) DEFAULT NULL,
  `status` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fr_category_product_idx` (`id_category`),
  CONSTRAINT `fr_category_product` FOREIGN KEY (`id_category`) REFERENCES `category` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (12,'цккццк',0.04,33,'цккц','/photos/6c882a74-db5c-4c42-a83a-829369a0f8c4.top_20_maloizvestnykh_no_ochen_interesnykh_ekzoticheskikh_fruktov.jpg',0),(28,'цкцкцкц',0.02,33,'кц','/photos/7e7c5fd3-bd96-4853-83ea-c73c6ecf5855.e87b941b85_500.jpg',0),(29,'цкцкцкцй',0.02,33,'кц','/photos/38a7c562-10f4-4905-bbc2-1c0e4043ddff.PtGfIimPZKY.jpg',0),(30,'цкцкцкцйк',0.02,33,'кц','/photos/5f0bdd46-d47f-4082-b086-660d3909bc54.PtGfIimPZKY.jpg',0),(36,'wrrw',0.01,70,'wrwr','/photos/f724365e-efb5-4ceb-8695-53504c324a2a.PtGfIimPZKY.jpg',0),(37,'wrrww',0.01,70,'wrwr','/photos/3fe29815-3f08-4f99-9e49-1562f880d1b1.PtGfIimPZKY.jpg',0),(41,'wrrwwr',0.02,70,'wr','/photos/35385706-bebd-4d64-8778-5e31d088fa46.e87b941b85_500.jpg',1),(42,'wrrwwrr',0.02,70,'wr','/photos/609c8e8f-e076-41e7-8549-5c6669e178b2.e87b941b85_500.jpg',1),(43,'wrrwwrrr',0.02,70,'wr','/photos/f33a87f5-fddd-4a29-a271-89dde1bed811.e87b941b85_500.jpg',1),(44,'wrrwwrrr2',0.02,70,'wr','/photos/e8ad6f57-1da7-42ee-8620-fb32ee6b156f.e87b941b85_500.jpg',1),(45,'wrrwwrrr22',0.02,70,'wr','/photos/67477ef5-be11-413d-9985-ce23cf677a1f.e87b941b85_500.jpg',1),(46,'wrrwwrrr223',0.02,70,'wr','/photos/4be3bbaa-8b3b-412d-9444-0e2e3590d036.e87b941b85_500.jpg',1),(50,'wwwwwwwафыафы',0.02,5,'цккц','/photos/3bb77893-2b6e-49ef-a8e3-3879d351637f.01.png',1),(51,'wwww',0.02,5,'цккц','/photos/3c01a5e9-3e98-4e59-92c9-eb21aff82c53.01.png',1),(52,'Питайя4',0.02,33,'цккц','/photos/1fa0040f-5553-4f3b-bb70-5b712def1b1c.01.png',1),(53,'Питайя45',0.02,33,'цккц','/photos/ebc6f472-bcc1-43b9-bc29-ca699c55d135.01.png',1),(54,'Питайя452',0.02,33,'цккц','/photos/a6c14406-7d8e-48ff-9795-fdf1cebcf129.01.png',1),(55,'Питайя4521',0.02,33,'цккц','/photos/ad2c689b-fc36-4fb1-989d-ea7155778a0f.01.png',1),(56,'fasfasfas',0.02,73,'wrrw','/photos/9b771c92-4973-4c25-bfb6-809831dfaf68.retro-film-production-canva-photo.xc6cf828a.png',1),(57,'qwrw',0.01,73,'qwrw','/photos/4f718574-3002-43d7-9869-4dc0d4c99ece.retro-film-production-canva-photo.xc6cf828a.png',0);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_basket_item_entities`
--

DROP TABLE IF EXISTS `product_basket_item_entities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_basket_item_entities` (
  `product_entity_id` int NOT NULL,
  `basket_item_entities_id` int NOT NULL,
  UNIQUE KEY `UK_sbkec8gt8s7lkbrtbau5uypy9` (`basket_item_entities_id`),
  KEY `FK8rj3fvlhmxmctvgqci57ufnyt` (`product_entity_id`),
  CONSTRAINT `FK8rj3fvlhmxmctvgqci57ufnyt` FOREIGN KEY (`product_entity_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FKc0m0y7rusbxjh1q39ucccmpme` FOREIGN KEY (`basket_item_entities_id`) REFERENCES `basket_item` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_basket_item_entities`
--

LOCK TABLES `product_basket_item_entities` WRITE;
/*!40000 ALTER TABLE `product_basket_item_entities` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_basket_item_entities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_by_order`
--

DROP TABLE IF EXISTS `product_by_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_by_order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `count_products` int DEFAULT NULL,
  `id_order` int DEFAULT NULL,
  `id_product` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKly2trh6tve2bmruxjygwxk5m8` (`id_order`),
  KEY `FKdsw4d29radj7osawx02p7i9fi` (`id_product`),
  CONSTRAINT `FKdsw4d29radj7osawx02p7i9fi` FOREIGN KEY (`id_product`) REFERENCES `product` (`id`),
  CONSTRAINT `FKly2trh6tve2bmruxjygwxk5m8` FOREIGN KEY (`id_order`) REFERENCES `order_table` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_by_order`
--

LOCK TABLES `product_by_order` WRITE;
/*!40000 ALTER TABLE `product_by_order` DISABLE KEYS */;
INSERT INTO `product_by_order` VALUES (1,1,1,36),(2,1,1,42),(3,1,1,43),(4,1,2,36),(5,1,2,42),(6,1,2,43),(7,1,3,36),(8,1,3,42),(9,1,3,43),(10,1,3,44),(11,1,4,42),(12,1,4,44),(13,1,5,42),(14,1,5,44),(15,1,6,42),(16,1,7,42),(17,1,8,42),(18,1,8,41),(19,1,9,51),(20,4,10,41),(21,1,10,28),(22,155,10,55),(23,1,11,43),(24,3,12,46),(25,3,12,44),(26,1,13,41),(27,1,14,57),(28,1,14,41),(29,3,14,56);
/*!40000 ALTER TABLE `product_by_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_product_by_order_entities`
--

DROP TABLE IF EXISTS `product_product_by_order_entities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_product_by_order_entities` (
  `product_entity_id` int NOT NULL,
  `product_by_order_entities_id` int NOT NULL,
  UNIQUE KEY `UK_a1dqrxov82o5hheruo8hf5ycn` (`product_by_order_entities_id`),
  KEY `FK13qsavcp6vqe3jxwk6fnvjpvn` (`product_entity_id`),
  CONSTRAINT `FK13qsavcp6vqe3jxwk6fnvjpvn` FOREIGN KEY (`product_entity_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FKhqg9ru18uarmv6aeyacsw8t6b` FOREIGN KEY (`product_by_order_entities_id`) REFERENCES `product_by_order` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_product_by_order_entities`
--

LOCK TABLES `product_product_by_order_entities` WRITE;
/*!40000 ALTER TABLE `product_product_by_order_entities` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_product_by_order_entities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_set`
--

DROP TABLE IF EXISTS `product_set`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_set` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_product` int DEFAULT NULL,
  `id_order` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fr_order_product_set_idx` (`id_order`),
  KEY `fr_product_set_product_idx` (`id_product`),
  CONSTRAINT `fr_order_product_set` FOREIGN KEY (`id_order`) REFERENCES `order` (`id`),
  CONSTRAINT `fr_product_set_product` FOREIGN KEY (`id_product`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_set`
--

LOCK TABLES `product_set` WRITE;
/*!40000 ALTER TABLE `product_set` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_set` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `token`
--

DROP TABLE IF EXISTS `token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `token` (
  `id` int NOT NULL,
  `expired` bit(1) NOT NULL,
  `revoked` bit(1) NOT NULL,
  `token` varchar(255) DEFAULT NULL,
  `token_type` varchar(255) DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_pddrhgwxnms2aceeku9s2ewy5` (`token`),
  KEY `FKe32ek7ixanakfqsdaokm4q9y2` (`user_id`),
  CONSTRAINT `FKe32ek7ixanakfqsdaokm4q9y2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `token`
--

LOCK TABLES `token` WRITE;
/*!40000 ALTER TABLE `token` DISABLE KEYS */;
INSERT INTO `token` VALUES (1,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJxd2VydHl1aSIsImlhdCI6MTY5Mzc3OTcwOCwiZXhwIjoxNjkzNzgxNTA4fQ.I6Cu8Vc8kd1Gegziy8IXGAMl62CfnqYLvY0BgXAzCoY','BEARER',21),(2,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJxd2VydHl1aSIsImlhdCI6MTY5Mzc4NDIzOCwiZXhwIjoxNjkzNzg2MDM4fQ.dWuWb_gYg3A_uLxNoku3VXtwU_1BEeytTq-dVBU94FQ','BEARER',21),(3,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJxd2VydHl1aSIsImlhdCI6MTY5Mzc4NDM4NSwiZXhwIjoxNjkzNzg2MTg1fQ.K3bHVtpwNQR6YO-kVX7XAYIPJpPMLcHKoY-1u8z3Utw','BEARER',21),(52,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJxd2VydHl1aSIsImlhdCI6MTY5Mzc4NDkwMiwiZXhwIjoxNjkzNzg2NzAyfQ.ZpZa7J3r1nP4WvkQfYJwqQuKrFKpEZVMsqivD1jLUqg','BEARER',21),(102,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJxd2VydHl1aSIsImlhdCI6MTY5Mzc4NTA0NiwiZXhwIjoxNjkzNzg2ODQ2fQ.nppSidQzTw60T6mdxIBp3xtyCAgMY2nR9sAtudjXc1c','BEARER',21),(152,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJxd2VydHl1aSIsImlhdCI6MTY5Mzc4NTI2NywiZXhwIjoxNjkzNzg3MDY3fQ.wjaoTnEG7Z90kSudewCbalcxDCROxBQ7ZY0-X2Z2_iE','BEARER',21),(202,_binary '\0',_binary '\0','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3cnJ3d3J3cndyd3J3dyIsImlhdCI6MTY5Mzk0NzA5NCwiZXhwIjoxNjk0MDMzNDk0fQ.s7AkE2EW6cRm4ch6Fl1Gxvba25baK6NLM44iAPh60NA','BEARER',27),(252,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJxd2VydHl1aSIsImlhdCI6MTY5Mzk0OTE4NSwiZXhwIjoxNjk0MDM1NTg1fQ.08ek8deNhPwwTN5Onogxg1KBB0xcmSEkxuNyQ0AbJhM','BEARER',21),(302,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJxd2VydHl1aSIsImlhdCI6MTY5Mzk1MjU5OSwiZXhwIjoxNjk0MDM4OTk5fQ.C9bXd3n4kkpnBOsfoOaxpFPg4U3GNLCzSF6f-Ic151Q','BEARER',21),(303,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJxd2VydHl1aSIsImlhdCI6MTY5Mzk1MjU5OSwiZXhwIjoxNjk0NTU3Mzk5fQ.--8d0jFR06gUhSSlXinbwnnLwNYtGmTSMN2QWp4Nn4M','BEARER',21),(352,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJxd2VydHl1aSIsImlhdCI6MTY5Mzk1MzExOCwiZXhwIjoxNjk0MDM5NTE4fQ.AukN1zONxLGPcyZC2TjN0qsdOTKrDKhpxuMRnTCYm9w','BEARER',21),(353,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJxd2VydHl1aSIsImlhdCI6MTY5Mzk1MzExOCwiZXhwIjoxNjk0NTU3OTE4fQ.fqJMOgQ6qpxdEB5XV2Tu0eZaUA6cXmIytjChltrT4K4','BEARER',21),(402,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJxd2VydHl1aSIsImlhdCI6MTY5Mzk1NTE4NywiZXhwIjoxNjk0MDQxNTg3fQ.TeZT9A-FdSiffX5D6LjBefa9L70AUJ3tCjLUKIVT2Ms','BEARER',21),(403,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJxd2VydHl1aSIsImlhdCI6MTY5Mzk1NTE4NywiZXhwIjoxNjk0NTU5OTg3fQ.MSt9kwrShrHMw0AfI9yqSVbAoPAOOL4PfTv76a9M8TY','BEARER',21),(452,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJxd2VydHl1aSIsImlhdCI6MTY5NDAzNTMzMywiZXhwIjoxNjk0MTIxNzMzfQ.p8XmarZt9F5xugzsn2xb2978g0MKW2ENzpneOloH_Iw','BEARER',21),(453,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJxd2VydHl1aSIsImlhdCI6MTY5NDAzNTMzMywiZXhwIjoxNjk0NjQwMTMzfQ.Rl7j74NtI-aaFgPz0j1lc8-6m92naRJkQXUMB18soUg','BEARER',21),(454,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3cnJ3d3J3d3IiLCJpYXQiOjE2OTQwMzU0NzcsImV4cCI6MTY5NDEyMTg3N30.IxeDVmkL98fZ0zSPMmJDWGexZN5N-73lQQLi124iIqc','BEARER',28),(455,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3cnJ3d3J3d3IiLCJpYXQiOjE2OTQwMzU0NzcsImV4cCI6MTY5NDY0MDI3N30.RGDX5jehEBW6MONtv1_O9uKTQKg2QGTC9GkHZbR2IQc','BEARER',28),(502,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3cnJ3d3J3d3IiLCJpYXQiOjE2OTQwMzY3NzAsImV4cCI6MTY5NDEyMzE3MH0.pOsFj-xCjj_BbQaZ2GQIiYzPIA4kHMBsPa32i7sqPb0','BEARER',28),(552,_binary '\0',_binary '\0','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3cnJ3d3J3d3IiLCJpYXQiOjE2OTQwMzcxODUsImV4cCI6MTY5NDEyMzU4NX0.c2BkrOHoypYxwVtMQ11wdMlFwZqenG65hmITed3nM-8','BEARER',28),(553,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJxd2VydHl1aSIsImlhdCI6MTY5NDAzNzM3OCwiZXhwIjoxNjk0MTIzNzc4fQ.s6XieOexOEpnKVck9X-l7QkFcBFrBaoEDOKkr9vI5js','BEARER',21),(554,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJxd2VydHl1aSIsImlhdCI6MTY5NDAzNzM3OCwiZXhwIjoxNjk0NjQyMTc4fQ.zAsHAUwNr-gyUOZSYVHmnPXrMVC03qKN8jQQePfDA1A','BEARER',21),(602,_binary '\0',_binary '\0','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJxd2VydHl1aSIsImlhdCI6MTY5NDI0NTI2OSwiZXhwIjoxNjk0MzMxNjY5fQ.7py0yGJunk7hsw_wqnY5myIajkuEEyCSWpGteJ-esUs','BEARER',21),(603,_binary '\0',_binary '\0','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJxd2VydHl1aSIsImlhdCI6MTY5NDI0NTI2OSwiZXhwIjoxNjk0ODUwMDY5fQ.gumBxEMHNJ6q_bv96lrYDgAhuPWuwDvl1NGHbeKRD20','BEARER',21);
/*!40000 ALTER TABLE `token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `token_seq`
--

DROP TABLE IF EXISTS `token_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `token_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `token_seq`
--

LOCK TABLES `token_seq` WRITE;
/*!40000 ALTER TABLE `token_seq` DISABLE KEYS */;
INSERT INTO `token_seq` VALUES (701);
/*!40000 ALTER TABLE `token_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `login` varchar(45) NOT NULL,
  `pass` varchar(45) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `surname` varchar(45) DEFAULT NULL,
  `telephone` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `path` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (21,'qwertyui','qwertyui','qwertyui','qwertyui','+222222222221','qwertyui016@pre.c','/photos/fbd1971a-f0c0-4fd5-9630-4567307e1dde.PtGfIimPZKY.jpg'),(22,'9999999www','99999999','www','www','+380999999999','qwertyui6@pre.cw','/photos/eb7269a6-f77f-4e8c-bada-94ebce70403e.rBEeJGKxjXyAfuaLAALDc3CS9iQ25.jpeg'),(23,'qqqqqqq','qqqqqqqq','qwewqwqwq','qqqqqqq','+3809999999','20-dk@ukr.net','/photos/01.png'),(24,'weewewew','weewewew','weewewew','weewewew','+2424422442','wrwrwrw@wrwr.com','/photos/01.png'),(25,'wrrwwrwrwrwrwr','wwwwwwwwwwwwwwwww','wwwwwww','wwwwwwww','+23244242242424','wrwrwrw@wrwr.comw','/photos/01.png'),(26,'wrrwwrwrwrwrwrw','wrrwwrwrwrwrwrw','wrrwwrwrwrwrwrw','wrrwwrwrwrwrwrw','+2324424224242','wrwrwrww@wrwr.comw','/photos/2cc2980b-03f3-4292-b9bd-6eea0e674302.PtGfIimPZKY.jpg'),(27,'wrrwwrwrwrwrww','wrrwwrwrwrwr','wrrwwrwrwrwrwrww','wrrwwrwrwrwrwrww','+2424224422222','wrrwwrwrwrwrwrw@w.com','/photos/b482d152-0d82-4fd1-aa7d-67a37da66260.PtGfIimPZKY.jpg'),(28,'wrrwwrwwr','wrrwwrwwr','wrrwwrwwr','wrrwwrwwr','+2424252242442','wrrwwrwwr@wf.com','/photos/01.png');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `roles` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK859n2jvi8ivhui0rl0esws6o` (`user_id`),
  CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=117 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (19,26,'MODERATOR'),(28,21,'ADMIN'),(88,24,'MODERATOR'),(89,25,'MODERATOR'),(111,22,'USER'),(112,23,'MODERATOR'),(115,27,'ADMIN'),(116,28,'USER');
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-10-04  3:38:10
