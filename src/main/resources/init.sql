/*
SQLyog  v12.2.6 (64 bit)
MySQL - 8.0.12 : Database - simon
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`simon` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin */;

USE `simon`;

/*Table structure for table `account` */

DROP TABLE IF EXISTS `account`;

CREATE TABLE `account` (
  `account_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '用户名',
  `password` varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '密码',
  `type` char(2) COLLATE utf8mb4_bin NOT NULL DEFAULT '0' COMMENT '类型（-1超级管理员0管理员1学生2教师）',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除0否1是',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`account_id`),
  UNIQUE KEY `AK_Key_2` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='登录账户（类型有学生、教师、管理员、超级管理员）';

/*Data for the table `account` */

insert  into `account`(`account_id`,`username`,`password`,`type`,`deleted`,`create_time`,`update_time`) values
(1,'simon','73f3d160244af05fbede927fb94319f6','1',0,'2018-09-10 08:32:34','2018-09-11 07:56:47'),
(2,'simon1','123456','2',0,'2018-09-10 08:33:00','2018-09-10 08:34:12'),
(3,'simon2','123456','0',0,'2018-09-10 08:33:39','2018-09-10 08:34:15'),
(4,'simon3','123456','-1',0,'2018-09-10 08:33:45','2018-09-10 08:34:09'),
(5,'u1','123456','1',0,'2018-09-11 06:47:44','2018-09-11 06:48:01'),
(6,'u2','123456','1',0,'2018-09-11 06:47:48','2018-09-11 06:48:06'),
(7,'u3','123456','1',0,'2018-09-11 06:47:56','2018-09-11 06:48:12');

/*Table structure for table `class_course` */

DROP TABLE IF EXISTS `class_course`;

CREATE TABLE `class_course` (
  `course_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `seat_id` bigint(20) NOT NULL COMMENT '座位结构id',
  `account_id` bigint(20) NOT NULL COMMENT '账户id',
  `type_id` bigint(20) NOT NULL COMMENT '类型id',
  `course_name` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '课程名字',
  `course_cost` double(10,2) NOT NULL COMMENT '课程费用',
  `course_start_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '课程开始时间',
  `course_end_time` datetime NOT NULL COMMENT '结束时间',
  `course_status` char(2) COLLATE utf8mb4_bin NOT NULL COMMENT '状态（-1取消0新建未发布1已发布2进行中3结束）',
  `course_abstract` text COLLATE utf8mb4_bin COMMENT '课程介绍',
  `course_remark` text COLLATE utf8mb4_bin COMMENT '备注',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除0否1是',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`course_id`),
  KEY `FK_Reference_16` (`account_id`),
  KEY `FK_Reference_3` (`seat_id`),
  KEY `FK_Reference_19` (`type_id`),
  CONSTRAINT `FK_Reference_16` FOREIGN KEY (`account_id`) REFERENCES `account` (`account_id`),
  CONSTRAINT `FK_Reference_19` FOREIGN KEY (`type_id`) REFERENCES `course_type` (`type_id`),
  CONSTRAINT `FK_Reference_3` FOREIGN KEY (`seat_id`) REFERENCES `seat_layout` (`seat_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='课程/班级';

/*Data for the table `class_course` */

insert  into `class_course`(`course_id`,`seat_id`,`account_id`,`type_id`,`course_name`,`course_cost`,`course_start_time`,`course_end_time`,`course_status`,`course_abstract`,`course_remark`,`deleted`,`create_time`,`update_time`) values
(1,1,2,2,'八年级下',500.00,'2018-09-11 13:59:25','2018-10-07 13:59:29','0','八年级下啊八年级下啊八年级下啊八年级下啊八年级下啊八年级下啊','备注备注备注备注备注备注备注备注备注备注',0,'2018-09-11 06:00:18','2018-09-11 06:41:03');

/*Table structure for table `course_order` */

DROP TABLE IF EXISTS `course_order`;

CREATE TABLE `course_order` (
  `order_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '订单编号',
  `course_id` bigint(20) NOT NULL COMMENT '课程id',
  `account_id` bigint(20) NOT NULL COMMENT '账户id',
  `order_status` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '0' COMMENT '状态(0未支付1成功2部分退款)',
  `order_cost` decimal(12,2) NOT NULL COMMENT '支付金额',
  `order_pay_status` char(2) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '支付状态',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`order_id`),
  UNIQUE KEY `AK_order_no_unique` (`order_no`),
  KEY `FK_Reference_18` (`account_id`),
  KEY `FK_Reference_4` (`course_id`),
  CONSTRAINT `FK_Reference_18` FOREIGN KEY (`account_id`) REFERENCES `account` (`account_id`),
  CONSTRAINT `FK_Reference_4` FOREIGN KEY (`course_id`) REFERENCES `class_course` (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='课程订单';

/*Data for the table `course_order` */

/*Table structure for table `course_roster` */

DROP TABLE IF EXISTS `course_roster`;

CREATE TABLE `course_roster` (
  `roster_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `course_id` bigint(20) NOT NULL COMMENT '课程id',
  `seat_id` bigint(20) NOT NULL COMMENT '座位结构id',
  `account_id` bigint(20) NOT NULL COMMENT '账户id',
  `roster_name` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '名单名称',
  `roster_course_count_rest` int(11) NOT NULL COMMENT '剩余课程总数',
  `roster_seat_x` int(11) NOT NULL COMMENT '座位坐标x',
  `roster_seat_y` int(11) NOT NULL COMMENT '座位坐标y',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`roster_id`),
  UNIQUE KEY `roster_keys` (`course_id`,`seat_id`),
  KEY `FK_Reference_12` (`account_id`),
  KEY `FK_Reference_7` (`seat_id`),
  CONSTRAINT `FK_Reference_12` FOREIGN KEY (`account_id`) REFERENCES `account` (`account_id`),
  CONSTRAINT `FK_Reference_5` FOREIGN KEY (`course_id`) REFERENCES `class_course` (`course_id`),
  CONSTRAINT `FK_Reference_7` FOREIGN KEY (`seat_id`) REFERENCES `seat_layout` (`seat_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='记录课程的名单以及学生课时';

/*Data for the table `course_roster` */

/*Table structure for table `course_type` */

DROP TABLE IF EXISTS `course_type`;

CREATE TABLE `course_type` (
  `type_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type_name` varchar(200) COLLATE utf8mb4_bin NOT NULL COMMENT '类型名称',
  `type_series` bigint(20) NOT NULL COMMENT '类型系列0代表一个系列其他代表某系列下的一个类别',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除0否1是',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='课程类型:数学、语文、英语等等';

/*Data for the table `course_type` */

insert  into `course_type`(`type_id`,`type_name`,`type_series`,`deleted`,`create_time`,`update_time`) values
(1,'新类目系列',0,0,'2018-09-11 06:40:19','2018-09-11 06:40:19'),
(2,'朗文直通车类别',0,0,'2018-09-11 06:40:39','2018-09-11 06:43:26'),
(3,'剑桥English IN Mind  类别',0,0,'2018-09-11 06:43:36','2018-09-11 06:43:36'),
(4,'物理类别',0,0,'2018-09-11 06:43:47','2018-09-11 06:43:47'),
(5,'语文类别',0,0,'2018-09-11 06:43:58','2018-09-11 06:43:58'),
(6,'青少新概念类别',0,0,'2018-09-11 06:44:13','2018-09-11 06:44:13'),
(7,'七年级上册',1,0,'2018-09-11 06:44:23','2018-09-11 06:44:23'),
(8,'七年级下册',1,0,'2018-09-11 06:44:31','2018-09-11 06:44:31'),
(9,'八年级上册',1,0,'2018-09-11 06:44:34','2018-09-11 06:44:34'),
(10,'八年级下册',1,0,'2018-09-11 06:44:37','2018-09-11 06:44:37'),
(11,'九年级上册',1,0,'2018-09-11 06:44:42','2018-09-11 06:44:42'),
(12,'九年级下册',1,0,'2018-09-11 06:44:47','2018-09-11 06:44:47'),
(13,'朗文直通车一级 1A',2,0,'2018-09-11 06:44:53','2018-09-11 06:44:53'),
(14,'朗文直通车一级 1B',2,0,'2018-09-11 06:44:59','2018-09-11 06:44:59'),
(15,'朗文直通车二级 2A',2,0,'2018-09-11 06:45:05','2018-09-11 06:45:05'),
(16,'朗文直通车二级 2B',2,0,'2018-09-11 06:45:10','2018-09-11 06:45:10'),
(17,'朗文直通车三级 3A',2,0,'2018-09-11 06:45:15','2018-09-11 06:45:15'),
(18,'朗文直通车三级3B',2,0,'2018-09-11 06:45:20','2018-09-11 06:45:20'),
(19,'English  IN  Mind预备级',3,0,'2018-09-11 06:45:26','2018-09-11 06:45:26'),
(20,'English  IN  Mind一级',3,0,'2018-09-11 06:45:32','2018-09-11 06:45:32'),
(21,'English  IN  Mind二级',3,0,'2018-09-11 06:45:36','2018-09-11 06:45:36'),
(22,'English  IN  Mind三级',3,0,'2018-09-11 06:45:41','2018-09-11 06:45:41'),
(23,'八年级上',4,0,'2018-09-11 06:45:46','2018-09-11 06:45:46'),
(24,'八年级下',4,0,'2018-09-11 06:45:51','2018-09-11 06:45:51'),
(25,'九年级上',4,0,'2018-09-11 06:45:56','2018-09-11 06:45:56'),
(26,'九年级下',4,0,'2018-09-11 06:46:00','2018-09-11 06:46:00'),
(27,'中考综合练',4,0,'2018-09-11 06:46:06','2018-09-11 06:46:06'),
(28,'初级语文培优班',5,0,'2018-09-11 06:46:10','2018-09-11 06:46:10'),
(29,'中级语文培优班',5,0,'2018-09-11 06:46:15','2018-09-11 06:46:15'),
(30,'作文专项班',5,0,'2018-09-11 06:46:20','2018-09-11 06:46:20'),
(31,'阅读专项班',5,0,'2018-09-11 06:46:25','2018-09-11 06:46:25'),
(32,'文言文专项班',5,0,'2018-09-11 06:46:29','2018-09-11 06:46:29'),
(33,'中考综合练专项班',5,0,'2018-09-11 06:46:33','2018-09-11 06:46:33'),
(34,'青少新概念一级 1A',6,0,'2018-09-11 06:46:41','2018-09-11 06:46:41'),
(35,'青少新概念一级 1B',6,0,'2018-09-11 06:46:46','2018-09-11 06:46:46'),
(36,'青少新概念二级 2A',6,0,'2018-09-11 06:46:50','2018-09-11 06:46:50'),
(37,'青少新概念二级 2B',6,0,'2018-09-11 06:46:54','2018-09-11 06:46:54'),
(38,'直通车预备级',6,0,'2018-09-11 06:46:57','2018-09-11 06:46:57');

/*Table structure for table `jurisdiction` */

DROP TABLE IF EXISTS `jurisdiction`;

CREATE TABLE `jurisdiction` (
  `jn_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `jn_name` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '权限名称',
  `jn_url` varchar(500) COLLATE utf8mb4_bin NOT NULL COMMENT '具体操作权限',
  `jn_pid` bigint(20) NOT NULL DEFAULT '0' COMMENT '父级id',
  `jn_path` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '所有父级id集合',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`jn_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='角色权限表';

/*Data for the table `jurisdiction` */

insert  into `jurisdiction`(`jn_id`,`jn_name`,`jn_url`,`jn_pid`,`jn_path`,`create_time`,`update_time`) values
(1,'课程','',0,NULL,'2018-09-10 08:58:56','2018-09-10 08:58:56'),
(2,'列表','/api/course/list',1,'1','2018-09-10 08:59:18','2018-09-10 09:00:07'),
(3,'修改','/api/course/update',1,'1','2018-09-10 08:59:47','2018-09-10 09:00:08'),
(4,'新增','/api/course/add',1,'1','2018-09-10 09:00:00','2018-09-10 09:00:17'),
(5,'删除','/api/course/delete',1,'1','2018-09-10 09:00:43','2018-09-10 09:00:43');

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_name` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '角色名称',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='用户角色表';

/*Data for the table `role` */

insert  into `role`(`role_id`,`role_name`,`create_time`,`update_time`) values
(1,'student','2018-09-10 08:31:03','2018-09-10 08:31:20'),
(2,'teacher','2018-09-10 08:31:14','2018-09-10 08:31:14'),
(3,'manager','2018-09-10 08:31:30','2018-09-10 08:31:30'),
(4,'admin','2018-09-10 08:31:32','2018-09-10 08:31:32');

/*Table structure for table `role_jn` */

DROP TABLE IF EXISTS `role_jn`;

CREATE TABLE `role_jn` (
  `role_jn_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `jn_id` bigint(20) NOT NULL COMMENT '权限id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`role_jn_id`),
  KEY `FK_Reference_13` (`jn_id`),
  KEY `FK_Reference_14` (`role_id`),
  CONSTRAINT `FK_Reference_13` FOREIGN KEY (`jn_id`) REFERENCES `jurisdiction` (`jn_id`),
  CONSTRAINT `FK_Reference_14` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='角色权限关系';

/*Data for the table `role_jn` */

insert  into `role_jn`(`role_jn_id`,`role_id`,`jn_id`,`create_time`,`update_time`) values
(1,1,1,'2018-09-10 09:01:05','2018-09-10 09:01:05'),
(2,1,2,'2018-09-10 09:01:11','2018-09-10 09:01:11'),
(3,2,1,'2018-09-10 09:01:18','2018-09-10 09:01:18'),
(4,2,2,'2018-09-10 09:01:21','2018-09-10 09:01:21'),
(5,3,1,'2018-09-10 09:01:25','2018-09-10 09:01:25'),
(6,3,2,'2018-09-10 09:01:28','2018-09-10 09:01:28'),
(7,4,1,'2018-09-10 09:01:42','2018-09-10 09:01:42'),
(8,4,2,'2018-09-10 09:01:46','2018-09-10 09:01:46'),
(9,4,3,'2018-09-10 09:01:49','2018-09-10 09:01:49'),
(10,4,4,'2018-09-10 09:01:51','2018-09-10 09:01:51');

/*Table structure for table `roster_attendance` */

DROP TABLE IF EXISTS `roster_attendance`;

CREATE TABLE `roster_attendance` (
  `attendance_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `course_id` bigint(20) NOT NULL COMMENT '课程id',
  `account_id` bigint(20) NOT NULL COMMENT '账户id',
  `attend_section_num` int(11) NOT NULL DEFAULT '1' COMMENT '第几课时、课程章节数',
  `attend_name` varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '考勤名称',
  `attend_remark` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`attendance_id`),
  UNIQUE KEY `attendance_keys` (`course_id`,`attend_section_num`),
  KEY `FK_Reference_17` (`account_id`),
  CONSTRAINT `FK_Reference_17` FOREIGN KEY (`account_id`) REFERENCES `account` (`account_id`),
  CONSTRAINT `FK_Reference_8` FOREIGN KEY (`course_id`) REFERENCES `class_course` (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='课程名单考勤记录';

/*Data for the table `roster_attendance` */

/*Table structure for table `seat_layout` */

DROP TABLE IF EXISTS `seat_layout`;

CREATE TABLE `seat_layout` (
  `seat_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `seat_left` int(12) NOT NULL COMMENT '左边区域数量',
  `seat_mid` int(12) NOT NULL COMMENT '中间区域数量',
  `seat_right` int(12) NOT NULL COMMENT '右边区域数量',
  `seat_rows` int(12) NOT NULL COMMENT '总行数',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除0否1是',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`seat_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='座位结构、布局';

/*Data for the table `seat_layout` */

insert  into `seat_layout`(`seat_id`,`seat_left`,`seat_mid`,`seat_right`,`seat_rows`,`deleted`,`create_time`,`update_time`) values
(1,3,3,3,5,0,'2018-09-11 05:57:03','2018-09-11 05:57:03'),
(2,3,0,3,6,0,'2018-09-11 05:57:15','2018-09-11 05:57:15'),
(3,3,0,3,5,0,'2018-09-11 05:57:28','2018-09-11 05:57:28'),
(4,2,0,2,5,0,'2018-09-11 05:57:38','2018-09-11 05:57:38'),
(5,3,0,2,5,0,'2018-09-11 05:57:48','2018-09-11 05:57:48'),
(6,3,0,3,4,0,'2018-09-11 05:57:56','2018-09-11 05:57:56');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `account_id` bigint(20) NOT NULL COMMENT '登录账户id',
  `name` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '姓名',
  `sex` char(2) COLLATE utf8mb4_bin NOT NULL DEFAULT '0' COMMENT '性别（0男1女）',
  `age` int(11) NOT NULL COMMENT '年龄',
  `phone` varchar(11) COLLATE utf8mb4_bin NOT NULL COMMENT '手机号',
  `card_num` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '身份证',
  `address` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '住址',
  `type` char(2) COLLATE utf8mb4_bin NOT NULL DEFAULT '1' COMMENT '类型(1学生2老师)',
  `remark` varchar(1000) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `AK_account_id_unique` (`account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='用户类型有教师、学生';

/*Data for the table `user` */

insert  into `user`(`user_id`,`account_id`,`name`,`sex`,`age`,`phone`,`card_num`,`address`,`type`,`remark`,`create_time`,`update_time`) values
(1,1,'simon','0',20,'15919161025','4417824511748114','珠海','1',NULL,'2018-09-10 08:25:52','2018-09-10 08:25:52'),
(2,5,'u1','0',0,'18933897092','8556456788412145',NULL,'1',NULL,'2018-09-11 07:00:41','2018-09-11 07:00:41'),
(3,6,'u2','0',0,'13421266952','4616574894561564',NULL,'1',NULL,'2018-09-11 07:01:33','2018-09-11 07:01:33'),
(4,7,'u3','0',12,'18833587895','7894564121234544',NULL,'1',NULL,'2018-09-11 07:01:58','2018-09-11 07:01:58');

/*Table structure for table `user_role` */

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `user_role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `account_id` bigint(20) NOT NULL COMMENT '登录账户id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`user_role_id`),
  KEY `FK_Reference_11` (`role_id`),
  KEY `FK_Reference_15` (`account_id`),
  CONSTRAINT `FK_Reference_11` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`),
  CONSTRAINT `FK_Reference_15` FOREIGN KEY (`account_id`) REFERENCES `account` (`account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='用户角色表';

/*Data for the table `user_role` */

insert  into `user_role`(`user_role_id`,`role_id`,`account_id`,`create_time`,`update_time`) values
(1,1,1,'2018-09-10 08:32:46','2018-09-10 08:32:46'),
(2,2,2,'2018-09-10 08:34:44','2018-09-10 08:34:44'),
(3,3,3,'2018-09-10 08:34:48','2018-09-10 08:34:48'),
(4,4,4,'2018-09-10 08:34:50','2018-09-10 08:34:50');

CREATE TABLE roster_income
(
   income_id            BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键' PRIMARY KEY,
   account_id           BIGINT NOT NULL COMMENT '教师id',
   course_id            BIGINT COMMENT '课程id',
   income_section_num   INT NOT NULL DEFAULT 1 COMMENT '第几课时、课程章节数',
   income_type          CHAR(2) NOT NULL COMMENT '收入类型（1按时收入2按分成）',
   must_number          INT NOT NULL COMMENT '应到人数',
   actual_number        INT NOT NULL COMMENT '实到人数',
   total_hour           DECIMAL(6,2) NOT NULL DEFAULT 0 COMMENT '上课总时间',
   average_hour         DECIMAL(6,1) COMMENT '每【average_hour】时起',
   average_hour_cost    DECIMAL(6,2) COMMENT '每【average_hour】时收费【average_hour_cost】元',
   percentage           DECIMAL(3,2) DEFAULT 0 COMMENT '百分点【teacher_charge_type】为1时：超过【exceed_num】人数提成【percentage】。为2时：每节课的提成百分比',
   exceed_num           INT COMMENT '超过【exceed_num】人数提成【percentage】',
   average_course       DECIMAL(6,2) DEFAULT 0 COMMENT '课程每节收费',
   income_amount        DECIMAL(6,2) NOT NULL DEFAULT 0 COMMENT '收入金额',
   create_time          DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   update_time          DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间'
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='教师课程收入';

ALTER TABLE roster_income ADD CONSTRAINT FK_Reference_20 FOREIGN KEY (course_id)
      REFERENCES class_course (course_id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE roster_income ADD CONSTRAINT FK_Reference_21 FOREIGN KEY (account_id)
      REFERENCES account (account_id) ON DELETE RESTRICT ON UPDATE RESTRICT;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- user 添加字段
 ALTER TABLE `simon`.`user`
  ADD COLUMN `teacher_charge_type` CHAR(2) NULL COMMENT '老师收费类型(1按时2按提成)' AFTER `type`,
  ADD COLUMN `average_hour` DECIMAL(6,1) NULL COMMENT '每【average_hour】时起' AFTER `teacher_charge_type`,
  ADD COLUMN `average_hour_cost` DECIMAL(6,2) NULL COMMENT '每【average_hour】时起' AFTER `average_hour`,
  ADD COLUMN `percentage` DECIMAL(3,2) NULL COMMENT '百分点【teacher_charge_type】为1时：超过【exceed_num】人数提成【percentage】。为2时：每节课的提成百分比' AFTER `average_hour`,
  ADD COLUMN `exceed_num` INT NULL COMMENT '超过【exceed_num】人数提成【percentage】' AFTER `percentage`;

ALTER TABLE `simon`.`user`
ADD COLUMN `parent_name` varchar(50)   DEFAULT NULL COMMENT '家长姓名'  AFTER `name`;


ALTER TABLE `simon`.`class_course`
  ADD COLUMN `course_current` INT DEFAULT 0 NOT NULL COMMENT '当前课时' AFTER `course_status`,
  ADD COLUMN `course_total` INT NOT NULL COMMENT '总课时' AFTER `course_current`;


ALTER TABLE `simon`.`course_roster`
  DROP COLUMN `seat_id`,
  DROP INDEX `FK_Reference_7`,
  DROP INDEX `roster_keys`,
  ADD  UNIQUE INDEX `roster_keys` (`course_id`),
  DROP FOREIGN KEY `FK_Reference_7`;


ALTER TABLE `simon`.`course_roster`
  DROP COLUMN `course_id`,
  ADD COLUMN `course_id` BIGINT(20) NOT NULL COMMENT '课程id' AFTER `roster_id`,
  DROP INDEX `roster_keys`,
  DROP FOREIGN KEY `FK_Reference_5`;

ALTER TABLE course_roster ADD CONSTRAINT FK_Reference_5 FOREIGN KEY (course_id)
      REFERENCES class_course (course_id) ON DELETE RESTRICT ON UPDATE RESTRICT;


ALTER TABLE `simon`.`user`
  ADD COLUMN `school_name` VARCHAR(50) NULL COMMENT '学校' AFTER `name`,
  ADD COLUMN `grade_name` VARCHAR(50) NULL COMMENT '年级' AFTER `school_name`;

ALTER TABLE `simon`.`roster_attendance`
  ADD COLUMN `end_time` DATETIME NULL COMMENT '下课时间/结束时间' AFTER `attend_remark`;

ALTER TABLE `simon`.`roster_attendance`
  CHANGE `attendance_id` `attendance_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键';

ALTER TABLE `simon`.`roster_attendance`
  CHANGE `attend_name` `attend_name` VARCHAR(100) CHARSET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '考勤名称';

ALTER TABLE `simon`.`roster_attendance`
  ADD COLUMN `attend_type` ENUM('1','2') NOT NULL COMMENT '签到类型1学生2老师' AFTER `account_id`;
ALTER TABLE `simon`.`account`
  CHANGE `type` `type` ENUM('-1','0','1','2') CHARSET utf8mb4 COLLATE utf8mb4_bin DEFAULT '0' NOT NULL COMMENT '类型（-1超级管理员0管理员1学生2教师）';
ALTER TABLE `simon`.`class_course`
  CHANGE `course_status` `course_status` ENUM('-1','0','1','2','3') CHARSET utf8mb4 COLLATE utf8mb4_bin DEFAULT '0' NOT NULL COMMENT '状态（-1取消0新建未发布1已发布2进行中3结束）';
ALTER TABLE `simon`.`course_order`
  CHANGE `order_status` `order_status` ENUM('0','1','2') CHARSET utf8mb4 COLLATE utf8mb4_bin DEFAULT '0' NOT NULL COMMENT '状态(0未支付1成功2部分退款)';
ALTER TABLE `simon`.`roster_income`
  CHANGE `income_type` `income_type` ENUM('1','2') CHARSET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '收入类型（1按时收入2按分成）';
ALTER TABLE `simon`.`user`
  CHANGE `type` `type` ENUM('1','2') CHARSET utf8mb4 COLLATE utf8mb4_bin DEFAULT '1' NOT NULL COMMENT '类型(1学生2老师)',
  CHANGE `teacher_charge_type` `teacher_charge_type` ENUM('1','2') CHARSET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '老师收费类型(1按时2按提成)';



ALTER TABLE `simon`.`roster_attendance`
  DROP INDEX `attendance_keys`,
  ADD  INDEX `FK_Reference_8` (`course_id`);


ALTER TABLE `simon`.`user`
  CHANGE `percentage` `percentage` DECIMAL(6,2) NULL COMMENT '提成点【teacher_charge_type】为1时：超过【exceed_num】人数提成【percentage】。为2时：每节课的提成点';


ALTER TABLE `simon`.`user`
  CHANGE `sex` `sex` ENUM('0','1') CHARSET utf8mb4 COLLATE utf8mb4_bin DEFAULT '0' NOT NULL COMMENT '性别（0男1女）';


ALTER TABLE `simon`.`roster_attendance`
  CHANGE `attend_type` `attend_type` ENUM('1','2','3') CHARSET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '签到类型1学生2老师3串课';


ALTER TABLE `simon`.`roster_attendance`
  ADD  UNIQUE INDEX `unique_key` (`course_id`, `account_id`, `attend_section_num`);


ALTER TABLE `simon`.`roster_attendance`
  ADD COLUMN `additional_seat_x` INT NULL COMMENT '串课位置x坐标' AFTER `end_time`,
  ADD COLUMN `additional_seat_y` INT NULL COMMENT '串课位置y坐标' AFTER `additional_seat_x`;



  ALTER TABLE `simon`.`course_roster`
  ADD  UNIQUE INDEX `roster_unique_key` (`course_id`, `account_id`);



ALTER TABLE `simon`.`course_order`
  CHANGE `order_status` `order_status` ENUM('0','1','2','3') CHARSET utf8mb4 COLLATE utf8mb4_bin DEFAULT '0' NOT NULL COMMENT '状态(0未支付1成功2部分退款3申请退款)';


-- 20181008
ALTER TABLE `simon`.`user`
CHANGE `age` `age` INT(11) NULL COMMENT '年龄',
  ADD COLUMN `birthday` DATE NULL COMMENT '生日' AFTER `sex`;



ALTER TABLE `simon`.`course_order`
  ADD COLUMN `order_pay_way` VARCHAR(20) NULL COMMENT '支付方式' AFTER `order_cost`;
