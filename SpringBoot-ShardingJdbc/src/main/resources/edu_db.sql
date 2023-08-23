CREATE DATABASE edu_db_1;
CREATE DATABASE edu_db_2;

USE edu_db_1;

CREATE TABLE course_1 (
                          `cid` bigint(20) PRIMARY KEY,
                          `cname` varchar(50) NOT NULL,
                          `user_id` bigint(20) NOT NULL,
                          `cstatus` varchar(10) NOT NULL
);

CREATE TABLE course_2 (
                          `cid` bigint(20) PRIMARY KEY,
                          `cname` varchar(50) NOT NULL,
                          `user_id` bigint(20) NOT NULL,
                          `cstatus` varchar(10) NOT NULL
);

USE edu_db_2;

CREATE TABLE course_1 (
                          `cid` bigint(20) PRIMARY KEY,
                          `cname` varchar(50) NOT NULL,
                          `user_id` bigint(20) NOT NULL,
                          `cstatus` varchar(10) NOT NULL
);

CREATE TABLE course_2 (
                          `cid` bigint(20) PRIMARY KEY,
                          `cname` varchar(50) NOT NULL,
                          `user_id` bigint(20) NOT NULL,
                          `cstatus` varchar(10) NOT NULL
);
create table t_udict(
                        `dictid` bigint(20) primary key,
                        `ustatus` varchar(100) not null,
                        `uvalue` varchar(100) not null
);