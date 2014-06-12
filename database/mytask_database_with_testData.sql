CREATE DATABASE  IF NOT EXISTS `mytask` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `mytask`;
-- MySQL dump 10.13  Distrib 5.6.11, for osx10.6 (i386)
--
-- Host: 101.251.195.186    Database: mytask
-- ------------------------------------------------------
-- Server version	5.5.36-log

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
-- Table structure for table `Product`
--

DROP TABLE IF EXISTS `Product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `maxAmt` decimal(19,2) DEFAULT NULL,
  `maxInterest` decimal(19,2) DEFAULT NULL,
  `maxPeriod` int(11) NOT NULL,
  `minAmt` decimal(19,2) DEFAULT NULL,
  `minInterest` decimal(19,2) DEFAULT NULL,
  `minPeriod` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `platformId` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=313 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Product`
--

LOCK TABLES `Product` WRITE;
/*!40000 ALTER TABLE `Product` DISABLE KEYS */;
/*!40000 ALTER TABLE `Product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attachment`
--

DROP TABLE IF EXISTS `attachment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attachment` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `url` varchar(60) DEFAULT NULL,
  `name` varchar(60) DEFAULT NULL,
  `own_id` int(10) DEFAULT NULL,
  `project_id` int(10) DEFAULT NULL,
  `fileType` varchar(1) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `contentType` varchar(100) DEFAULT NULL,
  `team_id` int(10) DEFAULT NULL,
  `width` int(5) DEFAULT '0',
  `height` int(5) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_attachment_project1_idx` (`project_id`),
  KEY `index3` (`url`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attachment`
--

LOCK TABLES `attachment` WRITE;
/*!40000 ALTER TABLE `attachment` DISABLE KEYS */;
/*!40000 ALTER TABLE `attachment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attachment_comment_rel`
--

DROP TABLE IF EXISTS `attachment_comment_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attachment_comment_rel` (
  `attachment_id` int(10) NOT NULL DEFAULT '0',
  `comment_id` int(10) NOT NULL DEFAULT '0',
  PRIMARY KEY (`attachment_id`,`comment_id`),
  KEY `fk_attachment_comment_rel_comment1_idx` (`comment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attachment_comment_rel`
--

LOCK TABLES `attachment_comment_rel` WRITE;
/*!40000 ALTER TABLE `attachment_comment_rel` DISABLE KEYS */;
/*!40000 ALTER TABLE `attachment_comment_rel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attachment_message_rel`
--

DROP TABLE IF EXISTS `attachment_message_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attachment_message_rel` (
  `attachment_id` int(10) NOT NULL DEFAULT '0',
  `message_id` int(10) NOT NULL DEFAULT '0',
  PRIMARY KEY (`attachment_id`,`message_id`),
  KEY `fk_attachment_message_rel_message1_idx` (`message_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attachment_message_rel`
--

LOCK TABLES `attachment_message_rel` WRITE;
/*!40000 ALTER TABLE `attachment_message_rel` DISABLE KEYS */;
/*!40000 ALTER TABLE `attachment_message_rel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attachment_reference`
--

DROP TABLE IF EXISTS `attachment_reference`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attachment_reference` (
  `url` varchar(100) NOT NULL,
  `target_type` varchar(20) DEFAULT NULL,
  `target_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`url`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attachment_reference`
--

LOCK TABLES `attachment_reference` WRITE;
/*!40000 ALTER TABLE `attachment_reference` DISABLE KEYS */;
INSERT INTO `attachment_reference` VALUES ('group1/M00/00/02/ZfvDu1OZQ1GAVe0IAABRbDFCTx8799.png','comment',31);
/*!40000 ALTER TABLE `attachment_reference` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attachment_todocomment_rel`
--

DROP TABLE IF EXISTS `attachment_todocomment_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attachment_todocomment_rel` (
  `attachment_id` int(10) NOT NULL DEFAULT '0',
  `todoComment_id` int(10) NOT NULL DEFAULT '0',
  PRIMARY KEY (`attachment_id`,`todoComment_id`),
  KEY `fk_attachment_todocomment_rel_todocomment1_idx` (`todoComment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attachment_todocomment_rel`
--

LOCK TABLES `attachment_todocomment_rel` WRITE;
/*!40000 ALTER TABLE `attachment_todocomment_rel` DISABLE KEYS */;
/*!40000 ALTER TABLE `attachment_todocomment_rel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attachment_todolistcomment_rel`
--

DROP TABLE IF EXISTS `attachment_todolistcomment_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attachment_todolistcomment_rel` (
  `attachment_id` int(10) NOT NULL DEFAULT '0',
  `todolistcomment_id` int(10) NOT NULL DEFAULT '0',
  PRIMARY KEY (`attachment_id`,`todolistcomment_id`),
  KEY `fk_attachment_todolistcomment_rel_todolistcomment1_idx` (`todolistcomment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attachment_todolistcomment_rel`
--

LOCK TABLES `attachment_todolistcomment_rel` WRITE;
/*!40000 ALTER TABLE `attachment_todolistcomment_rel` DISABLE KEYS */;
/*!40000 ALTER TABLE `attachment_todolistcomment_rel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `calendar`
--

DROP TABLE IF EXISTS `calendar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `calendar` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(60) DEFAULT NULL,
  `description` varchar(60) DEFAULT NULL,
  `color` varchar(10) DEFAULT NULL,
  `creator_id` int(10) DEFAULT NULL,
  `team_id` int(10) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `modify_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_calendar_user1_idx` (`creator_id`),
  KEY `fk_calendar_team1_idx` (`team_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `calendar`
--

LOCK TABLES `calendar` WRITE;
/*!40000 ALTER TABLE `calendar` DISABLE KEYS */;
INSERT INTO `calendar` VALUES (2,'test',NULL,'46647c',32,28,'2014-05-02 04:49:24',NULL);
/*!40000 ALTER TABLE `calendar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `calendar_user_rel`
--

DROP TABLE IF EXISTS `calendar_user_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `calendar_user_rel` (
  `calendar_id` int(10) NOT NULL DEFAULT '0',
  `user_id` int(10) NOT NULL DEFAULT '0',
  PRIMARY KEY (`calendar_id`,`user_id`),
  KEY `fk_calendar_user_rel_user1_idx` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `calendar_user_rel`
--

LOCK TABLES `calendar_user_rel` WRITE;
/*!40000 ALTER TABLE `calendar_user_rel` DISABLE KEYS */;
INSERT INTO `calendar_user_rel` VALUES (2,32);
/*!40000 ALTER TABLE `calendar_user_rel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `content` text,
  `creator_id` int(10) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `team_id` int(10) DEFAULT NULL,
  `project_id` int(10) DEFAULT NULL,
  `commentable_type` varchar(10) DEFAULT NULL,
  `commentable_id` int(10) DEFAULT NULL,
  `modify_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `author` (`creator_id`),
  KEY `index4` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (2,'sdfsdf',32,'2014-06-01 01:44:13',28,2,'todo',41,NULL),(3,'sdfsdf',32,'2014-06-01 01:44:25',28,2,'todo',41,NULL),(4,'sdfsdf',32,'2014-06-01 01:45:35',28,2,'todo',41,NULL),(5,'sfsdfsdf',32,'2014-06-01 01:51:30',28,2,'todo',41,NULL),(6,'sdfsdf',32,'2014-06-01 02:20:45',28,2,'todo',41,NULL),(7,'test',32,'2014-06-01 02:23:56',28,2,'todo',41,NULL),(8,'testtt',32,'2014-06-01 02:25:26',28,2,'todo',41,NULL),(9,'sdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjssdjfjsdjfjs',32,'2014-06-01 11:39:19',28,2,'todo',41,NULL),(10,'sdfsdf',32,'2014-06-02 03:26:34',28,2,'todo',41,NULL),(11,'同时',32,'2014-06-02 03:35:39',28,2,'todo',41,NULL),(12,'这个太无聊了',32,'2014-06-02 03:36:08',28,2,'todo',41,NULL),(13,'Testtt',32,'2014-06-02 03:38:30',28,2,'todo',41,NULL),(14,'Sssss',32,'2014-06-02 03:39:20',28,2,'todo',41,NULL),(15,'testtttt',32,'2014-06-02 08:58:01',28,2,'message',15,NULL),(16,'Sdfsdfsdf',32,'2014-06-02 08:58:19',28,2,'message',15,NULL),(17,'Sdfsdfsdfsdf',32,'2014-06-02 08:58:24',28,2,'message',15,NULL),(18,'Sdfsdf',32,'2014-06-02 08:58:28',28,2,'message',15,NULL),(19,'Sssss',32,'2014-06-02 10:25:36',28,2,'message',14,NULL),(20,'Sdfsdfsdf',32,'2014-06-02 10:25:43',28,2,'message',14,NULL),(21,'Sdfsdf',32,'2014-06-02 10:25:48',28,2,'message',14,NULL),(22,'<strong>sdf</strong>',32,'2014-06-02 14:31:33',28,2,'todo',62,NULL),(23,'<b>sdfsdfsdf<br><i>sdfsdf</i></b>',32,'2014-06-02 14:33:31',28,2,'todo',62,NULL),(24,'22222',32,'2014-06-03 05:15:19',28,2,'message',21,NULL),(25,'222',32,'2014-06-03 05:25:26',28,2,'message',21,NULL),(26,'2222',32,'2014-06-03 05:26:20',28,2,'message',21,NULL),(27,'2222',32,'2014-06-03 05:27:49',28,2,'message',21,NULL),(28,'222',32,'2014-06-03 05:27:57',28,2,'message',21,NULL),(29,'2',32,'2014-06-03 05:28:56',28,2,'todo',62,NULL),(30,'',32,'2014-06-12 05:29:41',28,2,'todo',75,NULL),(31,'sdfsdf',32,'2014-06-12 06:06:52',28,2,'todo',75,NULL);
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event`
--

DROP TABLE IF EXISTS `event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `event` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(60) DEFAULT NULL,
  `description` varchar(60) DEFAULT NULL,
  `team_id` int(10) DEFAULT NULL,
  `creator_id` int(10) DEFAULT NULL,
  `target_type` varchar(15) DEFAULT NULL,
  `target_id` int(10) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `start_time` time DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `modify_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_event_calendar1_idx` (`target_id`),
  KEY `fk_event_project1_idx` (`target_type`),
  KEY `index3` (`start_date`),
  KEY `index4` (`end_date`),
  KEY `fk_event_user1_idx` (`creator_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event`
--

LOCK TABLES `event` WRITE;
/*!40000 ALTER TABLE `event` DISABLE KEYS */;
/*!40000 ALTER TABLE `event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inviteproject`
--

DROP TABLE IF EXISTS `inviteproject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `inviteproject` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `project_id` int(10) NOT NULL,
  `invite_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_inviteproject_inviteuser1_idx` (`invite_id`),
  KEY `fk_inviteproject_project1_idx` (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inviteproject`
--

LOCK TABLES `inviteproject` WRITE;
/*!40000 ALTER TABLE `inviteproject` DISABLE KEYS */;
/*!40000 ALTER TABLE `inviteproject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inviteuser`
--

DROP TABLE IF EXISTS `inviteuser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `inviteuser` (
  `id` varchar(60) NOT NULL,
  `email` varchar(60) DEFAULT NULL,
  `invite_id` int(10) DEFAULT NULL,
  `team_id` int(10) DEFAULT NULL,
  `privilege` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_inviteuser_team1_idx` (`team_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inviteuser`
--

LOCK TABLES `inviteuser` WRITE;
/*!40000 ALTER TABLE `inviteuser` DISABLE KEYS */;
/*!40000 ALTER TABLE `inviteuser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `subject` varchar(90) NOT NULL,
  `content` text,
  `creator_id` int(10) NOT NULL,
  `project_id` int(10) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `team_id` int(10) DEFAULT NULL,
  `comment_num` int(5) DEFAULT '0',
  `summary` varchar(300) DEFAULT NULL,
  `modify_time` timestamp NULL DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `author` (`creator_id`),
  KEY `fk_message_project1_idx` (`project_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES (22,'','',33,6,'2014-06-11 15:13:17',NULL,0,'',NULL,0);
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message_data`
--

DROP TABLE IF EXISTS `message_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message_data` (
  `project_id` int(10) NOT NULL DEFAULT '0',
  `add_date` date NOT NULL DEFAULT '0000-00-00',
  `total_number` int(5) DEFAULT NULL,
  PRIMARY KEY (`project_id`,`add_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message_data`
--

LOCK TABLES `message_data` WRITE;
/*!40000 ALTER TABLE `message_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `message_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message_user_data`
--

DROP TABLE IF EXISTS `message_user_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message_user_data` (
  `project_id` int(10) NOT NULL DEFAULT '0',
  `user_id` int(10) NOT NULL DEFAULT '0',
  `add_date` date NOT NULL DEFAULT '0000-00-00',
  `total_number` int(5) DEFAULT NULL,
  PRIMARY KEY (`project_id`,`user_id`,`add_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message_user_data`
--

LOCK TABLES `message_user_data` WRITE;
/*!40000 ALTER TABLE `message_user_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `message_user_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message_week_data`
--

DROP TABLE IF EXISTS `message_week_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message_week_data` (
  `project_id` int(10) NOT NULL DEFAULT '0',
  `add_weekday` int(1) NOT NULL DEFAULT '0',
  `total_number` int(5) DEFAULT NULL,
  PRIMARY KEY (`project_id`,`add_weekday`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message_week_data`
--

LOCK TABLES `message_week_data` WRITE;
/*!40000 ALTER TABLE `message_week_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `message_week_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `operation`
--

DROP TABLE IF EXISTS `operation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `operation` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `own_id` int(10) DEFAULT NULL,
  `createTime` timestamp NULL DEFAULT NULL,
  `operation_type` int(2) DEFAULT NULL,
  `target_type` int(2) DEFAULT NULL,
  `target_id` int(10) DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `digest` text,
  `team_id` int(10) DEFAULT NULL,
  `project_id` int(10) DEFAULT NULL,
  `url` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=96 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `operation`
--

LOCK TABLES `operation` WRITE;
/*!40000 ALTER TABLE `operation` DISABLE KEYS */;
INSERT INTO `operation` VALUES (27,32,'2014-01-20 01:19:09',0,0,2,'时间管理',NULL,28,2,'/project/2'),(28,32,'2014-01-20 01:23:41',NULL,3,4,'Jetty学习','<span class=\"in_timeline\">\n创建 任务列表\n<span class=\"suffix\">:</span>\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/4\">\nJetty学习\n</a>\n</span>\n',28,2,NULL),(29,32,'2014-01-20 01:34:05',NULL,4,7,NULL,'<span class=\"in_timeline\">\n创建 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/4/todoitem/7\">\napp部署\n</a>\n</span>\n',28,2,NULL),(30,32,'2014-01-20 01:34:20',NULL,4,7,NULL,'<span class=\"in_timeline\">\n分配 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/4/todoitem/7\">\napp部署\n</a>\n给\n\n沈淼奇\n\n\n截至日期\n<span class=\"suffix\">:</span>\n2014-01-20\n\n</span>\n',28,2,NULL),(31,32,'2014-01-20 01:36:42',NULL,3,5,'P2P网站','<span class=\"in_timeline\">\n创建 任务列表\n<span class=\"suffix\">:</span>\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/5\">\nP2P网站\n</a>\n</span>\n',28,2,NULL),(32,32,'2014-01-20 05:47:11',NULL,3,6,'MyTask完善','<span class=\"in_timeline\">\n创建 任务列表\n<span class=\"suffix\">:</span>\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/6\">\nMyTask完善\n</a>\n</span>\n',28,2,NULL),(33,32,'2014-01-20 05:47:30',NULL,4,8,NULL,'<span class=\"in_timeline\">\n创建 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/6/todoitem/8\">\n任务修改历史\n</a>\n</span>\n',28,2,NULL),(34,32,'2014-01-20 05:47:34',NULL,4,8,NULL,'<span class=\"in_timeline\">\n分配 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/6/todoitem/8\">\n任务修改历史\n</a>\n给\n\n沈淼奇\n\n\n截至日期\n<span class=\"suffix\">:</span>\n2014-01-20\n\n</span>\n',28,2,NULL),(35,32,'2014-01-20 05:47:56',NULL,4,9,NULL,'<span class=\"in_timeline\">\n创建 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/5/todoitem/9\">\n个人信用中心\n</a>\n</span>\n',28,2,NULL),(36,32,'2014-01-20 05:48:04',NULL,4,9,NULL,'<span class=\"in_timeline\">\n分配 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/5/todoitem/9\">\n个人信用中心\n</a>\n给\n\n\n截至日期\n<span class=\"suffix\">:</span>\n2014-01-20\n\n</span>\n',28,2,NULL),(37,32,'2014-01-20 05:48:38',NULL,4,9,NULL,'<span class=\"in_timeline\">\n分配 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/5/todoitem/9\">\n个人信用中心\n</a>\n给\n\n沈淼奇\n\n\n截至日期\n<span class=\"suffix\">:</span>\n2014-01-21\n\n</span>\n',28,2,NULL),(38,32,'2014-01-20 05:50:21',NULL,4,10,NULL,'<span class=\"in_timeline\">\n创建 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/6/todoitem/10\">\n日历订阅\n</a>\n</span>\n',28,2,NULL),(39,32,'2014-01-20 08:25:13',9,4,10,'日历订阅','<span class=\"in_timeline\">\n完成 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/6/todoitem/10\">\n日历订阅\n</a>\n</span>\n',28,2,NULL),(40,32,'2014-01-20 08:25:15',10,4,10,'日历订阅','<span class=\"in_timeline\">\n取消完成 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/6/todoitem/10\">\n日历订阅\n</a>\n</span>\n',28,2,NULL),(41,32,'2014-01-20 08:25:17',9,4,8,'任务修改历史','<span class=\"in_timeline\">\n完成 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/6/todoitem/8\">\n任务修改历史\n</a>\n</span>\n',28,2,NULL),(42,32,'2014-01-20 14:44:33',9,4,10,'日历订阅','<span class=\"in_timeline\">\n完成 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/6/todoitem/10\">\n日历订阅\n</a>\n</span>\n',28,2,NULL),(43,32,'2014-01-21 03:11:34',NULL,4,11,NULL,'<span class=\"in_timeline\">\n创建 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/4/todoitem/11\">\nWebAppContext解析\n</a>\n</span>\n',28,2,NULL),(44,32,'2014-01-21 03:11:39',9,4,7,'app部署','<span class=\"in_timeline\">\n完成 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/4/todoitem/7\">\napp部署\n</a>\n</span>\n',28,2,NULL),(45,32,'2014-01-22 10:04:10',9,4,9,'个人信用中心','<span class=\"in_timeline\">\n完成 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/5/todoitem/9\">\n个人信用中心\n</a>\n</span>\n',28,2,NULL),(46,32,'2014-02-18 02:00:00',NULL,3,7,'数据抓取','<span class=\"in_timeline\">\n创建 任务列表\n<span class=\"suffix\">:</span>\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/7\">\n数据抓取\n</a>\n</span>\n',28,2,NULL),(47,32,'2014-02-18 02:00:12',NULL,4,12,NULL,'<span class=\"in_timeline\">\n创建 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/7/todoitem/12\">\n货币基金\n</a>\n</span>\n',28,2,NULL),(48,32,'2014-02-18 02:00:18',NULL,4,12,NULL,'<span class=\"in_timeline\">\n分配 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/7/todoitem/12\">\n货币基金\n</a>\n给\n\n\n截至日期\n<span class=\"suffix\">:</span>\n2014-02-18\n\n</span>\n',28,2,NULL),(49,32,'2014-02-18 08:00:56',NULL,4,13,NULL,'<span class=\"in_timeline\">\n创建 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/7/todoitem/13\">\n理财产品\n</a>\n</span>\n',28,2,NULL),(50,32,'2014-02-18 08:01:00',9,4,12,'货币基金','<span class=\"in_timeline\">\n完成 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/7/todoitem/12\">\n货币基金\n</a>\n</span>\n',28,2,NULL),(51,32,'2014-02-18 08:01:26',NULL,3,8,'Akka Documen阅读','<span class=\"in_timeline\">\n创建 任务列表\n<span class=\"suffix\">:</span>\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/8\">\nAkka Documen阅读\n</a>\n</span>\n',28,2,NULL),(52,32,'2014-02-18 08:01:53',NULL,4,14,NULL,'<span class=\"in_timeline\">\n创建 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/8/todoitem/14\">\nIntroduction\n</a>\n</span>\n',28,2,NULL),(53,32,'2014-03-11 00:43:01',9,4,13,'理财产品','<span class=\"in_timeline\">\n完成 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/7/todoitem/13\">\n理财产品\n</a>\n</span>\n',28,2,NULL),(54,32,'2014-03-11 00:43:37',NULL,4,15,NULL,'<span class=\"in_timeline\">\n创建 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/7/todoitem/15\">\n百度地址获取周边小区API调用\n</a>\n</span>\n',28,2,NULL),(55,32,'2014-03-11 00:43:42',NULL,4,15,NULL,'<span class=\"in_timeline\">\n分配 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/7/todoitem/15\">\n百度地址获取周边小区API调用\n</a>\n给\n\n\n截至日期\n<span class=\"suffix\">:</span>\n2014-03-11\n\n</span>\n',28,2,NULL),(56,32,'2014-03-11 00:44:01',NULL,3,9,'学习','<span class=\"in_timeline\">\n创建 任务列表\n<span class=\"suffix\">:</span>\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/9\">\n学习\n</a>\n</span>\n',28,2,NULL),(57,32,'2014-03-11 00:44:24',NULL,4,16,NULL,'<span class=\"in_timeline\">\n创建 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/9/todoitem/16\">\nfinagle IDE搭建\n</a>\n</span>\n',28,2,NULL),(58,32,'2014-03-11 00:44:29',NULL,4,16,NULL,'<span class=\"in_timeline\">\n分配 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/9/todoitem/16\">\nfinagle IDE搭建\n</a>\n给\n\n\n截至日期\n<span class=\"suffix\">:</span>\n2014-03-11\n\n</span>\n',28,2,NULL),(59,32,'2014-03-11 00:44:37',9,4,14,'Introduction','<span class=\"in_timeline\">\n完成 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/8/todoitem/14\">\nIntroduction\n</a>\n</span>\n',28,2,NULL),(60,32,'2014-03-13 08:35:29',9,4,15,'百度地址获取周边小区API调用','<span class=\"in_timeline\">\n完成 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/7/todoitem/15\">\n百度地址获取周边小区API调用\n</a>\n</span>\n',28,2,NULL),(61,32,'2014-03-13 08:35:44',NULL,4,17,NULL,'<span class=\"in_timeline\">\n创建 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/7/todoitem/17\">\nslick 动态SQL\n</a>\n</span>\n',28,2,NULL),(62,32,'2014-03-13 08:35:51',9,4,16,'finagle IDE搭建','<span class=\"in_timeline\">\n完成 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/9/todoitem/16\">\nfinagle IDE搭建\n</a>\n</span>\n',28,2,NULL),(63,32,'2014-03-19 02:50:11',0,0,3,'111',NULL,28,3,'/project/3'),(64,32,'2014-03-19 02:50:16',0,0,4,'12',NULL,28,4,'/project/4'),(65,32,'2014-03-19 02:50:23',0,0,5,'22',NULL,28,5,'/project/5'),(66,32,'2014-05-12 02:32:03',9,4,11,'WebAppContext解析','<span class=\"in_timeline\">\n完成 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/4/todoitem/11\">\nWebAppContext解析\n</a>\n</span>\n',28,2,NULL),(67,32,'2014-05-12 02:32:08',9,4,17,'slick 动态SQL','<span class=\"in_timeline\">\n完成 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/7/todoitem/17\">\nslick 动态SQL\n</a>\n</span>\n',28,2,NULL),(68,32,'2014-05-12 02:32:19',NULL,3,10,'理财网站','<span class=\"in_timeline\">\n创建 任务列表\n<span class=\"suffix\">:</span>\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/10\">\n理财网站\n</a>\n</span>\n',28,2,NULL),(69,32,'2014-05-12 02:49:55',NULL,4,18,NULL,'<span class=\"in_timeline\">\n创建 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/10/todoitem/18\">\n首页排行榜\n</a>\n</span>\n',28,2,NULL),(70,32,'2014-05-12 11:40:57',NULL,4,19,NULL,'<span class=\"in_timeline\">\n创建 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/10/todoitem/19\">\n网页编辑器\n</a>\n</span>\n',28,2,NULL),(71,32,'2014-05-12 11:40:59',9,4,18,'首页排行榜','<span class=\"in_timeline\">\n完成 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/10/todoitem/18\">\n首页排行榜\n</a>\n</span>\n',28,2,NULL),(72,32,'2014-05-12 11:41:03',NULL,4,19,NULL,'<span class=\"in_timeline\">\n分配 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/10/todoitem/19\">\n网页编辑器\n</a>\n给\n\n沈淼奇\n\n\n截至日期\n<span class=\"suffix\">:</span>\n2014-05-13\n\n</span>\n',28,2,NULL),(73,32,'2014-05-13 12:15:58',9,4,19,'网页编辑器','<span class=\"in_timeline\">\n完成 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/10/todoitem/19\">\n网页编辑器\n</a>\n</span>\n',28,2,NULL),(74,32,'2014-05-13 12:16:28',NULL,4,20,NULL,'<span class=\"in_timeline\">\n创建 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/10/todoitem/20\">\n文章增加，修改\n</a>\n</span>\n',28,2,NULL),(75,32,'2014-05-13 12:16:31',9,4,20,'文章增加，修改','<span class=\"in_timeline\">\n完成 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/10/todoitem/20\">\n文章增加，修改\n</a>\n</span>\n',28,2,NULL),(76,32,'2014-05-13 12:16:41',NULL,4,21,NULL,'<span class=\"in_timeline\">\n创建 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/10/todoitem/21\">\n文章展示\n</a>\n</span>\n',28,2,NULL),(77,32,'2014-05-13 12:16:51',NULL,4,22,NULL,'<span class=\"in_timeline\">\n创建 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/10/todoitem/22\">\n文章同步到微博\n</a>\n</span>\n',28,2,NULL),(78,32,'2014-05-14 14:34:33',9,4,21,'文章展示','<span class=\"in_timeline\">\n完成 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/10/todoitem/21\">\n文章展示\n</a>\n</span>\n',28,2,NULL),(79,32,'2014-05-14 14:34:57',NULL,4,23,NULL,'<span class=\"in_timeline\">\n创建 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/10/todoitem/23\">\n文章预览\n</a>\n</span>\n',28,2,NULL),(80,32,'2014-05-14 14:35:05',NULL,4,24,NULL,'<span class=\"in_timeline\">\n创建 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/10/todoitem/24\">\n权限控制\n</a>\n</span>\n',28,2,NULL),(81,32,'2014-05-14 14:35:09',NULL,4,22,NULL,'<span class=\"in_timeline\">\n分配 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/10/todoitem/22\">\n文章同步到微博\n</a>\n给\n\n沈淼奇\n\n\n截至日期\n<span class=\"suffix\">:</span>\n2014-05-14\n\n</span>\n',28,2,NULL),(82,32,'2014-05-14 14:35:12',NULL,4,23,NULL,'<span class=\"in_timeline\">\n分配 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/10/todoitem/23\">\n文章预览\n</a>\n给\n\n沈淼奇\n\n\n截至日期\n<span class=\"suffix\">:</span>\n2014-05-14\n\n</span>\n',28,2,NULL),(83,32,'2014-05-14 14:35:15',NULL,4,24,NULL,'<span class=\"in_timeline\">\n分配 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/10/todoitem/24\">\n权限控制\n</a>\n给\n\n沈淼奇\n\n\n截至日期\n<span class=\"suffix\">:</span>\n2014-05-14\n\n</span>\n',28,2,NULL),(84,32,'2014-05-14 14:35:26',NULL,4,22,NULL,'<span class=\"in_timeline\">\n分配 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/10/todoitem/22\">\n文章同步到微博\n</a>\n给\n\n沈淼奇\n\n\n截至日期\n<span class=\"suffix\">:</span>\n2014-05-15\n\n</span>\n',28,2,NULL),(85,32,'2014-05-14 14:35:29',NULL,4,23,NULL,'<span class=\"in_timeline\">\n分配 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/10/todoitem/23\">\n文章预览\n</a>\n给\n\n沈淼奇\n\n\n截至日期\n<span class=\"suffix\">:</span>\n2014-05-15\n\n</span>\n',28,2,NULL),(86,32,'2014-05-14 14:35:33',NULL,4,24,NULL,'<span class=\"in_timeline\">\n分配 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/10/todoitem/24\">\n权限控制\n</a>\n给\n\n沈淼奇\n\n\n截至日期\n<span class=\"suffix\">:</span>\n2014-05-15\n\n</span>\n',28,2,NULL),(87,32,'2014-05-14 15:46:50',9,4,24,'权限控制','<span class=\"in_timeline\">\n完成 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/10/todoitem/24\">\n权限控制\n</a>\n</span>\n',28,2,NULL),(88,32,'2014-05-15 07:16:29',9,4,22,'文章同步到微博','<span class=\"in_timeline\">\n完成 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/10/todoitem/22\">\n文章同步到微博\n</a>\n</span>\n',28,2,NULL),(89,32,'2014-05-15 07:16:31',9,4,23,'文章预览','<span class=\"in_timeline\">\n完成 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/10/todoitem/23\">\n文章预览\n</a>\n</span>\n',28,2,NULL),(90,32,'2014-05-15 14:43:46',NULL,4,25,NULL,'<span class=\"in_timeline\">\n创建 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/10/todoitem/25\">\n排行榜历史\n</a>\n</span>\n',28,2,NULL),(91,32,'2014-05-16 03:20:23',9,4,25,'排行榜历史','<span class=\"in_timeline\">\n完成 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/10/todoitem/25\">\n排行榜历史\n</a>\n</span>\n',28,2,NULL),(92,32,'2014-05-16 03:20:44',NULL,4,26,NULL,'<span class=\"in_timeline\">\n创建 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/10/todoitem/26\">\n旺财静态化\n</a>\n</span>\n',28,2,NULL),(93,32,'2014-05-16 03:21:03',NULL,4,26,NULL,'<span class=\"in_timeline\">\n分配 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/10/todoitem/26\">\n网站静态化\n</a>\n给\n\n\n</span>\n',28,2,NULL),(94,32,'2014-05-16 05:18:46',NULL,4,27,NULL,'<span class=\"in_timeline\">\n创建 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/10/todoitem/27\">\n网页编辑器美化\n</a>\n</span>\n',28,2,NULL),(95,32,'2014-05-16 05:18:49',NULL,4,27,NULL,'<span class=\"in_timeline\">\n分配 任务\n<a class=\"decorated\" data-default-stack=\"true\" href=\"/28/project/2/todolist/10/todoitem/27\">\n网页编辑器美化\n</a>\n给\n\n\n截至日期\n<span class=\"suffix\">:</span>\n2014-05-16\n\n</span>\n',28,2,NULL);
/*!40000 ALTER TABLE `operation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(90) NOT NULL,
  `description` varchar(300) DEFAULT NULL,
  `own_id` int(10) NOT NULL,
  `team_id` int(10) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `discussionNum` int(3) DEFAULT '0',
  `todoNum` int(3) DEFAULT '0',
  `fileNum` int(3) DEFAULT '0',
  `documentNum` int(3) DEFAULT '0',
  `repository` int(1) DEFAULT '0',
  `repositoryName` varchar(180) DEFAULT NULL,
  `color` varchar(10) DEFAULT NULL,
  `modify_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `author` (`own_id`),
  KEY `fk_project_team1_idx` (`team_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES (2,'时间管理','时间管理',32,28,'2014-01-20 01:19:09',0,21,0,0,0,NULL,'2c5322','2014-01-20 01:19:09'),(6,'时间管理','时间管理',33,29,'2014-06-11 14:39:35',0,0,0,0,0,NULL,'2c5322',NULL);
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `team`
--

DROP TABLE IF EXISTS `team`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `team` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(90) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `description` varchar(300) DEFAULT NULL,
  `modify_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team`
--

LOCK TABLES `team` WRITE;
/*!40000 ALTER TABLE `team` DISABLE KEYS */;
INSERT INTO `team` VALUES (28,'goldratio','2014-01-19 15:45:22','1',NULL),(29,'test','2014-06-11 14:38:33','2',NULL);
/*!40000 ALTER TABLE `team` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `todo`
--

DROP TABLE IF EXISTS `todo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `todo` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `description` varchar(300) NOT NULL,
  `own_id` int(10) NOT NULL,
  `list_id` int(10) NOT NULL,
  `worker_id` int(10) DEFAULT NULL,
  `deadline` date DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `project_id` int(10) DEFAULT NULL,
  `team_id` int(10) DEFAULT NULL,
  `done` int(1) DEFAULT '0',
  `modify_time` timestamp NULL DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `author` (`own_id`),
  KEY `fk_todoitem_todolist1_idx` (`list_id`),
  KEY `fk_todoitem_user2_idx` (`worker_id`),
  KEY `index5` (`deadline`),
  KEY `index6` (`create_time`),
  KEY `fk_todoitem_project1_idx` (`project_id`)
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `todo`
--

LOCK TABLES `todo` WRITE;
/*!40000 ALTER TABLE `todo` DISABLE KEYS */;
INSERT INTO `todo` VALUES (7,'app部署',32,4,32,'2014-05-26','2014-01-20 01:34:05',2,28,1,NULL,0),(8,'任务修改历史',32,6,32,'2014-05-26','2014-01-20 05:47:30',2,28,1,NULL,0),(10,'日历订阅',32,6,32,'2014-05-26','2014-01-20 05:50:21',2,28,1,NULL,0),(12,'货币基金',32,7,32,'2014-05-26','2014-02-18 02:00:12',2,28,1,NULL,0),(13,'理财产品',32,7,32,'2014-05-26','2014-02-18 08:00:56',2,28,1,NULL,0),(14,'Introduction',32,8,32,'2014-05-26','2014-02-18 08:01:53',2,28,1,NULL,0),(15,'百度地址获取周边小区API调用',32,7,32,'2014-05-26','2014-03-11 00:43:37',2,28,1,NULL,0),(16,'finagle IDE搭建',32,9,32,'2014-05-26','2014-03-11 00:44:24',2,28,1,NULL,0),(17,'slick 动态SQL',32,7,32,'2014-05-26','2014-03-13 08:35:44',2,28,1,NULL,0),(18,'首页排行榜',32,10,32,'2014-05-26','2014-05-12 02:49:55',2,28,1,NULL,0),(19,'网页编辑器',32,10,32,'2014-05-26','2014-05-12 11:40:57',2,28,1,NULL,0),(20,'文章增加，修改',32,10,32,'2014-05-26','2014-05-13 12:16:28',2,28,1,NULL,0),(21,'文章展示',32,10,32,'2014-05-26','2014-05-13 12:16:41',2,28,1,NULL,0),(22,'文章同步到微博',32,10,32,'2014-05-26','2014-05-13 12:16:51',2,28,1,NULL,0),(23,'文章预览',32,10,32,'2014-05-26','2014-05-14 14:34:57',2,28,1,NULL,0),(24,'权限控制',32,10,32,'2014-05-26','2014-05-14 14:35:05',2,28,1,NULL,0),(25,'排行榜历史',32,10,32,'2014-05-26','2014-05-15 14:43:46',2,28,1,NULL,0),(41,'HTTP访问通用化',32,24,32,'2014-06-02','2014-05-26 05:14:17',2,28,1,NULL,0),(42,'内管模板个性化可行性探讨',32,24,32,'2014-06-02','2014-05-26 05:15:27',2,28,1,NULL,0),(43,'性能分析',32,24,32,'2014-06-02','2014-05-26 05:16:50',2,28,0,NULL,0),(44,'历史排行榜 日期数据补齐',32,25,32,'2014-05-27','2014-05-27 09:13:22',2,28,1,NULL,0),(45,'排行榜页面 加入面包屑',32,25,32,'2014-05-27','2014-05-27 09:13:36',2,28,1,NULL,0),(46,'排行榜页面加入banenr图',32,25,32,'2014-05-28','2014-05-27 09:13:52',2,28,1,NULL,0),(47,'首页 热门话题格式：[专题名]+标题文字  仅显示一行',32,25,32,'2014-05-28','2014-05-27 09:14:22',2,28,1,NULL,0),(48,'首页、话题的子专题列表 加入分页',32,25,32,'2014-05-28','2014-05-27 09:14:40',2,28,1,NULL,0),(49,'文章页 “热门产品”改为“同文相关产品”',32,25,32,'2014-05-28','2014-05-27 09:14:55',2,28,1,NULL,0),(50,'赚钱话题页面（含子专题）右侧“热门产品”与首页保持一致',32,25,32,'2014-05-28','2014-05-27 09:14:56',2,28,1,NULL,0),(51,'子专题 面包屑加入父级栏目“赚钱话题”',32,25,32,'2014-06-03','2014-05-27 09:15:00',2,28,1,NULL,0),(75,'产品历史分析',32,25,32,'2014-06-09','2014-06-03 09:52:09',2,28,0,NULL,0),(76,'滚动',32,31,32,'2014-06-03','2014-06-03 14:59:59',2,28,1,NULL,0),(77,'滚动视图开发',32,31,32,'2014-06-10','2014-06-03 15:00:18',2,28,1,NULL,0),(78,'ios添加任务bug',32,31,32,'2014-06-03','2014-06-03 15:00:45',2,28,1,NULL,0),(81,'右滑菜单bug',32,31,32,'2014-06-11','2014-06-03 15:10:43',2,28,1,NULL,0),(82,'手机游览bug',32,25,32,'2014-06-09','2014-06-05 03:03:02',2,28,1,NULL,0),(83,'字体颜色bug',32,25,32,'2014-06-09','2014-06-05 03:03:12',2,28,1,NULL,0),(84,'字体居中功能',32,25,32,'2014-06-09','2014-06-05 03:03:54',2,28,1,NULL,0),(85,'源码模式',32,25,32,'2014-06-09','2014-06-05 03:04:10',2,28,1,NULL,0),(86,'用户缓存',32,24,32,'2014-06-09','2014-06-05 03:06:53',2,28,1,NULL,0),(87,'下载web页面',32,24,32,'2014-06-11','2014-06-09 07:36:23',2,28,1,NULL,0),(88,'平均数和标准差',32,32,32,'2014-06-12','2014-06-09 15:14:17',2,28,0,NULL,0),(89,'JVM概览',32,33,32,'2014-06-13','2014-06-10 08:53:29',2,28,0,NULL,0),(90,'日历修改',32,31,32,'2014-06-11','2014-06-10 13:51:57',2,28,1,NULL,0),(91,'菜单优化',32,31,32,'2014-06-11','2014-06-11 01:42:56',2,28,1,NULL,0),(92,'直方图',32,32,32,'2014-06-11','2014-06-11 06:03:35',2,28,1,NULL,0),(93,'Tabview 优化',32,31,32,'2014-06-12','2014-06-11 14:33:59',2,28,1,NULL,0),(94,'test',33,34,33,'2014-06-11','2014-06-11 15:04:41',6,29,1,NULL,0),(95,'文件上传',32,31,32,'2014-06-12','2014-06-12 01:15:07',2,28,1,NULL,0),(96,'回顾功能开发',32,31,32,'2014-06-13','2014-06-12 06:27:21',2,28,0,NULL,0);
/*!40000 ALTER TABLE `todo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `todo_data`
--

DROP TABLE IF EXISTS `todo_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `todo_data` (
  `project_id` int(10) NOT NULL DEFAULT '0',
  `add_date` date NOT NULL DEFAULT '0000-00-00',
  `total_number` int(5) DEFAULT NULL,
  PRIMARY KEY (`project_id`,`add_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `todo_data`
--

LOCK TABLES `todo_data` WRITE;
/*!40000 ALTER TABLE `todo_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `todo_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `todo_user_data`
--

DROP TABLE IF EXISTS `todo_user_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `todo_user_data` (
  `project_id` int(10) NOT NULL DEFAULT '0',
  `user_id` int(10) NOT NULL DEFAULT '0',
  `add_date` date NOT NULL DEFAULT '0000-00-00',
  `total_number` int(5) DEFAULT NULL,
  PRIMARY KEY (`project_id`,`user_id`,`add_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `todo_user_data`
--

LOCK TABLES `todo_user_data` WRITE;
/*!40000 ALTER TABLE `todo_user_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `todo_user_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `todocomment`
--

DROP TABLE IF EXISTS `todocomment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `todocomment` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `content` varchar(300) DEFAULT NULL,
  `own_id` int(10) NOT NULL,
  `todoitem_id` int(10) NOT NULL,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `team_id` int(10) DEFAULT NULL,
  `project_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `author` (`own_id`),
  KEY `fk_todocomment_todoitem1_idx` (`todoitem_id`),
  KEY `index4` (`createTime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `todocomment`
--

LOCK TABLES `todocomment` WRITE;
/*!40000 ALTER TABLE `todocomment` DISABLE KEYS */;
/*!40000 ALTER TABLE `todocomment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `todolist`
--

DROP TABLE IF EXISTS `todolist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `todolist` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(300) DEFAULT NULL,
  `own_id` int(10) NOT NULL DEFAULT '1',
  `project_id` int(10) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `description` varchar(300) DEFAULT NULL,
  `team_id` int(10) DEFAULT NULL,
  `modify_time` timestamp NULL DEFAULT NULL,
  `uncomplete_no` int(2) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `author` (`own_id`),
  KEY `fk_todolist_project1_idx` (`project_id`),
  KEY `index4` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `todolist`
--

LOCK TABLES `todolist` WRITE;
/*!40000 ALTER TABLE `todolist` DISABLE KEYS */;
INSERT INTO `todolist` VALUES (4,'Jetty学习',32,2,'2014-01-20 01:23:41','22',28,'2014-05-23 15:05:16',0),(5,'P2P网站',32,2,'2014-01-20 01:36:42',NULL,28,'2014-05-22 15:23:01',0),(6,'MyTask完善',32,2,'2014-01-20 05:47:11',NULL,28,NULL,0),(7,'数据抓取',32,2,'2014-02-18 02:00:00',NULL,28,NULL,0),(8,'Akka Documen阅读',32,2,'2014-02-18 08:01:26',NULL,28,NULL,0),(9,'学习',32,2,'2014-03-11 00:44:01',NULL,28,NULL,0),(10,'理财网站',32,2,'2014-05-12 02:32:19',NULL,28,NULL,0),(23,'test',1,2,'2014-05-23 09:42:22',NULL,NULL,NULL,0),(24,'智慧社区',1,2,'2014-05-26 05:13:51',NULL,NULL,NULL,1),(25,'理财网站',1,2,'2014-05-27 09:13:20',NULL,NULL,NULL,1),(31,'任务管理系统',1,2,'2014-06-03 14:59:13',NULL,NULL,NULL,3),(32,'统计学习',1,2,'2014-06-09 15:13:41',NULL,NULL,NULL,1),(33,'Java性能优化',1,2,'2014-06-10 08:53:07',NULL,NULL,NULL,1),(34,'test',1,6,'2014-06-11 15:04:27',NULL,NULL,NULL,0);
/*!40000 ALTER TABLE `todolist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `todolistcomment`
--

DROP TABLE IF EXISTS `todolistcomment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `todolistcomment` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `content` varchar(300) DEFAULT NULL,
  `own_id` int(10) NOT NULL,
  `todolist_id` int(10) NOT NULL,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `team_id` int(10) DEFAULT NULL,
  `project_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `author` (`own_id`),
  KEY `fk_todolistcomment_todolist1_idx` (`todolist_id`),
  KEY `index4` (`createTime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `todolistcomment`
--

LOCK TABLES `todolistcomment` WRITE;
/*!40000 ALTER TABLE `todolistcomment` DISABLE KEYS */;
/*!40000 ALTER TABLE `todolistcomment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `email` varchar(60) NOT NULL,
  `password` varchar(100) NOT NULL,
  `nickName` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `avatar` varchar(60) DEFAULT NULL,
  `salt` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (32,'沈淼奇','goldratio87@gmail.com','c8Tp1h/gN6FK6H7PllTrRA==','沈淼奇',NULL,'default',NULL),(33,'梁','mqshen@126.com','LTe8ybzbllMihRB1zqWxHQ==','梁',NULL,'default',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_cookie`
--

DROP TABLE IF EXISTS `user_cookie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_cookie` (
  `user_id` int(10) NOT NULL,
  `sid` varchar(40) NOT NULL,
  PRIMARY KEY (`user_id`),
  KEY `sid` (`sid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_cookie`
--

LOCK TABLES `user_cookie` WRITE;
/*!40000 ALTER TABLE `user_cookie` DISABLE KEYS */;
INSERT INTO `user_cookie` VALUES (32,'2995f22744544901bc26a71af3142747');
/*!40000 ALTER TABLE `user_cookie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_project_rel`
--

DROP TABLE IF EXISTS `user_project_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_project_rel` (
  `project_id` int(10) NOT NULL,
  `user_id` int(10) NOT NULL,
  PRIMARY KEY (`project_id`,`user_id`),
  KEY `fk_project_user_rel_project1_idx` (`project_id`),
  KEY `fk_project_user_rel_user1_idx` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_project_rel`
--

LOCK TABLES `user_project_rel` WRITE;
/*!40000 ALTER TABLE `user_project_rel` DISABLE KEYS */;
INSERT INTO `user_project_rel` VALUES (2,32),(6,33);
/*!40000 ALTER TABLE `user_project_rel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_team_rel`
--

DROP TABLE IF EXISTS `user_team_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_team_rel` (
  `team_id` int(10) NOT NULL,
  `user_id` int(10) NOT NULL,
  `privilege` int(2) DEFAULT NULL,
  PRIMARY KEY (`team_id`,`user_id`),
  KEY `fk_team_user_rel_user_idx` (`user_id`),
  KEY `fk_team_user_rel_team1_idx` (`team_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_team_rel`
--

LOCK TABLES `user_team_rel` WRITE;
/*!40000 ALTER TABLE `user_team_rel` DISABLE KEYS */;
INSERT INTO `user_team_rel` VALUES (28,32,2),(29,33,2);
/*!40000 ALTER TABLE `user_team_rel` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-06-12 23:25:38
