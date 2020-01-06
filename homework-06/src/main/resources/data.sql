-- MySQL dump 10.13  Distrib 8.0.18, for Linux (x86_64)
--
-- Host: localhost    Database: library_test
-- ------------------------------------------------------
-- Server version	8.0.18-0ubuntu0.19.10.1

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
-- Dumping data for table `tbl_authors`
--

LOCK TABLES `tbl_authors` WRITE;
/*!40000 ALTER TABLE `tbl_authors` DISABLE KEYS */;
INSERT INTO `tbl_authors` VALUES (31,'Suzanne Collins',NULL),(32,'Joanne Rowling',NULL),(33,' Mary GrandPré',NULL),(34,'Stephenie Meyer',NULL),(35,'Harper Lee',NULL),(36,'Scott Fitzgerald',NULL),(37,'John Green',NULL),(38,'John Tolkien',NULL),(39,'Jerome Salinger',NULL),(40,'Dan Brown',NULL),(41,'Jane Austen',NULL),(42,'Khaled Hosseini',NULL),(43,'Veronica Roth',NULL),(44,'George Orwell',NULL),(45,'Erich Fromm',NULL),(46,'Celâl Üster',NULL),(48,'Anne Frank',NULL),(49,'Eleanor Roosevelt',NULL),(50,'Barbara Mooyaart-Doubleday',NULL),(51,'Stieg Larsson',NULL),(55,'Reg Keeland',NULL),(56,'Rufus Beck',NULL);
/*!40000 ALTER TABLE `tbl_authors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tbl_book_author`
--

--
-- Dumping data for table `tbl_books`
--

LOCK TABLES `tbl_books` WRITE;
/*!40000 ALTER TABLE `tbl_books` DISABLE KEYS */;
INSERT INTO `tbl_books` VALUES (3,'The Hunger Games',9780439023480,2008),(4,'Harry Potter and the Philosopher\'s Stone',9780439554930,1997),(5,'Twilight',9780316015840,2005),(6,'To Kill a Mockingbird',9780061120080,1960),(7,'The Great Gatsby',9780743273560,1925),(8,'The Fault in Our Stars',9780525478810,2012),(9,'The Hobbit or There and Back Again',9780618260300,1937),(10,'The Catcher in the Rye',9780316769170,1951),(11,'Angels & Demons ',9781416524790,2000),(12,'Pride and Prejudice',9780679783270,1813),(13,'The Kite Runner ',9781594480000,2003),(14,'Divergent',9780062024040,2011),(15,'Nineteen Eighty-Four',9780451524940,1949),(16,'Animal Farm: A Fairy Story',9780452284240,1945),(17,'Het Achterhuis: Dagboekbrieven 14 juni 1942 - 1 augustus 1944',9780553296980,1947),(18,'Män som hatar kvinnor',9780307269750,2005),(19,'Catching Fire',9780439023500,2009),(20,'Harry Potter and the Prisoner of Azkaban',9780439655480,1999),(21,' The Fellowship of the Ring',9780618346260,1954),(22,'Mockingjay',9780439023510,2010);
/*!40000 ALTER TABLE `tbl_books` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tbl_genres`
--

LOCK TABLES `tbl_genres` WRITE;
/*!40000 ALTER TABLE `tbl_genres` DISABLE KEYS */;
INSERT INTO `tbl_genres` VALUES (1,'Dystopian'),(2,'Science fiction'),(3,'Fantasy'),(4,'Romance'),(5,'Young adult fiction'),(6,'Southern Gothic'),(7,'Bildungsroman'),(8,'Tragedy'),(9,'Realistic fiction'),(10,'High fantasy'),(11,'Juvenile fantasy'),(12,'Coming-of-age fiction'),(13,'Action'),(14,'Mystery'),(15,'Thriller'),(16,'Classic Regency novel'),(17,'Historical fiction'),(18,'Drama'),(19,'Classic'),(20,'Political fiction'),(21,'Political satire'),(22,'Autobiography'),(23,'Crime'),(24,'Scandinavian noir'),(25,'War'),(26,'Adventure');
/*!40000 ALTER TABLE `tbl_genres` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Dumping data for table `tbl_book_genre`
--

LOCK TABLES `tbl_book_genre` WRITE;
/*!40000 ALTER TABLE `tbl_book_genre` DISABLE KEYS */;
INSERT INTO `tbl_book_genre` VALUES (47,3,1),(48,14,1),(49,15,1),(50,19,1),(51,3,2),(52,14,2),(53,15,2),(54,19,2),(55,22,2),(56,4,3),(57,5,3),(58,20,3),(59,21,3),(60,5,4),(61,5,5),(62,8,5),(63,14,5),(64,6,6),(65,6,7),(66,7,8),(67,8,9),(68,10,9),(69,9,10),(70,9,11),(71,10,12),(72,11,13),(73,22,13),(74,11,14),(75,18,14),(76,11,15),(77,18,15),(78,22,15),(79,12,16),(80,13,17),(81,13,18),(82,13,19),(83,15,20),(84,16,21),(85,17,22),(86,18,23),(87,18,24),(88,22,25),(89,3,26),(90,19,26),(91,22,26);
/*!40000 ALTER TABLE `tbl_book_genre` ENABLE KEYS */;
UNLOCK TABLES;


LOCK TABLES `tbl_book_author` WRITE;
/*!40000 ALTER TABLE `tbl_book_author` DISABLE KEYS */;
INSERT INTO `tbl_book_author` VALUES (5,15,46),(6,15,45),(7,4,33),(8,18,55),(9,17,48),(10,17,50),(11,11,40),(12,17,49),(13,15,44),(14,16,44),(15,6,35),(16,12,41),(17,10,39),(18,4,32),(19,20,32),(20,8,37),(21,9,38),(22,21,38),(23,13,42),(24,20,55),(25,20,56),(26,7,36),(27,5,34),(28,18,51),(29,3,31),(30,19,31),(31,22,31),(32,14,43);
/*!40000 ALTER TABLE `tbl_book_author` ENABLE KEYS */;
UNLOCK TABLES;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-12-22 22:52:41