CREATE DATABASE `atguigu _db` CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';
DROP TABLE IF EXISTS ` atguigu _order_1`;
CREATE TABLE `atguigu _order_1` (
                                     `order_id` bigint(20) NOT NULL COMMENT '订单id',
                                     `price` decimal(10, 2) NOT NULL COMMENT '订单价格',
                                     `user_id` bigint(20) NOT NULL COMMENT '下单用户id',
                                     `status` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单状态',
                                     PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = INNODB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;
DROP TABLE IF EXISTS ` atguigu _order_2`;
CREATE TABLE `atguigu _order_2` (
                                     `order_id` bigint(20) NOT NULL COMMENT '订单id',
                                     `price` decimal(10, 2) NOT NULL COMMENT '订单价格',
                                     `user_id` bigint(20) NOT NULL COMMENT '下单用户id',
                                     `status` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单状态',
                                     PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = INNODB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;