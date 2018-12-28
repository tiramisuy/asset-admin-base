/*
Navicat MySQL Data Transfer

Source Server         : mysql192.168.180.134
Source Server Version : 50548
Source Host           : 192.168.180.134:3306
Source Database       : dp-boot-lte

Target Server Type    : MYSQL
Target Server Version : 50548
File Encoding         : 65001

Date: 2017-09-13 22:32:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单id',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8 COMMENT='菜单管理';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '0', '系统管理', null, '', '0', 'fa fa-coffee', '0', '2017-08-09 22:49:47', '2017-09-11 17:25:22');
INSERT INTO `sys_menu` VALUES ('2', '1', '系统菜单', 'base/menu/list.html', null, '1', 'fa fa-th-list', '1', '2017-08-09 22:55:15', '2017-08-17 10:00:12');
INSERT INTO `sys_menu` VALUES ('3', '0', '组织机构', null, null, '0', 'fa fa-desktop', '1', '2017-08-09 23:06:55', '2017-08-17 09:54:28');
INSERT INTO `sys_menu` VALUES ('4', '1', '通用字典', 'base/macro/list.html', null, '1', 'fa fa-book', '2', '2017-08-09 23:06:58', '2017-08-17 10:00:24');
INSERT INTO `sys_menu` VALUES ('6', '3', '用户管理', 'base/user/list.html', '', '1', 'fa fa-user', '2', '2017-08-10 14:12:11', '2017-09-05 12:57:42');
INSERT INTO `sys_menu` VALUES ('7', '3', '角色管理', 'base/role/list.html', '', '1', 'fa fa-paw', '1', '2017-08-10 14:13:19', '2017-09-05 12:57:30');
INSERT INTO `sys_menu` VALUES ('11', '6', '刷新', '/sys/user/list', 'sys:user:list', '2', null, '0', '2017-08-14 10:51:05', '2017-09-05 12:47:23');
INSERT INTO `sys_menu` VALUES ('12', '6', '新增', '/sys/user/save', 'sys:user:save', '2', null, '0', '2017-08-14 10:51:35', '2017-09-05 12:47:34');
INSERT INTO `sys_menu` VALUES ('13', '6', '编辑', '/sys/user/update', 'sys:user:edit', '2', null, '0', '2017-08-14 10:52:06', '2017-09-05 12:47:46');
INSERT INTO `sys_menu` VALUES ('14', '6', '删除', '/sys/user/remove', 'sys:user:remove', '2', null, '0', '2017-08-14 10:52:24', '2017-09-05 12:48:03');
INSERT INTO `sys_menu` VALUES ('15', '7', '刷新', '/sys/role/list', 'sys:role:list', '2', null, '0', '2017-08-14 10:56:37', '2017-09-05 12:44:04');
INSERT INTO `sys_menu` VALUES ('16', '7', '新增', '/sys/role/save', 'sys:role:save', '2', null, '0', '2017-08-14 10:57:02', '2017-09-05 12:44:23');
INSERT INTO `sys_menu` VALUES ('17', '7', '编辑', '/sys/role/update', 'sys:role:edit', '2', null, '0', '2017-08-14 10:57:31', '2017-09-05 12:44:48');
INSERT INTO `sys_menu` VALUES ('18', '7', '删除', '/sys/role/remove', 'sys:role:remove', '2', null, '0', '2017-08-14 10:57:50', '2017-09-05 12:45:02');
INSERT INTO `sys_menu` VALUES ('19', '7', '操作权限', '/sys/role/authorize/opt', 'sys:role:authorizeOpt', '2', null, '0', '2017-08-14 10:58:55', '2017-09-05 12:45:29');
INSERT INTO `sys_menu` VALUES ('20', '2', '刷新', '/sys/menu/list', 'sys:menu:list', '2', null, '0', '2017-08-14 10:59:32', '2017-09-05 13:06:24');
INSERT INTO `sys_menu` VALUES ('21', '2', '新增', '/sys/menu/save', 'sys:menu:save', '2', null, '0', '2017-08-14 10:59:56', '2017-09-05 13:06:35');
INSERT INTO `sys_menu` VALUES ('22', '2', '编辑', '/sys/menu/update', 'sys:menu:edit', '2', null, '0', '2017-08-14 11:00:26', '2017-09-05 13:06:48');
INSERT INTO `sys_menu` VALUES ('23', '2', '删除', '/sys/menu/remove', 'sys:menu:remove', '2', null, '0', '2017-08-14 11:00:58', '2017-09-05 13:07:00');
INSERT INTO `sys_menu` VALUES ('24', '6', '启用', '/sys/user/enable', 'sys:user:enable', '2', null, '0', '2017-08-14 17:27:18', '2017-09-05 12:48:30');
INSERT INTO `sys_menu` VALUES ('25', '6', '停用', '/sys/user/disable', 'sys:user:disable', '2', null, '0', '2017-08-14 17:27:43', '2017-09-05 12:48:49');
INSERT INTO `sys_menu` VALUES ('26', '6', '重置密码', '/sys/user/rest', 'sys:user:resetPassword', '2', null, '0', '2017-08-14 17:28:34', '2017-09-05 12:49:17');
INSERT INTO `sys_menu` VALUES ('27', '1', '系统日志', 'base/log/list.html', null, '1', 'fa fa-warning', '3', '2017-08-14 22:11:53', '2017-08-17 09:55:19');
INSERT INTO `sys_menu` VALUES ('28', '27', '刷新', '/sys/log/list', 'sys:log:list', '2', null, '0', '2017-08-14 22:30:22', '2017-09-05 13:05:24');
INSERT INTO `sys_menu` VALUES ('29', '27', '删除', '/sys/log/remove', 'sys:log:remove', '2', null, '0', '2017-08-14 22:30:43', '2017-09-05 13:05:37');
INSERT INTO `sys_menu` VALUES ('30', '27', '清空', '/sys/log/clear', 'sys:log:clear', '2', null, '0', '2017-08-14 22:31:02', '2017-09-05 13:05:53');
INSERT INTO `sys_menu` VALUES ('32', '4', '刷新', '/sys/macro/list', 'sys:macro:list', '2', null, '0', '2017-08-15 16:55:33', '2017-09-05 13:04:00');
INSERT INTO `sys_menu` VALUES ('33', '4', '新增', '/sys/macro/save', 'sys:macro:save', '2', null, '0', '2017-08-15 16:55:52', '2017-09-05 13:04:22');
INSERT INTO `sys_menu` VALUES ('34', '4', '编辑', '/sys/macro/update', 'sys:macro:edit', '2', null, '0', '2017-08-15 16:56:09', '2017-09-05 13:04:36');
INSERT INTO `sys_menu` VALUES ('35', '4', '删除', '/sys/macro/remove', 'sys:macro:remove', '2', null, '0', '2017-08-15 16:56:29', '2017-09-05 13:04:49');
INSERT INTO `sys_menu` VALUES ('36', '3', '机构管理', 'base/org/list.html', '', '1', 'fa fa-sitemap', '0', '2017-08-17 09:57:14', '2017-09-05 12:58:53');
INSERT INTO `sys_menu` VALUES ('37', '1', '行政区域', 'base/area/list.html', 'sys:area:list', '1', 'fa fa-leaf', '0', '2017-08-17 09:59:57', '2017-09-05 12:49:47');
INSERT INTO `sys_menu` VALUES ('38', '37', '刷新', '/sys/area/list', 'sys:area:list', '2', null, '0', '2017-08-17 10:01:33', '2017-09-05 13:00:54');
INSERT INTO `sys_menu` VALUES ('39', '37', '新增', '/sys/area/save', 'sys:area:save', '2', null, '0', '2017-08-17 10:02:16', '2017-09-05 13:01:06');
INSERT INTO `sys_menu` VALUES ('40', '37', '编辑', '/sys/area/update', 'sys:area:edit', '2', null, '0', '2017-08-17 10:02:33', '2017-09-05 13:01:21');
INSERT INTO `sys_menu` VALUES ('41', '37', '删除', '/sys/area/remove', 'sys:area:remove', '2', null, '0', '2017-08-17 10:02:50', '2017-09-05 13:01:32');
INSERT INTO `sys_menu` VALUES ('42', '36', '刷新', '/sys/org/list', 'sys:org:list', '2', null, '0', '2017-08-17 10:03:36', '2017-09-05 11:47:37');
INSERT INTO `sys_menu` VALUES ('43', '36', '新增', '/sys/org/save', 'sys:org:save', '2', null, '0', '2017-08-17 10:03:54', '2017-09-05 12:40:55');
INSERT INTO `sys_menu` VALUES ('44', '36', '编辑', '/sys/org/update', 'sys:org:edit', '2', null, '0', '2017-08-17 10:04:11', '2017-09-05 12:43:06');
INSERT INTO `sys_menu` VALUES ('45', '36', '删除', '/sys/org/remove', 'sys:org:remove', '2', null, '0', '2017-08-17 10:04:30', '2017-09-05 12:42:19');
INSERT INTO `sys_menu` VALUES ('46', '7', '数据权限', '/sys/role/authorize/data', 'sys:role:authorizeData', '2', null, '0', '2017-08-17 13:48:11', '2017-09-05 12:45:54');
INSERT INTO `sys_menu` VALUES ('47', '1', '定时任务', 'base/quartz/list.html', null, '1', 'fa fa-bell', '4', '2017-08-19 23:00:08', null);
INSERT INTO `sys_menu` VALUES ('48', '47', '刷新', '/quartz/job/list', 'quartz:job:list', '2', null, '0', '2017-08-19 23:00:54', '2017-09-05 13:08:18');
INSERT INTO `sys_menu` VALUES ('49', '47', '新增', '/quartz/job/save', 'quartz:job:save', '2', null, '0', '2017-08-19 23:01:29', '2017-09-05 13:08:30');
INSERT INTO `sys_menu` VALUES ('50', '47', '编辑', '/quartz/job/update', 'quartz:job:edit', '2', null, '0', '2017-08-19 23:01:58', '2017-09-05 13:08:44');
INSERT INTO `sys_menu` VALUES ('51', '47', '删除', '/quartz/job/remove', 'quartz:job:remove', '2', null, '0', '2017-08-19 23:02:30', '2017-09-05 13:08:57');
INSERT INTO `sys_menu` VALUES ('52', '63', '启用', '/quartz/job/enable', 'quartz:job:enable', '2', null, '0', '2017-08-19 23:08:59', '2017-09-13 22:12:35');
INSERT INTO `sys_menu` VALUES ('53', '63', '停用', '/quartz/job/disable', 'quartz:job:disable', '2', null, '0', '2017-08-19 23:09:31', '2017-09-13 22:12:53');
INSERT INTO `sys_menu` VALUES ('54', '63', '立即执行', '/quartz/job/run', 'quartz:job:run', '2', null, '0', '2017-08-19 23:10:09', '2017-09-13 22:13:11');
INSERT INTO `sys_menu` VALUES ('55', '47', '日志列表', null, 'quartz:job:log', '1', null, '0', '2017-08-19 23:10:40', '2017-09-13 22:21:12');
INSERT INTO `sys_menu` VALUES ('56', '55', '刷新', '/quartz/job/log/list', 'quartz:log:list', '2', null, '0', '2017-08-21 13:25:33', '2017-09-13 22:21:27');
INSERT INTO `sys_menu` VALUES ('57', '55', '删除', '/quartz/job/log/remove', 'quartz:log:remove', '2', null, '0', '2017-08-21 13:25:52', '2017-09-13 22:21:46');
INSERT INTO `sys_menu` VALUES ('58', '55', '清空', '/quartz/job/log/clear', 'quartz:log:clear', '2', null, '0', '2017-08-21 13:26:11', '2017-09-13 22:22:04');
INSERT INTO `sys_menu` VALUES ('59', '1', '敏捷开发', 'base/generator/list.html', null, '1', 'fa fa-archive', '5', '2017-09-05 10:49:04', null);
INSERT INTO `sys_menu` VALUES ('60', '59', '刷新', '/sys/generator/list', 'sys:gen:list', '2', null, '0', '2017-09-05 10:49:25', '2017-09-05 13:07:33');
INSERT INTO `sys_menu` VALUES ('61', '59', '生成代码', '/sys/generator/code', 'sys:gen:code', '2', null, '0', '2017-09-05 10:49:44', '2017-09-05 13:07:48');
INSERT INTO `sys_menu` VALUES ('62', '1', '系统监控', 'druid/index.html', null, '1', 'fa fa-bug', '6', '2017-09-10 17:01:59', '2017-09-10 17:02:19');
INSERT INTO `sys_menu` VALUES ('63', '47', '更多', null, 'quartz:job:more', '1', null, '0', '2017-09-13 22:11:51', '2017-09-13 22:12:12');
INSERT INTO `sys_menu` VALUES ('64', '1', '接口管理', 'swagger-ui.html', null, '1', 'fa fa-support', '7', '2017-09-10 17:01:59', '2017-09-10 17:02:19');

-- ----------------------------
-- Table structure for sys_org
-- ----------------------------
DROP TABLE IF EXISTS `sys_org`;
CREATE TABLE `sys_org` (
  `org_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '机构id',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '上级机构ID，一级机构为0',
  `code` varchar(100) DEFAULT NULL COMMENT '机构编码',
  `name` varchar(100) DEFAULT NULL COMMENT '机构名称',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  `status` tinyint(4) DEFAULT '1' COMMENT '可用标识  1：可用  0：不可用',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`org_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='机构管理';

-- ----------------------------
-- Records of sys_org
-- ----------------------------
INSERT INTO `sys_org` VALUES ('1', '0', 'js', '江苏省', '0', '1', '2017-08-17 12:03:15', '2017-08-17 17:06:08');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `org_id` bigint(255) DEFAULT NULL COMMENT '所属机构',
  `role_name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `role_sign` varchar(100) DEFAULT NULL COMMENT '角色标识',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `user_id_create` bigint(255) DEFAULT NULL COMMENT '创建用户id',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='系统角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '1', '超级管理员', 'admin', '【系统内置】', '2', '2017-08-12 00:43:52', '2017-09-05 14:02:04');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录id',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色与菜单对应关系';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (null, '1', '1');
INSERT INTO `sys_role_menu` VALUES (null, '1', '2');
INSERT INTO `sys_role_menu` VALUES (null, '1', '4');
INSERT INTO `sys_role_menu` VALUES (null, '1', '3');
INSERT INTO `sys_role_menu` VALUES (null, '1', '6');
INSERT INTO `sys_role_menu` VALUES (null, '1', '7');
INSERT INTO `sys_role_menu` VALUES (null, '1', '11');
INSERT INTO `sys_role_menu` VALUES (null, '1', '12');
INSERT INTO `sys_role_menu` VALUES (null, '1', '13');
INSERT INTO `sys_role_menu` VALUES (null, '1', '14');
INSERT INTO `sys_role_menu` VALUES (null, '1', '15');
INSERT INTO `sys_role_menu` VALUES (null, '1', '16');
INSERT INTO `sys_role_menu` VALUES (null, '1', '17');
INSERT INTO `sys_role_menu` VALUES (null, '1', '18');
INSERT INTO `sys_role_menu` VALUES (null, '1', '19');
INSERT INTO `sys_role_menu` VALUES (null, '1', '20');
INSERT INTO `sys_role_menu` VALUES (null, '1', '21');
INSERT INTO `sys_role_menu` VALUES (null, '1', '22');
INSERT INTO `sys_role_menu` VALUES (null, '1', '23');
INSERT INTO `sys_role_menu` VALUES (null, '1', '24');
INSERT INTO `sys_role_menu` VALUES (null, '1', '25');
INSERT INTO `sys_role_menu` VALUES (null, '1', '26');
INSERT INTO `sys_role_menu` VALUES (null, '1', '27');
INSERT INTO `sys_role_menu` VALUES (null, '1', '28');
INSERT INTO `sys_role_menu` VALUES (null, '1', '29');
INSERT INTO `sys_role_menu` VALUES (null, '1', '30');
INSERT INTO `sys_role_menu` VALUES (null, '1', '32');
INSERT INTO `sys_role_menu` VALUES (null, '1', '33');
INSERT INTO `sys_role_menu` VALUES (null, '1', '34');
INSERT INTO `sys_role_menu` VALUES (null, '1', '35');
INSERT INTO `sys_role_menu` VALUES (null, '1', '36');
INSERT INTO `sys_role_menu` VALUES (null, '1', '37');
INSERT INTO `sys_role_menu` VALUES (null, '1', '38');
INSERT INTO `sys_role_menu` VALUES (null, '1', '39');
INSERT INTO `sys_role_menu` VALUES (null, '1', '40');
INSERT INTO `sys_role_menu` VALUES (null, '1', '41');
INSERT INTO `sys_role_menu` VALUES (null, '1', '42');
INSERT INTO `sys_role_menu` VALUES (null, '1', '43');
INSERT INTO `sys_role_menu` VALUES (null, '1', '44');
INSERT INTO `sys_role_menu` VALUES (null, '1', '45');
INSERT INTO `sys_role_menu` VALUES (null, '1', '46');
INSERT INTO `sys_role_menu` VALUES (null, '1', '47');
INSERT INTO `sys_role_menu` VALUES (null, '1', '48');
INSERT INTO `sys_role_menu` VALUES (null, '1', '49');
INSERT INTO `sys_role_menu` VALUES (null, '1', '50');
INSERT INTO `sys_role_menu` VALUES (null, '1', '51');
INSERT INTO `sys_role_menu` VALUES (null, '1', '55');
INSERT INTO `sys_role_menu` VALUES (null, '1', '56');
INSERT INTO `sys_role_menu` VALUES (null, '1', '57');
INSERT INTO `sys_role_menu` VALUES (null, '1', '58');
INSERT INTO `sys_role_menu` VALUES (null, '1', '63');
INSERT INTO `sys_role_menu` VALUES (null, '1', '52');
INSERT INTO `sys_role_menu` VALUES (null, '1', '53');
INSERT INTO `sys_role_menu` VALUES (null, '1', '54');
INSERT INTO `sys_role_menu` VALUES (null, '1', '59');
INSERT INTO `sys_role_menu` VALUES (null, '1', '60');
INSERT INTO `sys_role_menu` VALUES (null, '1', '61');
INSERT INTO `sys_role_menu` VALUES (null, '1', '62');
INSERT INTO `sys_role_menu` VALUES (null, '1', '64');

-- ----------------------------
-- Table structure for sys_role_org
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_org`;
CREATE TABLE `sys_role_org` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录id',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `org_id` bigint(20) DEFAULT NULL COMMENT '机构ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色与机构对应关系';

-- ----------------------------
-- Records of sys_role_org
-- ----------------------------
INSERT INTO `sys_role_org` VALUES (null, '1', '1');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `org_id` bigint(255) DEFAULT NULL COMMENT '所属机构',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) DEFAULT NULL COMMENT '手机号',
  `status` tinyint(255) DEFAULT NULL COMMENT '状态 0:禁用，1:正常',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `user_id_create` bigint(255) DEFAULT NULL COMMENT '创建用户id',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='系统用户';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', '1', 'admin', '33808479d49ca8a3cdc93d4f976d1e3d', 'admin@example.com', '123456', '1', null, '1', '2017-08-15 21:40:39', '2017-08-15 21:41:00');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户与角色对应关系';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (null, '1', '1');

-- ----------------------------
-- Table structure for sys_user_token
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_token`;
CREATE TABLE `sys_user_token` (
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `token` varchar(100) NOT NULL COMMENT 'token',
  `gmt_expire` datetime DEFAULT NULL COMMENT '过期时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `token` (`token`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户Token';

-- ----------------------------
-- Records of sys_user_token
-- ----------------------------
insert  into `sys_user_token`(`user_id`,`token`,`gmt_expire`,`gmt_modified`) values (1,'a3c646202882f1213b63fe74dc118e9d','2017-10-26 22:10:52','2017-10-26 10:10:52');