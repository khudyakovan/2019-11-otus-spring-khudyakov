-- MySQL dump 10.13  Distrib 8.0.18, for Linux (x86_64)
--
-- Host: localhost    Database: library
-- ------------------------------------------------------
-- Server version	8.0.18-0ubuntu0.19.10.1

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `tbl_books`;
DROP TABLE IF EXISTS `tbl_authors`;
DROP TABLE IF EXISTS `tbl_book_genre`;
DROP TABLE IF EXISTS `tbl_genres`;
DROP TABLE IF EXISTS `tbl_book_author`;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE `tbl_books` (
  `uid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Уникальный идентификатор',
  `title` varchar(255) NOT NULL COMMENT 'Заголовок книги',
  `isbn` bigint(20) DEFAULT NULL,
  `publication_year` int(11) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COMMENT='Справочник книг';

CREATE TABLE `tbl_authors` (
  `uid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Уникальный идентификатор',
  `full_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `pen_name` varchar(255) DEFAULT NULL COMMENT 'Творческий псевдоним',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8 COMMENT='Справочник авторов книг';

CREATE TABLE `tbl_genres` (
  `uid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Уникальный идентификатор',
  `name` varchar(50) NOT NULL COMMENT 'Жанр книги',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COMMENT='Справочник жанров';


CREATE TABLE `tbl_book_author` (
  `uid` bigint(20) NOT NULL AUTO_INCREMENT,
  `book_uid` bigint(20) NOT NULL,
  `author_uid` bigint(20) NOT NULL,
  PRIMARY KEY (`uid`),
  KEY `tbl_book_author_book_FK` (`book_uid`),
  KEY `tbl_book_author_author_FK` (`author_uid`),
  CONSTRAINT `tbl_book_author_author_FK` FOREIGN KEY (`author_uid`) REFERENCES `tbl_authors` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tbl_book_author_book_FK` FOREIGN KEY (`book_uid`) REFERENCES `tbl_books` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 COMMENT='Регистр сопоставлений книги ее авторов';

CREATE TABLE `tbl_book_genre` (
  `uid` bigint(20) NOT NULL AUTO_INCREMENT,
  `book_uid` bigint(20) NOT NULL,
  `genre_uid` bigint(20) NOT NULL,
  PRIMARY KEY (`uid`),
  KEY `fk_book_genre_idx` (`book_uid`),
  KEY `fk_book_genre_book_idx` (`genre_uid`),
  CONSTRAINT `fk_book_genre_book` FOREIGN KEY (`book_uid`) REFERENCES `tbl_books` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_book_genre_genre` FOREIGN KEY (`genre_uid`) REFERENCES `tbl_genres` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8 COMMENT='Регистр сопоставлений жанра книгам';