/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50623
Source Host           : localhost:3306
Source Database       : mydb

Target Server Type    : MYSQL
Target Server Version : 50623
File Encoding         : 65001

Date: 2019-05-07 10:50:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for db_user
-- ----------------------------
DROP TABLE IF EXISTS `db_user`;
CREATE TABLE `db_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `birth` date DEFAULT NULL,
  `enable` bit(1) DEFAULT NULL,
  `hiredate` datetime DEFAULT NULL,
  `pwd` varchar(255) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of db_user
-- ----------------------------
INSERT INTO `db_user` VALUES ('1', '张三', '12', '1991-06-06', '', '2019-05-02 19:32:54', '1234546', '2019-04-29 19:32:01', '2019-04-29 19:33:01');
INSERT INTO `db_user` VALUES ('2', '李四', '24', '1991-06-05', '', '2019-04-29 12:11:12', '123456', '2019-04-30 13:38:38', '2019-04-30 13:38:38');
