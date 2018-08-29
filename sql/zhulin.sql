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

/*Table structure for table `essay_type` */

DROP TABLE IF EXISTS `essay_type`;

CREATE TABLE `essay_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '领域名称',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章领域';

/*Table structure for table `order` */

DROP TABLE IF EXISTS `order`;

CREATE TABLE `order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_code` varchar(255) DEFAULT NULL COMMENT '订单号',
  `order_status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '1' COMMENT '1：待接单 2：已接单 3：待审核 4：待点评 5：商家已完成（已打款）6：取消 7：关闭 8：管理员已完成（已打款）',
  `pay_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_swedish_ci DEFAULT NULL COMMENT '1、微信 2、支付宝 3、银联 4、余额 5、现金 6、chinaPay',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `total` int(11) DEFAULT '0' COMMENT '数量',
  `merchant_price` decimal(20,2) DEFAULT '0.00' COMMENT '商家价格',
  `admin_price` decimal(20,2) DEFAULT '0.00' COMMENT '管理员价格',
  `eassy_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文章领域',
  `notes` varchar(1024) DEFAULT NULL COMMENT '备注',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `order_title` varchar(255) DEFAULT NULL COMMENT '文章标题',
  `original_level` double(10,2) DEFAULT '0.00' COMMENT '原创度',
  `picture` int(3) DEFAULT NULL COMMENT '图片数量要求',
  `type` tinyint(1) DEFAULT NULL COMMENT '0-流量文，1-养号文',
  `end_time` timestamp NULL DEFAULT NULL COMMENT '截止日期',
  `require` varchar(1024) DEFAULT NULL COMMENT '要求',
  `eassy_total` int(3) DEFAULT NULL COMMENT '文章数量',
  `word_count` int(8) DEFAULT NULL COMMENT '文章字数',
  `open_user` int(11) DEFAULT NULL COMMENT '指定用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25732 DEFAULT CHARSET=utf8;

/*Table structure for table `order_eassy` */

DROP TABLE IF EXISTS `order_eassy`;

CREATE TABLE `order_eassy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_order_id` int(11) NOT NULL COMMENT '用户订单号id',
  `order_code` varchar(255) DEFAULT NULL COMMENT '订单号',
  `eassy_tital` varchar(255) DEFAULT NULL COMMENT '文章标题',
  `eassy_file` varchar(255) DEFAULT NULL COMMENT '文件路径',
  `original_level` double(10,2) DEFAULT '0.00' COMMENT '原创度',
  `picture_total` int(5) DEFAULT NULL COMMENT '图片数量',
  `picture_id` int(11) DEFAULT NULL COMMENT '图片id',
  `notes` varchar(1024) DEFAULT NULL COMMENT '备注',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(1) DEFAULT '0' COMMENT '0-待审核，1-',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `permission` */

DROP TABLE IF EXISTS `permission`;

CREATE TABLE `permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限id',
  `code` varchar(50) NOT NULL COMMENT '权限码',
  `name` varchar(50) DEFAULT NULL COMMENT '权限名',
  `description` varchar(100) DEFAULT NULL COMMENT '权限描述',
  `system_id` int(11) NOT NULL COMMENT '系统id',
  `pid` int(11) NOT NULL COMMENT '父id',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_code_system` (`code`,`system_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `picture` */

DROP TABLE IF EXISTS `picture`;

CREATE TABLE `picture` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `picture_pixel` varchar(30) NOT NULL COMMENT '图片像素',
  `picture_name` varchar(255) DEFAULT NULL COMMENT '图片名称',
  `order_eassy_id` int(11) DEFAULT NULL COMMENT '文章id',
  `notes` varchar(1024) DEFAULT NULL COMMENT '备注',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `name` varchar(50) NOT NULL COMMENT '角色名称',
  `enabled` bit(1) NOT NULL DEFAULT b'1',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `role_permission` */

DROP TABLE IF EXISTS `role_permission`;

CREATE TABLE `role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色权限id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `permission_id` int(11) NOT NULL COMMENT '权限id',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
  `enabled` int(2) NOT NULL DEFAULT '1' COMMENT '1-待提交信息，2-待审核，3-审核成功，4-审核失败，5-禁用',
  `credit_level` int(11) DEFAULT NULL COMMENT '信用等级',
  `type` int(4) NOT NULL COMMENT '用户身份(1-超级管理员，2-管理员，3-商家，4-写手)',
  `pid` int(11) DEFAULT NULL COMMENT '用户所属id',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `number` varchar(60) DEFAULT NULL COMMENT '编号',
  `remark` text COMMENT '备注',
  `sex` int(2) DEFAULT NULL COMMENT '性别：0-男，1-女',
  `age` int(2) DEFAULT NULL COMMENT '年龄',
  `profession` varchar(50) DEFAULT NULL COMMENT '职业',
  `good` varchar(100) DEFAULT NULL COMMENT '擅长领域',
  `full_time` int(2) DEFAULT NULL COMMENT '0-全职，1-兼职',
  `pay_picture` varchar(100) DEFAULT NULL COMMENT '支付宝二维码',
  `start_level` int(1) DEFAULT NULL COMMENT '用户星级别',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_account` (`account`),
  UNIQUE KEY `uk_number` (`number`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Table structure for table `user_order` */

DROP TABLE IF EXISTS `user_order`;

CREATE TABLE `user_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL COMMENT '订单号id',
  `order_code` varchar(255) DEFAULT NULL COMMENT '订单号',
  `order_status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '1' COMMENT '1：待完成 2：已完成',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `user_total` int(11) DEFAULT '0' COMMENT '已上传数量',
  `notes` varchar(1024) DEFAULT NULL COMMENT '备注',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `user_permission` */

DROP TABLE IF EXISTS `user_permission`;

CREATE TABLE `user_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户权限id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `permission_id` int(11) NOT NULL COMMENT '权限id',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `user_role` */

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户角色id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
