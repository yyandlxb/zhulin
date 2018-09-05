/*
SQLyog Ultimate v8.32
MySQL - 8.0.12 : Database - zhulin
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`zhulin` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `zhulin`;

/*Table structure for table `limit_time` */

DROP TABLE IF EXISTS `limit_time`;

CREATE TABLE `limit_time` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `limit_time` int(11) DEFAULT NULL COMMENT '限制小时',
  `created_at` timestamp NOT NULL,
  `updated_at` timestamp NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章领域';

/*Data for the table `limit_time` */

/*Table structure for table `order` */

DROP TABLE IF EXISTS `order`;

CREATE TABLE `order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_code` varchar(255) DEFAULT NULL COMMENT '订单号',
  `order_status` tinyint(1) DEFAULT '0' COMMENT '0：待审核 1：发布中 2：已完成 3：待点评 4：商家已打款5：取消 6：关闭 7：管理员已完成（已打款）,8-审核未通过',
  `pay_type` tinyint(1) DEFAULT NULL COMMENT '1、微信 2、支付宝 3、银联 4、余额 5、现金 6、chinaPay',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `total` int(11) DEFAULT '0' COMMENT '数量',
  `merchant_price` decimal(20,2) DEFAULT '0.00' COMMENT '商家价格',
  `admin_price` decimal(20,2) DEFAULT '0.00' COMMENT '管理员价格',
  `eassy_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文章领域',
  `notes` varchar(1024) DEFAULT NULL COMMENT '备注',
  `created_at` timestamp NOT NULL,
  `updated_at` timestamp NOT NULL,
  `order_title` varchar(255) DEFAULT NULL COMMENT '文章标题',
  `original_level` double(10,2) DEFAULT '0.00' COMMENT '原创度',
  `picture` int(3) DEFAULT NULL COMMENT '图片数量要求',
  `type` tinyint(1) DEFAULT NULL COMMENT '0-流量文，1-养号文',
  `end_time` timestamp NULL DEFAULT NULL COMMENT '截止日期',
  `require` varchar(1024) DEFAULT NULL COMMENT '要求',
  `eassy_total` int(3) DEFAULT NULL COMMENT '文章数量',
  `word_count` varchar(8) DEFAULT NULL COMMENT '文章字数',
  `result` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '审核结果',
  `appoint_total` int(2) DEFAULT NULL COMMENT '已预约数量',
  `admin_end_time` timestamp NULL DEFAULT NULL COMMENT '管理员设定的截稿时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25736 DEFAULT CHARSET=utf8;

/*Data for the table `order` */

insert  into `order`(`id`,`order_code`,`order_status`,`pay_type`,`user_id`,`total`,`merchant_price`,`admin_price`,`eassy_type`,`notes`,`created_at`,`updated_at`,`order_title`,`original_level`,`picture`,`type`,`end_time`,`require`,`eassy_total`,`word_count`,`result`,`appoint_total`,`admin_end_time`) values (25731,'33',1,NULL,NULL,0,'0.00','3.00','1',NULL,'2018-08-28 19:28:36','2018-08-28 19:30:02',NULL,322.00,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(25733,'1535791959241743',0,NULL,2,3,'12.00','0.00','aaa',NULL,'2018-09-01 16:52:39','2018-09-01 17:04:13','bbb',NULL,NULL,NULL,'2018-09-13 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `order_essay` */

DROP TABLE IF EXISTS `order_essay`;

CREATE TABLE `order_essay` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_order_id` int(11) NOT NULL COMMENT '用户订单号id',
  `order_code` varchar(255) DEFAULT NULL COMMENT '订单号',
  `essay_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文章标题',
  `eassy_file` varchar(255) DEFAULT NULL COMMENT '文件路径',
  `original_level` double(10,2) DEFAULT '0.00' COMMENT '原创度',
  `picture_total` int(5) DEFAULT NULL COMMENT '图片数量',
  `picture_id` int(11) DEFAULT NULL COMMENT '图片id',
  `notes` varchar(1024) DEFAULT NULL COMMENT '备注',
  `created_at` timestamp NOT NULL,
  `updated_at` timestamp NOT NULL,
  `status` tinyint(1) DEFAULT '0' COMMENT '0-待管理员审核，1-商家退稿，2-收稿成功，3-商家已打款，4待商家审核，5商家退稿，6管理员已打款',
  `result` varchar(1024) DEFAULT NULL COMMENT '审核结果',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `order_essay` */

/*Table structure for table `permission` */

DROP TABLE IF EXISTS `permission`;

CREATE TABLE `permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限id',
  `code` varchar(50) NOT NULL COMMENT '权限码',
  `name` varchar(50) DEFAULT NULL COMMENT '权限名',
  `description` varchar(100) DEFAULT NULL COMMENT '权限描述',
  `system_id` int(11) NOT NULL COMMENT '系统id',
  `pid` int(11) NOT NULL COMMENT '父id',
  `created_at` timestamp NOT NULL,
  `updated_at` timestamp NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_code_system` (`code`,`system_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `permission` */

/*Table structure for table `picture` */

DROP TABLE IF EXISTS `picture`;

CREATE TABLE `picture` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `picture_pixel` varchar(30) NOT NULL COMMENT '图片像素',
  `picture_name` varchar(255) DEFAULT NULL COMMENT '图片名称',
  `order_eassy_id` int(11) DEFAULT NULL COMMENT '文章id',
  `notes` varchar(1024) DEFAULT NULL COMMENT '备注',
  `created_at` timestamp NOT NULL,
  `updated_at` timestamp NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `picture` */

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `name` varchar(50) NOT NULL COMMENT '角色名称',
  `enabled` bit(1) NOT NULL DEFAULT b'1',
  `created_at` timestamp NOT NULL,
  `updated_at` timestamp NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `role` */

/*Table structure for table `role_permission` */

DROP TABLE IF EXISTS `role_permission`;

CREATE TABLE `role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色权限id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `permission_id` int(11) NOT NULL COMMENT '权限id',
  `created_at` timestamp NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `role_permission` */

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `account` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL COMMENT '用户名称',
  `password` varchar(50) NOT NULL COMMENT '用户密码',
  `qq` varchar(15) DEFAULT NULL COMMENT 'qq号',
  `wechat` varchar(50) DEFAULT NULL COMMENT '微信号',
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户编码',
  `address` varchar(255) DEFAULT NULL COMMENT '用户地址',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1-待提交信息，2-待审核，3-审核成功，4-审核失败，5-禁用',
  `credit_level` int(11) DEFAULT '100' COMMENT '信用等级',
  `type` tinyint(1) NOT NULL COMMENT '用户身份(1-超级管理员，2-管理员，3-商家，4-写手)',
  `pid` int(11) DEFAULT NULL COMMENT '用户所属id',
  `created_at` timestamp NOT NULL,
  `updated_at` timestamp NOT NULL,
  `number` varchar(60) DEFAULT NULL COMMENT '编号',
  `remark` text COMMENT '备注',
  `sex` int(2) DEFAULT NULL COMMENT '性别：0-男，1-女',
  `age` int(2) DEFAULT NULL COMMENT '年龄',
  `profession` varchar(50) DEFAULT NULL COMMENT '职业',
  `good` varchar(100) DEFAULT NULL COMMENT '擅长领域',
  `full_time` int(1) DEFAULT NULL COMMENT '0-全职，1-兼职',
  `pay_picture` varchar(100) DEFAULT NULL COMMENT '支付宝二维码',
  `start_level` int(1) DEFAULT NULL COMMENT '用户星级别',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `result` varchar(255) DEFAULT NULL COMMENT '审核结果',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_account` (`account`),
  UNIQUE KEY `uk_number` (`number`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`id`,`account`,`name`,`password`,`qq`,`wechat`,`code`,`address`,`status`,`credit_level`,`type`,`pid`,`created_at`,`updated_at`,`number`,`remark`,`sex`,`age`,`profession`,`good`,`full_time`,`pay_picture`,`start_level`,`email`,`result`) values (2,'15736883328','ayao','e10adc3949ba59abbe56e057f20f883e',NULL,NULL,NULL,NULL,1,NULL,1,NULL,'2018-09-01 16:13:05','2018-09-01 16:13:05',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `user_order` */

DROP TABLE IF EXISTS `user_order`;

CREATE TABLE `user_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_code` varchar(255) DEFAULT NULL COMMENT '订单号',
  `reserve_total` int(5) NOT NULL COMMENT '预定的文章数量',
  `complete` int(5) NOT NULL COMMENT '完成文章数量',
  `created_at` timestamp NOT NULL,
  `updated_at` timestamp NOT NULL,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `status` tinyint(1) DEFAULT NULL COMMENT '0待预约，1-预约成功',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user_order` */

/*Table structure for table `user_permission` */

DROP TABLE IF EXISTS `user_permission`;

CREATE TABLE `user_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户权限id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `permission_id` int(11) NOT NULL COMMENT '权限id',
  `created_at` timestamp NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user_permission` */

/*Table structure for table `user_role` */

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户角色id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `create_at` timestamp NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user_role` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
