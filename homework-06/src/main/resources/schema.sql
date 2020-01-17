-- MySQL dump 10.13  Distrib 8.0.18, for Linux (x86_64)
--
-- Host: localhost    Database: library
-- ------------------------------------------------------
-- Server version	8.0.18-0ubuntu0.19.10.1

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS tbl_books;
DROP TABLE IF EXISTS tbl_authors;
DROP TABLE IF EXISTS tbl_book_genre;
DROP TABLE IF EXISTS tbl_genres;
DROP TABLE IF EXISTS tbl_book_author;
DROP TABLE IF EXISTS tbl_comments;
DROP TABLE IF EXISTS tbl_commentators;
DROP TABLE IF EXISTS tbl_book_comment;
SET FOREIGN_KEY_CHECKS = 1;

--
-- Table structure for table tbl_books
--

CREATE TABLE tbl_books (
  uid bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Уникальный идентификатор',
  title varchar(255) NOT NULL COMMENT 'Заголовок книги',
  isbn bigint(20) DEFAULT NULL,
  publication_year int(11) DEFAULT NULL,
  PRIMARY KEY (uid)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COMMENT='Справочник книг';

--
-- Table structure for table tbl_commentators
--

CREATE TABLE tbl_commentators (
  uid bigint(20) NOT NULL AUTO_INCREMENT,
  login varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  password varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  first_name varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  last_name varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (uid)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='Справочник комментаторов';
ALTER TABLE library.tbl_commentators ADD CONSTRAINT UN_tbl_commentators UNIQUE KEY (login);

--
-- Table structure for table tbl_comments
--

CREATE TABLE tbl_comments (
  uid bigint(20) NOT NULL AUTO_INCREMENT,
  book_uid bigint(20) NOT NULL,
  commentator_uid bigint(20) NOT NULL,
  comment_text text NOT NULL,
  comment_date datetime NOT NULL,
  PRIMARY KEY (uid),
  KEY tbl_comments_FK (commentator_uid)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
alter table library.tbl_comments add constraint FK_tbl_comments FOREIGN KEY (commentator_uid) REFERENCES tbl_commentators (uid) ON DELETE CASCADE ON UPDATE CASCADE;
alter table library.tbl_comments add constraint FK_comments_book FOREIGN KEY (book_uid) REFERENCES tbl_books (uid) ON DELETE CASCADE ON UPDATE CASCADE;


--
-- Table structure for table tbl_genres
--

CREATE TABLE tbl_genres (
  uid bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Уникальный идентификатор',
  name varchar(50) NOT NULL COMMENT 'Жанр книги',
  PRIMARY KEY (uid)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COMMENT='Справочник жанров';

--
-- Table structure for table tbl_authors
--

CREATE TABLE tbl_authors (
  uid bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Уникальный идентификатор',
  full_name varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  pen_name varchar(255) DEFAULT NULL COMMENT 'Творческий псевдоним',
  PRIMARY KEY (uid)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8 COMMENT='Справочник авторов книг';

--
-- Table structure for table tbl_book_author
--

CREATE TABLE tbl_book_author (
  uid bigint(20) NOT NULL AUTO_INCREMENT,
  book_uid bigint(20) NOT NULL,
  author_uid bigint(20) NOT NULL,
  PRIMARY KEY (uid),
  KEY tbl_book_author_book_FK (book_uid),
  KEY tbl_book_author_author_FK (author_uid),
  CONSTRAINT tbl_book_author_author_FK FOREIGN KEY (author_uid) REFERENCES tbl_authors (uid) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT tbl_book_author_book_FK FOREIGN KEY (book_uid) REFERENCES tbl_books (uid) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 COMMENT='Регистр сопоставлений книги ее авторов';
--
-- Table structure for table tbl_book_genre
--

CREATE TABLE tbl_book_genre (
  uid bigint(20) NOT NULL AUTO_INCREMENT,
  book_uid bigint(20) NOT NULL,
  genre_uid bigint(20) NOT NULL,
  PRIMARY KEY (uid),
  KEY fk_book_genre_idx (book_uid),
  KEY fk_book_genre_book_idx (genre_uid),
  CONSTRAINT fk_book_genre_book FOREIGN KEY (book_uid) REFERENCES tbl_books (uid) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_book_genre_genre FOREIGN KEY (genre_uid) REFERENCES tbl_genres (uid) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=96 DEFAULT CHARSET=utf8 COMMENT='Регистр сопоставлений жанра книгам';

-- Dump completed on 2020-01-11 23:50:50