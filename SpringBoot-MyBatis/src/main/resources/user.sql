DROP
DATABASE IF EXISTS `test`;

CREATE
DATABASE `test`;

USE
`test`;

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user`
(
    `uid`     INT(11) NOT NULL AUTO_INCREMENT,
    `uname`   VARCHAR(20) NOT NULL,
    `ugender` VARCHAR(20) NOT NULL,
    `uage`    INT(11) NOT NULL,
    PRIMARY KEY (`uid`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

INSERT INTO `test`.`t_user` (`uname`, `ugender`, `uage`)
VALUES ('张三', '男', '18');
INSERT INTO `test`.`t_user` (`uname`, `ugender`, `uage`)
VALUES ('李四', '女', '19');
INSERT INTO `test`.`t_user` (`uname`, `ugender`, `uage`)
VALUES ('王五', '男', '20');
