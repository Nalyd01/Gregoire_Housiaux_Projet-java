CREATE DATABASE  IF NOT EXISTS `java_projet` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;
USE `java_projet`;
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: java_projet
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `zone`
--

DROP TABLE IF EXISTS `zone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `zone` (
  `identifiant` int(5) NOT NULL CHECK (identifiant > 0),
  `nom` varchar(20) NOT NULL,
  `rayon` int(3) NOT NULL CHECK (rayon > 0),
  PRIMARY KEY (`identifiant`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `zone`
--

LOCK TABLES `zone` WRITE;
/*!40000 ALTER TABLE `zone` DISABLE KEYS */;
INSERT INTO `zone` (`identifiant`, `nom`, `rayon`) VALUES (19654,'Ardenne',50),(27635,'Famenne',50),(38153,'Bruxelles',50),(43225,'Namur',50),(50978,'Liège',50);
/*!40000 ALTER TABLE `zone` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `voiture`
--

DROP TABLE IF EXISTS `voiture`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `voiture` (
  `numChassis` varchar(17) NOT NULL,
  `marque` varchar(20) NOT NULL CHECK (marque IN ('Skoda','Audi','Volkswagen','Seat')),
  `anneeAchat` date NOT NULL CHECK (anneeAchat > '1980-01-01'),
  `kilometrage` decimal(8,2) NOT NULL CHECK (kilometrage > 0 AND kilometrage < 200000),
  PRIMARY KEY (`numChassis`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `voiture`
--

LOCK TABLES `voiture` WRITE;
/*!40000 ALTER TABLE `voiture` DISABLE KEYS */;
INSERT INTO `voiture` (`numChassis`, `marque`, `anneeAchat`, `kilometrage`) VALUES ('ANZEHD56ZVV879283','Skoda','2012-03-15',100000.00),('EACZAA34ZXX325506','Skoda','2006-09-02',4309.00),('MONJHR92MQL230446','Audi','2014-05-29',134523.00),('WAUZZZ85ZHA123456','Audi','2008-03-22',132003.00),('WCCVNL54ZML310506','Seat','2006-02-13',87980.00);
/*!40000 ALTER TABLE `voiture` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Table structure for table `chauffeur`
--

DROP TABLE IF EXISTS `chauffeur`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `chauffeur` (
  `matricule` int(6) NOT NULL CHECK (matricule > 0),
  `nom` varchar(20) NOT NULL,
  `dateEmbauche` date NOT NULL CHECK (dateEmbauche > '1990-01-01'),
  `salaire` decimal(10,0) NOT NULL CHECK (salaire > 0 AND salary < 5000),
  `zone_id` int(5) NOT NULL ,
  `numChassis` varchar(17) NOT NULL,
  PRIMARY KEY (`matricule`),
  KEY `zone_fk` (`zone_id`),
  KEY `voiture_fk` (`numChassis`),
  CONSTRAINT `voiture_fk` FOREIGN KEY (`numChassis`) REFERENCES `voiture` (`numChassis`),
  CONSTRAINT `zone_fk` FOREIGN KEY (`zone_id`) REFERENCES `zone` (`identifiant`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chauffeur`
--

LOCK TABLES `chauffeur` WRITE;
/*!40000 ALTER TABLE `chauffeur` DISABLE KEYS */;
INSERT INTO `chauffeur` (`matricule`, `nom`, `dateEmbauche`, `salaire`, `zone_id`, `numChassis`) VALUES (110698,'Hubert','2010-12-20',2000,19654,'WAUZZZ85ZHA123456'),(121291,'Ansiaux','2014-01-03',1689,19654,'ANZEHD56ZVV879283'),(150380,'Soupart','2008-03-01',2250,38153,'WCCVNL54ZML310506'),(240872,'Lambert','2008-11-14',2500,43225,'EACZAA34ZXX325506'),(280191,'Collard','2016-12-21',1879,27635,'MONJHR92MQL230446');
/*!40000 ALTER TABLE `chauffeur` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `client` (
  `identifiant` int(6) NOT NULL CHECK (identifiant > 0),
  `nom` varchar(20) NOT NULL,
  `prenom` varchar(20) NOT NULL,
  PRIMARY KEY (`identifiant`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` (`identifiant`, `nom`, `prenom`) VALUES (1,'Lothaire','Renaud'),(2,'Taton','Alexandre'),(3,'Renoy','Olivier'),(4,'Wilkin','Françoise'),(5,'Grégoire','Amélia'),(6,'Thény','Véronique'),(7,'Parache','Alain'),(8,'Parache','Lorrie'),(9,'Fourny','Dimitri'),(10,'Michel','Lambert'),(11,'Paepe','Alexandre'),(12,'Taton','Sylvain'),(13,'Gheert','Maxence'),(14,'Hollard','Mathieu'),(15,'Lambert','Alix'),(16,'Delait','François');
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `localite`
--

DROP TABLE IF EXISTS `localite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `localite` (
  `codePostal` int(4) NOT NULL CHECK (codePostal >= 1000 AND codePostal <= 9992),
  `nom` varchar(20) NOT NULL,
  PRIMARY KEY (`codePostal`,`nom`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `localite`
--

LOCK TABLES `localite` WRITE;
/*!40000 ALTER TABLE `localite` DISABLE KEYS */;
INSERT INTO `localite` (`codePostal`, `nom`) VALUES (1000,'Bruxelles'),(1310,'La Hulpe'),(1340,'Ottignies'),(1400,'Nivelles'),(2000,'Anvers'),(2300,'Turnhout'),(3832,'Ulbeek'),(4000,'Liège'),(4040,'Herstal'),(4042,'Liers'),(4830,'Limbourg'),(4970,'Francorchamps'),(5000,'Namur'),(5020,'Champion'),(5030,'Gembloux'),(5100,'Daves'),(5100,'Jambes'),(5100,'Wépion'),(6800,'Libramont'),(6820,'Florenville'),(6830,'Bouillon');
/*!40000 ALTER TABLE `localite` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trajet`
--

DROP TABLE IF EXISTS `trajet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `trajet` (
  `identifiant` int(6) NOT NULL AUTO_INCREMENT,
  `nbKm` int(4) NOT NULL CHECK (nbKm > 0 AND nbKm < 2000),
  `nbPassagers` int(1) NOT NULL CHECK (nbPassagers > 0 AND nbPassagers < 8),
  `panne` tinyint(1) DEFAULT NULL,
  `aEuEmbouteillage` tinyint(4) DEFAULT NULL,
  `matricule` int(6) NOT NULL,
  `codePostal` int(4) NOT NULL,
  `nom` varchar(20) NOT NULL,
  `client_id` int(6) NOT NULL,
  `heureArrivee` timestamp NULL DEFAULT NULL,
  `heureDepart` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`identifiant`),
  UNIQUE KEY `identifiant_UNIQUE` (`identifiant`),
  KEY `matricule_fk` (`matricule`),
  KEY `codePostal_fk` (`codePostal`,`nom`),
  KEY `client_fk` (`client_id`),
  CONSTRAINT `date_verif` CHECK (heureArrivee >= heureDepart),
  CONSTRAINT `client_fk` FOREIGN KEY (`client_id`) REFERENCES `client` (`identifiant`),
  CONSTRAINT `codePostal_fk` FOREIGN KEY (`codePostal`, `nom`) REFERENCES `localite` (`codePostal`, `nom`),
  CONSTRAINT `matricule_fk` FOREIGN KEY (`matricule`) REFERENCES `chauffeur` (`matricule`)
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trajet`
--

LOCK TABLES `trajet` WRITE;
/*!40000 ALTER TABLE `trajet` DISABLE KEYS */;
INSERT INTO `trajet` (`identifiant`, `nbKm`, `nbPassagers`, `panne`, `aEuEmbouteillage`, `matricule`, `codePostal`, `nom`, `client_id`, `heureArrivee`, `heureDepart`) VALUES (1,50,2,NULL,NULL,110698,1000,'Bruxelles',1,'2018-05-19 17:33:00','2018-05-19 16:46:00'),(2,15,3,0,0,121291,1310,'La Hulpe',5,'2018-07-16 06:58:00','2018-07-16 06:40:00'),(3,82,1,1,0,280191,2000,'Anvers',8,'2019-08-23 19:47:00','2019-08-23 18:03:00'),(4,55,4,0,0,150380,6830,'Bouillon',12,'2018-09-03 09:59:00','2018-09-03 09:04:00'),(5,3,6,NULL,NULL,121291,1340,'Ottignies',5,'2018-11-23 07:12:00','2018-11-23 06:55:00'),(6,20,3,0,0,110698,1400,'Nivelles',3,'2018-12-19 02:45:00','2018-12-19 02:16:00'),(7,8,1,0,0,150380,6820,'Florenville',15,'2019-01-01 18:04:00','2019-01-01 17:57:00'),(8,11,2,0,1,150380,2300,'Turnhout',5,'2019-02-22 12:07:00','2019-02-22 11:00:00'),(9,20,1,NULL,NULL,121291,4000,'Liège',2,'2019-04-10 18:46:00','2019-04-10 18:20:00'),(10,33,3,0,0,150380,5100,'Wépion',4,'2019-05-01 21:44:00','2019-05-01 21:03:00'),(11,54,2,0,0,280191,5100,'Jambes',12,'2019-05-03 16:59:00','2019-05-03 12:04:00'),(12,4,1,NULL,0,150380,5100,'Wépion',14,'2019-05-10 20:18:00','2019-05-10 20:14:00'),(13,90,3,0,NULL,121291,6820,'Florenville',11,'2019-05-10 14:46:00','2019-05-10 13:26:00'),(14,47,2,1,0,110698,2300,'Turnhout',2,'2019-05-16 19:56:00','2019-05-16 19:07:00'),(15,12,4,0,0,280191,2000,'Anvers',6,'2019-05-18 06:19:00','2019-05-18 06:08:00'),(16,23,1,NULL,NULL,110698,1000,'Bruxelles',1,'2019-05-18 10:39:00','2019-05-18 10:09:00'),(17,21,1,0,0,150380,1340,'Ottignies',7,'2019-05-18 13:44:00','2019-05-18 13:10:00'),(18,102,1,0,0,240872,5100,'Wépion',11,'2019-05-18 18:30:00','2019-05-18 17:03:00'),(19,28,3,0,0,110698,1000,'Bruxelles',1,'2019-05-19 06:55:00','2019-05-19 06:16:00');
/*!40000 ALTER TABLE `trajet` ENABLE KEYS */;
UNLOCK TABLES;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-05-19 11:40:02
