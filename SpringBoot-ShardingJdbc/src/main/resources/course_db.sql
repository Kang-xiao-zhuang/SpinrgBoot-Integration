CREATE DATABASE course_db;

USE course_db;

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
