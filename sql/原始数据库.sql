/*
SQLyog Ultimate v11.27 (32 bit)
MySQL - 8.0.26 : Database - db_authority_system
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`db_authority_system` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `db_authority_system`;

/*Table structure for table `sys_department` */

DROP TABLE IF EXISTS `sys_department`;

CREATE TABLE `sys_department` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '部门编号',
  `department_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '部门名称',
  `phone` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '部门电话',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '部门地址',
  `pid` bigint NOT NULL COMMENT '所属部门编号',
  `parent_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '所属部门名称',
  `order_num` int DEFAULT NULL COMMENT '排序',
  `is_delete` tinyint DEFAULT '0' COMMENT '是否删除(0-未删除 1-已删除)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;

/*Data for the table `sys_department` */

insert  into `sys_department`(`id`,`department_name`,`phone`,`address`,`pid`,`parent_name`,`order_num`,`is_delete`) values (1,'广州码农信息技术有限公司','020-8888888','广州市天河区',0,'顶级部门',0,0),(2,'软件技术部','020-88881001','广州市天河区',1,'广州码农信息技术有限公司',1,0),(3,'人事管理部','020-88881002','广州市天河区',1,'广州码农信息技术有限公司',1,0),(4,'市场管理部','020-88881003','广州市天河区',1,'广州码农信息技术有限公司',1,0),(5,'软件研发部','020-88881234','广州市天河区',1,'广州码农信息技术有限公司',2,0),(6,'Java技术部','020-12345678','广州市天河区',2,'软件技术部',NULL,1);

/*Table structure for table `sys_permission` */

DROP TABLE IF EXISTS `sys_permission`;

CREATE TABLE `sys_permission` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '权限编号',
  `label` varchar(50) DEFAULT NULL COMMENT '权限名称',
  `parent_id` bigint DEFAULT NULL COMMENT '父权限ID',
  `parent_name` varchar(50) DEFAULT NULL COMMENT '父权限名称',
  `code` varchar(50) DEFAULT NULL COMMENT '授权标识符',
  `path` varchar(100) DEFAULT NULL COMMENT '路由地址',
  `name` varchar(50) DEFAULT NULL COMMENT '路由名称',
  `url` varchar(100) DEFAULT NULL COMMENT '授权路径',
  `type` tinyint DEFAULT NULL COMMENT '权限类型(0-目录 1-菜单 2-按钮)',
  `icon` varchar(50) DEFAULT NULL COMMENT '图标',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `order_num` int DEFAULT NULL COMMENT '排序',
  `is_delete` tinyint DEFAULT '0' COMMENT '是否删除(0-未删除，1-已删除)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb3;

/*Data for the table `sys_permission` */

insert  into `sys_permission`(`id`,`label`,`parent_id`,`parent_name`,`code`,`path`,`name`,`url`,`type`,`icon`,`create_time`,`update_time`,`remark`,`order_num`,`is_delete`) values (1,'系统管理',0,'顶级菜单','sys:manager','/system','system','/system/system',0,'el-icon-menu','2022-04-25 14:40:32','2022-04-25 14:40:32',NULL,0,0),(2,'部门管理',1,'系统管理','sys:department','/department','department','/system/department/department',1,'el-icon-s-tools','2022-04-25 14:40:32','2022-04-25 14:40:32',NULL,NULL,0),(3,'新增',2,'部门管理','sys:department:add',NULL,NULL,NULL,2,'el-icon-plus','2022-04-25 14:40:32','2022-04-25 14:40:32',NULL,NULL,0),(4,'修改',2,'部门管理','sys:department:edit',NULL,NULL,NULL,2,'el-icon-edit','2022-04-25 14:40:32','2022-04-25 14:40:32',NULL,NULL,0),(5,'删除',2,'部门管理','sys:department:delete',NULL,NULL,NULL,2,'el-icon-delete','2022-04-25 14:40:32','2022-04-25 14:40:32',NULL,NULL,0),(6,'用户管理',1,'系统管理','sys:user','/userList','userList','/system/user/userList',1,'el-icon-s-custom','2022-04-25 14:40:32','2022-04-25 14:40:32',NULL,NULL,0),(7,'新增',6,'用户管理','sys:user:add',NULL,NULL,NULL,2,'el-icon-plus','2022-04-25 14:40:32','2022-04-25 14:40:32',NULL,NULL,0),(8,'修改',6,'用户管理','sys:user:edit',NULL,NULL,NULL,2,'el-icon-edit','2022-04-25 14:40:32','2022-04-25 14:40:32',NULL,NULL,0),(9,'删除',6,'用户管理','sys:user:delete',NULL,NULL,NULL,2,'el-icon-delete','2022-04-25 14:40:32','2022-04-25 14:40:32',NULL,NULL,0),(10,'角色管理',1,'系统管理','sys:role','/roleList','roleList','/system/role/roleList',1,'el-icon-s-tools','2022-04-25 14:40:32','2022-04-25 14:40:32',NULL,NULL,0),(11,'新增',10,'角色管理','sys:role:add',NULL,NULL,NULL,2,'el-icon-plus','2022-04-25 14:40:32','2022-04-25 14:40:32',NULL,NULL,0),(12,'修改',10,'角色管理','sys:role:edit',NULL,NULL,NULL,2,'el-icon-edit','2022-04-25 14:40:32','2022-04-25 14:40:32',NULL,NULL,0),(13,'删除',10,'角色管理','sys:role:delete',NULL,NULL,NULL,2,'el-icon-delete','2022-04-25 14:40:32','2022-04-25 14:40:32',NULL,NULL,0),(14,'菜单管理',1,'系统管理','sys:menu','/menuList','menuList','/system/menu/menuList',1,'el-icon-s-tools','2022-04-25 14:40:32','2022-04-25 14:40:32',NULL,NULL,0),(15,'新增',14,'权限管理','sys:menu:add',NULL,NULL,NULL,2,'el-icon-plus','2022-04-25 14:40:32','2022-04-25 14:40:32',NULL,NULL,0),(16,'修改',14,'权限管理','sys:menu:edit',NULL,NULL,NULL,2,'el-icon-edit','2022-04-25 14:40:32','2022-04-25 14:40:32',NULL,NULL,0),(17,'删除',14,'权限管理','sys:menu:delete',NULL,NULL,NULL,2,'el-icon-delete','2022-04-25 14:40:32','2022-04-25 14:40:32',NULL,NULL,0),(18,'资料管理',0,'顶级菜单','sys:resource','/resource','resource','/resource/index',0,'el-icon-menu','2022-04-25 14:40:32','2022-04-25 14:40:32',NULL,2,0),(19,'供应商管理',18,'资料管理','sys:provider','/providerList','providerList','/system/provider/providerList',1,'el-icon-s-tools','2022-04-25 14:40:32','2022-04-25 14:40:32',NULL,2,0),(20,'新增',19,'供应商管理','sys:provider:add',NULL,NULL,NULL,2,'el-icon-plus','2022-04-25 14:40:32','2022-04-25 14:40:32',NULL,NULL,0),(21,'修改',19,'供应商管理','sys:provider:edit',NULL,NULL,NULL,2,'el-icon-edit','2022-04-25 14:40:32','2022-04-25 14:40:32',NULL,NULL,0),(22,'删除',19,'供应商管理','sys:provider:delete',NULL,NULL,NULL,2,'el-icon-delete','2022-04-25 14:40:32','2022-04-25 14:40:32',NULL,NULL,0),(23,'分配角色',6,'用户管理','sys:user:assign','','','',2,'el-icon-setting',NULL,NULL,NULL,NULL,0),(24,'分配权限',10,'角色管理','sys:role:assign','','','',2,'el-icon-setting',NULL,NULL,NULL,NULL,0),(25,'查询',2,'部门管理','sys:department:select','','','',2,'el-icon-search',NULL,NULL,NULL,NULL,0),(26,'查询',6,'用户管理','sys:user:select','','','',2,'el-icon-search',NULL,NULL,NULL,NULL,0),(27,'查询',10,'角色管理','sys:role:select','','','',2,'el-icon-search',NULL,NULL,NULL,NULL,0),(28,'查询',14,'菜单管理','sys:menu:select','','','',2,'el-icon-search',NULL,NULL,NULL,NULL,0),(29,'订单管理',18,'资料管理','resource:order','/resource/order','OrderList','/resource/order/orderList',1,'el-icon-setting',NULL,NULL,NULL,2,0);

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色编号',
  `role_code` varchar(50) NOT NULL COMMENT '角色编码',
  `role_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `create_user` bigint DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `is_delete` tinyint DEFAULT '0' COMMENT '是否删除(0-未删除，1-已删除)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;

/*Data for the table `sys_role` */

insert  into `sys_role`(`id`,`role_code`,`role_name`,`create_user`,`create_time`,`update_time`,`remark`,`is_delete`) values (1,'ROLE_SYSTEM','超级管理员',1,'2022-04-25 14:44:23','2022-04-25 14:44:23',NULL,0),(2,'ROLE_SYSTEM','系统管理员',1,'2022-04-25 14:44:23','2022-04-25 14:44:23','拥有系统管理功能模块的权限',0),(3,'ROLE_RESOURCE','资料管理员',NULL,NULL,NULL,'拥有资料管理模块的功能权限',0);

/*Table structure for table `sys_role_permission` */

DROP TABLE IF EXISTS `sys_role_permission`;

CREATE TABLE `sys_role_permission` (
  `role_Id` bigint NOT NULL COMMENT '角色ID',
  `permission_Id` bigint NOT NULL COMMENT '权限ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

/*Data for the table `sys_role_permission` */

insert  into `sys_role_permission`(`role_Id`,`permission_Id`) values (1,1),(1,2),(1,3),(1,4),(1,5),(1,25),(1,6),(1,7),(1,8),(1,9),(1,23),(1,26),(1,10),(1,11),(1,12),(1,13),(1,24),(1,27),(1,14),(1,15),(1,16),(1,17),(1,28),(1,18),(1,19),(1,20),(1,21),(1,22),(2,1),(2,2),(2,3),(2,4),(2,5),(2,25),(2,6),(2,7),(2,8),(2,9),(2,23),(2,26),(2,10),(2,11),(2,12),(2,13),(2,24),(2,27),(2,14),(2,15),(2,16),(2,17),(2,28),(3,18),(3,19),(3,20),(3,21),(3,22),(3,29);

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录名称(用户名)',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录密码',
  `is_account_non_expired` tinyint NOT NULL COMMENT '帐户是否过期(1-未过期，0-已过期)',
  `is_account_non_locked` tinyint NOT NULL COMMENT '帐户是否被锁定(1-未过期，0-已过期)',
  `is_credentials_non_expired` tinyint NOT NULL COMMENT '密码是否过期(1-未过期，0-已过期)',
  `is_enabled` tinyint NOT NULL COMMENT '帐户是否可用(1-可用，0-禁用)',
  `real_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '真实姓名',
  `nick_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '昵称',
  `department_id` bigint DEFAULT NULL COMMENT '所属部门ID',
  `department_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '所属部门名称',
  `gender` tinyint NOT NULL COMMENT '性别(0-男，1-女)',
  `phone` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '电话',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邮箱',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT 'https://manong-authority.oss-cn-guangzhou.aliyuncs.com/avatar/default-avatar.gif' COMMENT '用户头像',
  `is_admin` tinyint DEFAULT '0' COMMENT '是否是管理员(1-管理员)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_delete` tinyint DEFAULT '0' COMMENT '是否删除(0-未删除，1-已删除)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;

/*Data for the table `sys_user` */

insert  into `sys_user`(`id`,`username`,`password`,`is_account_non_expired`,`is_account_non_locked`,`is_credentials_non_expired`,`is_enabled`,`real_name`,`nick_name`,`department_id`,`department_name`,`gender`,`phone`,`email`,`avatar`,`is_admin`,`create_time`,`update_time`,`is_delete`) values (1,'admin','$2a$10$TdEVQtGCkpo8L.jKjFB3/uxV5xkkDfiy0zoCa.ZS2yAXHe7H95OIC',1,1,1,1,'李明','超级管理员',1,'广州码农信息技术有限公司',0,'13242587415','liming@163.com','https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',1,NULL,NULL,0),(2,'liming','$2a$10$WwhJ8dBezfyMFIn19.ELru58K65k6N2tgewtv2sWdClKiRCjC55wG',1,1,1,1,'黎明','黎明',2,'软件技术部',0,'13262365412578','','https://manong-authority.oss-cn-guangzhou.aliyuncs.com/avatar/default-avatar.gif',0,NULL,NULL,0),(3,'zhangsan','$2a$10$iBQbmrAEBE5B84/U3RY7c.zhObI4aIpjl807FV4LzL/uay7arIcpu',1,1,1,1,'张三','张三',2,'软件技术部',0,'13245678965','zhangsan@163.com','https://manong-authority.oss-cn-guangzhou.aliyuncs.com/avatar/2022/05/16/bfa834a4c9424461a1ea0cbf8d4c9105-5acd2ed959790ec52b2825cbbc11b72d.jpeg',0,NULL,NULL,1),(4,'lisi','$2a$10$QywHvELdRoFGiU6LKpd/X.LYpfaXETtS0pD4Nem2K3c0iMQwaZuAm',1,1,1,1,'李四','李四',2,'软件技术部',0,'13754214568','','https://manong-authority.oss-cn-guangzhou.aliyuncs.com/avatar/2022/05/16/8868a2bfb4364e0697f7c3d28f3d889a-5acd2ed959790ec52b2825cbbc11b72d.jpeg',0,NULL,NULL,0),(5,'wangwu','$2a$10$O8uyPZFS9PLfR8JN.aMRi.l/YeykYYuKH.cg/HBAR.N4NJeNg8hQK',1,1,1,1,'王五','王五',2,'软件技术部',0,'13212345678','','https://manong-authority.oss-cn-guangzhou.aliyuncs.com/avatar/2022/05/16/fe664c1e45bb4e39a719cd3f6d95232a-male.jpg',0,NULL,NULL,0),(6,'zhaoliu','$2a$10$r45wkEYLHlteEr0KLI8y3.G506ylhQrEJkmGM.i2eHkcCnFvfbhCS',1,1,1,1,'赵六','赵六',2,'软件技术部',0,'13212345676','','https://manong-authority.oss-cn-guangzhou.aliyuncs.com/avatar/2022/05/16/8868a2bfb4364e0697f7c3d28f3d889a-5acd2ed959790ec52b2825cbbc11b72d.jpeg',0,NULL,NULL,0);

/*Table structure for table `sys_user_role` */

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `role_id` bigint NOT NULL COMMENT '角色编号'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

/*Data for the table `sys_user_role` */

insert  into `sys_user_role`(`user_id`,`role_id`) values (1,1),(2,2),(4,2),(6,3),(5,3);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
