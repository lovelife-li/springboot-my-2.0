
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for cat
-- ----------------------------
DROP TABLE IF EXISTS `cat`;
CREATE TABLE `cat` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of cat
-- ----------------------------
INSERT INTO `cat` VALUES ('1', '12', '12', null, null);
INSERT INTO `cat` VALUES ('2', 'dd', '23', null, null);
INSERT INTO `cat` VALUES ('3', 'c', '14', null, null);
INSERT INTO `cat` VALUES ('4', 'd', '45', null, null);
INSERT INTO `cat` VALUES ('7', 'dd', '23', null, null);
INSERT INTO `cat` VALUES ('8', 'dd', '23', null, null);
INSERT INTO `cat` VALUES ('9', 'dd', '23', null, null);
INSERT INTO `cat` VALUES ('10', 'dd', '23', null, null);
INSERT INTO `cat` VALUES ('12', 'a', '12', '1', '3');
