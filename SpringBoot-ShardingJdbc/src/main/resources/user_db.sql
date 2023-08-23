create database user_db;

use user_db;

create table t_user(
                       `user_id` bigint(20) primary key,
                       `username` varchar(100) not null,
                       `ustatus` varchar(50) not NULL
);

SELECT * FROM user_db.t_user tu;

create table t_udict(
                        `dictid` bigint(20) primary key,
                        `ustatus` varchar(100) not null,
                        `uvalue` varchar(100) not null
);
