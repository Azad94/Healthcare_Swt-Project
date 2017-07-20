-- MySQL dump 10.16  Distrib 10.1.24-MariaDB, for Linux (x86_64)
--
-- Host: h2627939.stratoserver.net    Database: healthcare
-- ------------------------------------------------------
-- Server version	10.1.24-MariaDB-1~xenial

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `abstract_sub_task`
--

DROP TABLE IF EXISTS `abstract_sub_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `abstract_sub_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `beacon`
--

DROP TABLE IF EXISTS `beacon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `beacon` (
  `uuid` varchar(33) NOT NULL,
  `major` int(11) DEFAULT NULL,
  `minor` int(11) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `beacon_object`
--

DROP TABLE IF EXISTS `beacon_object`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `beacon_object` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `beacon_object_type` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `state` int(11) DEFAULT 1,
  `beacon_uuid` varchar(33) NOT NULL DEFAULT NULL,
  `location_id` bigint(20) NOT NULL DEFAULT NULL,
  `picture_of_object_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjqbijibyarj7uv9uua7bqu2rn` (`beacon_uuid`),
  KEY `FK9xts58oihpnx9cpii3jrrtdag` (`location_id`),
  KEY `FKbe6y81m5117wrfxr6hogr3pdd` (`picture_of_object_id`),
  CONSTRAINT `beacon_object_to_location` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`),
  CONSTRAINT `beacon_object_to_picture_of_object` FOREIGN KEY (`picture_of_object_id`) REFERENCES `picture_of_object` (`id`),
  CONSTRAINT `beacon_object_to_beacon` FOREIGN KEY (`beacon_uuid`) REFERENCES `beacon` (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `beacon_object`
--



--
-- Table structure for table `cleaning_task`
--

DROP TABLE IF EXISTS `cleaning_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cleaning_task` (
  `id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `cleaning_task_to_task` FOREIGN KEY (`id`) REFERENCES `task` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `location`
--

DROP TABLE IF EXISTS `location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `location` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `building` int(11) NOT NULL,
  `floor` int(11) NOT NULL,
  `room` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `maintainance_task`
--

DROP TABLE IF EXISTS `maintainance_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `maintainance_task` (
  `repeat_task_in_day` int(11) DEFAULT NULL,
  `id` bigint(20) NOT NULL,
  `picture_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKo3fbiamhvs4lrr0emam839f7j` (`picture_id`),
  CONSTRAINT `maintainance_task_to_task` FOREIGN KEY (`id`) REFERENCES `task` (`id`),
  CONSTRAINT `maintainance_task_to_picture_of_object` FOREIGN KEY (`picture_id`) REFERENCES `picture_of_object` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `maintainance_task_sub_tasks`
--

DROP TABLE IF EXISTS `maintainance_task_sub_tasks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `maintainance_task_sub_tasks` (
  `maintainance_task_id` bigint(20) NOT NULL,
  `sub_tasks_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_dn0yg4y7xekr3quoxc3ap69mh` (`sub_tasks_id`),
  KEY `FKjc48bk0i6xru2008kwwmptyur` (`maintainance_task_id`),
  CONSTRAINT `maintainance_task_sub_tasks_to_maintainance_task` FOREIGN KEY (`maintainance_task_id`) REFERENCES `maintainance_task` (`id`),
  CONSTRAINT `maintainance_task_sub_tasks_to_sub_task` FOREIGN KEY (`sub_tasks_id`) REFERENCES `abstract_sub_task` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `picture_of_object`
--

DROP TABLE IF EXISTS `picture_of_object`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `picture_of_object` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `picture` mediumblob,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `sub_task_checkbox`
--

DROP TABLE IF EXISTS `sub_task_checkbox`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sub_task_checkbox` (
  `value` bit(1) NOT NULL,
  `id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `sub_task_checkbox_to_abstract_sub_task` FOREIGN KEY (`id`) REFERENCES `abstract_sub_task` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `sub_task_image`
--

DROP TABLE IF EXISTS `sub_task_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sub_task_image` (
  `picture` mediumblob,
  `id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `sub_task_image_to_abstract_sub_task` FOREIGN KEY (`id`) REFERENCES `abstract_sub_task` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `sub_task_slider`
--

DROP TABLE IF EXISTS `sub_task_slider`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sub_task_slider` (
  `value` int(11) NOT NULL,
  `id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `sub_task_slider_to_abstract_sub_task` FOREIGN KEY (`id`) REFERENCES `abstract_sub_task` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `task`
--

DROP TABLE IF EXISTS `task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `accepted_time` datetime DEFAULT NULL,
  `closed_time` datetime DEFAULT NULL,
  `creation_time` datetime NOT NULL,
  `description` varchar(255) NOT NULL,
  `last_edited` datetime NOT NULL,
  `level` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `beacon_object_id` bigint(20) NOT NULL,
  `creator_id` bigint(20) NOT NULL,
  `editor_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6iuwjq9weqnme5lil8xo9l531` (`beacon_object_id`),
  KEY `FKqc1galw66ryn480v0lygu3n4c` (`creator_id`),
  KEY `FKk3e76d3ou73nevqstaw1nfib1` (`editor_id`),
  KEY `FKqyr43lnks4pso90vm1d16w4ry` (`role_id`),
  CONSTRAINT `task_to_beacon_object_id` FOREIGN KEY (`beacon_object_id`) REFERENCES `beacon_object` (`id`),
  CONSTRAINT `task_to_user_editor` FOREIGN KEY (`editor_id`) REFERENCES `user` (`id`),
  CONSTRAINT `task_to_user_creator` FOREIGN KEY (`creator_id`) REFERENCES `user` (`id`),
  CONSTRAINT `task_to_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `transport_task`
--

DROP TABLE IF EXISTS `transport_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transport_task` (
  `id` bigint(20) NOT NULL,
  `target_location_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3f0au1wndq66fuw7v5wfbfjsg` (`target_location_id`),
  CONSTRAINT `transport_task_to_location` FOREIGN KEY (`target_location_id`) REFERENCES `location` (`id`),
  CONSTRAINT `transport_task_to_task` FOREIGN KEY (`id`) REFERENCES `task` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `last_update` datetime NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKn82ha3ccdebhokx3a8fgdqeyy` (`role_id`),
  CONSTRAINT `user_to_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;


-- Start daten um admin zu setzen
LOCK TABLES `role` WRITE;
INSERT INTO `role` (`id`,`name`) VALUES (1,'Administrator');
UNLOCK TABLES;
LOCK TABLES `user` WRITE;
INSERT INTO `user` (`name`,`password`,`email`,`last_update`,`role_id`) VALUES ('user','123456',' ',CURRENT_TIMESTAMP,2);
UNLOCK TABLES;

--Testdaten
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;
LOCK TABLES `role` WRITE;
INSERT INTO `role` (`id`,`name`) VALUES (2,'User');
INSERT INTO `role` (`id`,`name`) VALUES (3,'Schieber');
INSERT INTO `role` (`id`,`name`) VALUES (4,'Techniker');
UNLOCK TABLES;
LOCK TABLES `user` WRITE;
INSERT INTO `user` (`name`,`password`,`email`,`last_update`,`role_id`) VALUES ('w','1',' ',CURRENT_TIMESTAMP,3);
INSERT INTO `user` (`name`,`password`,`email`,`last_update`,`role_id`) VALUES ('t','1',' ',CURRENT_TIMESTAMP,4);
UNLOCK TABLES;
LOCK TABLES `location` WRITE;
INSERT INTO `location` (`id`,`building`,`floor`,`room`) VALUES (1,1,1,1);
INSERT INTO `location` (`id`,`building`,`floor`,`room`) VALUES (2,1,1,2);
INSERT INTO `location` (`id`,`building`,`floor`,`room`) VALUES (3,1,2,1);
INSERT INTO `location` (`id`,`building`,`floor`,`room`) VALUES (4,1,2,2);
INSERT INTO `location` (`id`,`building`,`floor`,`room`) VALUES (5,2,1,1);
UNLOCK TABLES;
LOCK TABLES `beacon` WRITE;
INSERT INTO `beacon` (`uuid`,`major`,`minor`) VALUES ("00000001-healthcarew123456789101",1,1);
INSERT INTO `beacon` (`uuid`,`major`,`minor`) VALUES ("00000002-healthcarew123456789101",1,2);
INSERT INTO `beacon` (`uuid`,`major`,`minor`) VALUES ("00000003-healthcarew123456789101",2,1);
INSERT INTO `beacon` (`uuid`,`major`,`minor`) VALUES ("00000004-healthcarew123456789101",2,2);
INSERT INTO `beacon` (`uuid`,`major`,`minor`) VALUES ("00000005-healthcarew123456789101",3,2);
UNLOCK TABLES;
LOCK TABLES `beacon_object` WRITE;
INSERT INTO `beacon_object` (`id`,`beacon_object_type`,`name`,`state`,`beacon_uuid`,`location_id`) VALUES (1,"Standardbett","beacon",1,"00000005-healthcarew123456789101",1);
INSERT INTO `beacon_object` (`id`,`beacon_object_type`,`name`,`state`,`beacon_uuid`,`location_id`) VALUES (2,"Kinderbett","beacon",1,"00000001-healthcarew123456789101",2);
INSERT INTO `beacon_object` (`id`,`beacon_object_type`,`name`,`state`,`beacon_uuid`,`location_id`) VALUES (3,"Brandschutztür","beacon",1,"00000002-healthcarew123456789101",3);
UNLOCK TABLES;
LOCK TABLES `task` WRITE;
INSERT INTO `task` (`id`,`creation_time`,`description`,`last_edited`,`level`,`state`,`beacon_object_id`,`creator_id`,`role_id`,`name`) VALUES (1, CURRENT_TIMESTAMP,"eine wichtige Aufgabe",CURRENT_TIMESTAMP,1,1,1,2,1,"Transi");
INSERT INTO `task` (`id`,`creation_time`,`description`,`last_edited`,`level`,`state`,`beacon_object_id`,`creator_id`,`role_id`,`name`) VALUES (2, CURRENT_TIMESTAMP,"eine wichtige Aufgabe",CURRENT_TIMESTAMP,1,1,2,2,1,"Maini");
INSERT INTO `task` (`id`,`creation_time`,`description`,`last_edited`,`level`,`state`,`beacon_object_id`,`creator_id`,`role_id`,`name`) VALUES (3, CURRENT_TIMESTAMP,"eine wichtige Aufgabe",CURRENT_TIMESTAMP,1,1,3,2,1,"Cleani");
UNLOCK TABLES;
LOCK TABLES `transport_task` WRITE;
INSERT INTO `transport_task` (`id`,`target_location_id`) VALUES (1,5);
UNLOCK TABLES;
LOCK TABLES `cleaning_task` WRITE;
INSERT INTO `cleaning_task` (`id`) VALUES (3);
UNLOCK TABLES;
LOCK TABLES `maintainance_task` WRITE;
INSERT INTO `maintainance_task` (`repeat_task_in_days`,`id`) VALUES (1,2);
UNLOCK TABLES;



/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
