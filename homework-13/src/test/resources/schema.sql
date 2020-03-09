drop table tbl_book_author if exists;
drop table tbl_book_genre if exists;
drop table tbl_book_comment if exists;
drop table tbl_user_role if exists;

drop table tbl_comments if exists;
drop table tbl_users if exists;
drop table tbl_roles if exists;


drop table tbl_authors if exists;
drop table tbl_books if exists;
drop table tbl_genres if exists;

DROP TABLE IF EXISTS acl_entry;
DROP TABLE IF EXISTS acl_object_identity;
DROP TABLE IF EXISTS acl_sid;
DROP TABLE IF EXISTS acl_class;

create table tbl_authors
(
    uid       bigint auto_increment
        primary key,
    full_name varchar(255) null,
    pen_name  varchar(255) null
);

create table tbl_books
(
    uid              bigint auto_increment
        primary key,
    title            varchar(255) not null,
    isbn             bigint       null,
    publication_year int          null
);

create table tbl_book_author
(
    uid        bigint auto_increment
        primary key,
    book_uid   bigint not null,
    author_uid bigint not null,
    constraint tbl_book_author_author_FK
        foreign key (author_uid) references tbl_authors (uid)
            on update cascade on delete cascade,
    constraint tbl_book_author_book_FK
        foreign key (book_uid) references tbl_books (uid)
            on update cascade on delete cascade
);

create table tbl_genres
(
    uid  bigint auto_increment
        primary key,
    name varchar(50) not null
);

create table tbl_book_genre
(
    uid       bigint auto_increment
        primary key,
    book_uid  bigint not null,
    genre_uid bigint not null,
    constraint fk_book_genre_book
        foreign key (book_uid) references tbl_books (uid)
            on update cascade on delete cascade,
    constraint fk_book_genre_genre
        foreign key (genre_uid) references tbl_genres (uid)
            on update cascade on delete cascade
);

create index fk_book_genre_book_idx
    on tbl_book_genre (genre_uid);

create index fk_book_genre_idx
    on tbl_book_genre (book_uid);

create table tbl_roles
(
    uid  bigint auto_increment
        primary key,
    name varchar(50) not null
);

create table tbl_users
(
    uid        bigint auto_increment
        primary key,
    username   varchar(50)  not null,
    password   varchar(20)  null,
    first_name varchar(100) null,
    last_name  varchar(100) null,
    constraint UN_tbl_commentators
        unique (username)
);

create table tbl_comments
(
    uid          bigint auto_increment
        primary key,
    book_uid     bigint   not null,
    user_uid     bigint   not null,
    comment_text text     not null,
    comment_date datetime not null,
    constraint FK_comments_book
        foreign key (book_uid) references tbl_books (uid)
            on update cascade on delete cascade,
    constraint FK_tbl_comments
        foreign key (user_uid) references tbl_users (uid)
            on update cascade on delete cascade
);

create index tbl_comments_FK
    on tbl_comments (user_uid);

create table tbl_user_role
(
    uid      bigint not null
        primary key,
    user_uid bigint not null,
    role_uid bigint not null,
    constraint fk_user_role_role
        foreign key (role_uid) references tbl_roles (uid),
    constraint fk_user_role_user
        foreign key (user_uid) references tbl_users (uid)
            on update cascade on delete cascade
);

create index fk_user_role_role_idx
    on tbl_user_role (role_uid);

create index fk_user_role_user_idx
    on tbl_user_role (user_uid);

--- ACL

CREATE TABLE IF NOT EXISTS acl_sid (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  principal tinyint(1) NOT NULL,
  sid varchar(100) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY unique_uk_1 (sid,principal)
);

CREATE TABLE IF NOT EXISTS acl_class (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  class varchar(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY unique_uk_2 (class)
);

CREATE TABLE IF NOT EXISTS acl_entry (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  acl_object_identity bigint(20) NOT NULL,
  ace_order int(11) NOT NULL,
  sid bigint(20) NOT NULL,
  mask int(11) NOT NULL,
  granting tinyint(1) NOT NULL,
  audit_success tinyint(1) NOT NULL,
  audit_failure tinyint(1) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY unique_uk_4 (acl_object_identity,ace_order)
);

CREATE TABLE IF NOT EXISTS acl_object_identity (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  object_id_class bigint(20) NOT NULL,
  object_id_identity bigint(20) NOT NULL,
  parent_object bigint(20) DEFAULT NULL,
  owner_sid bigint(20) DEFAULT NULL,
  entries_inheriting tinyint(1) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY unique_uk_3 (object_id_class,object_id_identity)
);

ALTER TABLE acl_entry
ADD FOREIGN KEY (acl_object_identity) REFERENCES acl_object_identity(id);

ALTER TABLE acl_entry
ADD FOREIGN KEY (sid) REFERENCES acl_sid(id);

--
-- Constraints for table acl_object_identity
--
ALTER TABLE acl_object_identity
ADD FOREIGN KEY (parent_object) REFERENCES acl_object_identity (id);

ALTER TABLE acl_object_identity
ADD FOREIGN KEY (object_id_class) REFERENCES acl_class (id);

ALTER TABLE acl_object_identity
ADD FOREIGN KEY (owner_sid) REFERENCES acl_sid (id);
