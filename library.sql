CREATE DATABASE  IF NOT EXISTS `library` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `library`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: 127.0.0.1    Database: library
-- ------------------------------------------------------
-- Server version	5.6.21

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
-- Table structure for table `biblioteca`
--

DROP TABLE IF EXISTS `biblioteca`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `biblioteca` (
  `IdLibro` int(11) NOT NULL AUTO_INCREMENT,
  `Titolo` varchar(45) NOT NULL,
  `Autore` varchar(45) NOT NULL,
  `Genere` varchar(45) NOT NULL,
  `DataPrestito` date DEFAULT NULL,
  `DataRestituzione` date DEFAULT NULL,
  `BeneficiarioPrestito` varchar(45) DEFAULT NULL,
  `NumeroDiPrestiti` int(11) NOT NULL DEFAULT '0',
  `Scadenza` date DEFAULT NULL,
  `VotoMedio` double NOT NULL DEFAULT '0',
  `NumRecensioni` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`IdLibro`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `biblioteca`
--

LOCK TABLES `biblioteca` WRITE;
/*!40000 ALTER TABLE `biblioteca` DISABLE KEYS */;
INSERT INTO `biblioteca` VALUES (1,'I promessi sposi','Alessandro Manzoni','Romanzo Storico','2020-05-24','2020-05-24',NULL,3,NULL,3.5,3),(2,'La grande storia del tempo','Stephen W. Hawking','Scientifico','2020-05-24','2020-05-24',NULL,2,NULL,4.5,2),(3,'La guerra di Giugurta','Sallustio','Classico','2020-05-04',NULL,'verdi',2,'2020-06-01',4,1),(4,'Il cacciatore di aquiloni','Khaled Hosseini','Romanzo',NULL,NULL,NULL,0,NULL,0,0),(5,'De bello gallico','Giulio Cesare','Classico','2020-05-24',NULL,'verdi',2,'2020-09-24',4,1),(6,'Cent\'anni di solitudine','Gabriel Garcia Marquez','Romanzo','2020-05-24','2020-05-24',NULL,2,NULL,4,2),(7,'Fiesta','Ernest Hemingway','Classico Moderno','2020-05-24','2020-05-24',NULL,3,NULL,3.75,3),(8,'Ogni mattina a Jenin','Susan Abulhawa','Romanzo','2020-05-24','2020-05-24',NULL,2,NULL,4.5,2),(9,'Buio a mezzogiorno','Arthur Koestler','Classico Moderno',NULL,NULL,NULL,0,NULL,0,0),(10,'Sulla strada','Jack Kerouac','Classico Moderno',NULL,NULL,NULL,0,NULL,0,0),(11,'1984','George Orwell','Classico Moderno',NULL,NULL,NULL,0,NULL,0,0),(12,'Seven brief lessons on physics','Carlo Rovelli','Scientifico',NULL,NULL,NULL,0,NULL,0,0),(13,'Se questo è un uomo','Primo Levi','Classico Moderno',NULL,NULL,NULL,0,NULL,0,0),(14,'Uno, nessuno e centomila','Luigi Pirandello','Classico',NULL,NULL,NULL,0,NULL,0,0),(15,'L\'ultimo saluto di Sherlock Holmes','Arthur Conan Doyle','Giallo',NULL,NULL,NULL,0,NULL,0,0),(16,'Uno studio in rosso','Arthur Conan Doyle','Giallo',NULL,NULL,NULL,0,NULL,0,0),(17,'Niente di nuovo sul fronte occidentale','Erich Maria Remarque','Classico Moderno',NULL,NULL,NULL,0,NULL,0,0),(18,'Il bambino del giovedi','Alison Pick','Romanzo',NULL,NULL,NULL,0,NULL,0,0),(19,'L\'armata perduta','Valerio Massimo Manfredi','Romanzo',NULL,NULL,NULL,0,NULL,0,0),(20,'Il signore delle mosche','William Golding','Romanzo',NULL,NULL,NULL,0,NULL,0,0);
/*!40000 ALTER TABLE `biblioteca` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-06-04 18:12:45
