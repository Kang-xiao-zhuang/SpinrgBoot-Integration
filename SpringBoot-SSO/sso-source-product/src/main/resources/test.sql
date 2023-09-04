DROP DATABASE IF EXISTS `test`;

CREATE DATABASE `test`;

USE `test`;

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
                            `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '角色编号',
                            `name` VARCHAR(32) NOT NULL COMMENT '角色名称',
                            `desc` VARCHAR(32) NOT NULL COMMENT '角色描述',
                            PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

INSERT  INTO `sys_role`(`id`,`name`,`desc`) VALUES (1,'ROLE_USER','用户权限');
INSERT  INTO `sys_role`(`id`,`name`,`desc`) VALUES (2,'ROLE_ADMIN','管理权限');
INSERT  INTO `sys_role`(`id`,`name`,`desc`) VALUES (3,'ROLE_PRODUCT','产品权限');
INSERT  INTO `sys_role`(`id`,`name`,`desc`) VALUES (4,'ROLE_ORDER','订单权限');

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
                            `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '用户编号',
                            `username` VARCHAR(32) NOT NULL COMMENT '用户名称',
                            `password` VARCHAR(128) NOT NULL COMMENT '用户密码',
                            `status` INT(1) NOT NULL DEFAULT '1' COMMENT '用户状态（0：关闭、1：开启）',
                            PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

INSERT  INTO `sys_user`(`id`,`username`,`password`,`status`) VALUES (1,'zhangsan','$2a$10$M7fmKpMZEkkzrTBiKie.EeAKZhQDrWAltpCA1y/py5AU/8lyiNB8y',0);
INSERT  INTO `sys_user`(`id`,`username`,`password`,`status`) VALUES (2,'lisi','$2a$10$M7fmKpMZEkkzrTBiKie.EeAKZhQDrWAltpCA1y/py5AU/8lyiNB8y',1);
INSERT  INTO `sys_user`(`id`,`username`,`password`,`status`) VALUES (3,'wangwu','$2a$10$M7fmKpMZEkkzrTBiKie.EeAKZhQDrWAltpCA1y/py5AU/8lyiNB8y',2);
INSERT  INTO `sys_user`(`id`,`username`,`password`,`status`) VALUES (4,'zhaoliu','$2a$10$M7fmKpMZEkkzrTBiKie.EeAKZhQDrWAltpCA1y/py5AU/8lyiNB8y',3);
INSERT  INTO `sys_user`(`id`,`username`,`password`,`status`) VALUES (5,'xiaoqi','$2a$10$M7fmKpMZEkkzrTBiKie.EeAKZhQDrWAltpCA1y/py5AU/8lyiNB8y',4);

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
                                 `uid` INT(11) NOT NULL COMMENT '用户编号',
                                 `rid` INT(11) NOT NULL COMMENT '角色编号',
                                 PRIMARY KEY (`uid`,`rid`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

INSERT  INTO `sys_user_role`(`uid`,`rid`) VALUES (1,1);
INSERT  INTO `sys_user_role`(`uid`,`rid`) VALUES (1,3);
INSERT  INTO `sys_user_role`(`uid`,`rid`) VALUES (2,1);
INSERT  INTO `sys_user_role`(`uid`,`rid`) VALUES (2,4);
INSERT  INTO `sys_user_role`(`uid`,`rid`) VALUES (3,1);
INSERT  INTO `sys_user_role`(`uid`,`rid`) VALUES (3,2);
INSERT  INTO `sys_user_role`(`uid`,`rid`) VALUES (3,3);
INSERT  INTO `sys_user_role`(`uid`,`rid`) VALUES (3,4);
