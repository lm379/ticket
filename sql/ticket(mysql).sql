/*
 Navicat Premium Data Transfer

 Source Server         : ticket
 Source Server Type    : MySQL
 Source Server Version : 80037 (8.0.37)

 Target Server Type    : MySQL
 Target Server Version : 80037 (8.0.37)
 File Encoding         : 65001

 Date: 13/06/2024 13:18:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for company
-- ----------------------------
DROP TABLE IF EXISTS `company`;
CREATE TABLE `company`  (
  `CompanyNumber` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `CompanyName` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `CompanyAddress` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `CompanyHotline` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`CompanyNumber`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of company
-- ----------------------------
INSERT INTO `company` VALUES ('1', '中国南方航空', '广州市机场路278号95539', '95539');
INSERT INTO `company` VALUES ('2', '中国东方航空', '上海市虹桥路2550号', '95530');
INSERT INTO `company` VALUES ('3', '中国国际航空', '北京市顺义区天柱路30号', '95583');
INSERT INTO `company` VALUES ('4', '海南航空', '海口市国兴大道7号', '950718');
INSERT INTO `company` VALUES ('5', '深圳航空', '深圳宝安国际机场航站四路2033号', '95080');
INSERT INTO `company` VALUES ('6', '厦门航空', '厦门市埭辽路22号', '95557');
INSERT INTO `company` VALUES ('7', '吉祥航空', '上海市虹翔三路80号', '95520');
INSERT INTO `company` VALUES ('8', '四川航空', '成都市双流国际机场航空大厦', '95378');
INSERT INTO `company` VALUES ('9', '山东航空', '青岛市延安三路111号丙汇利大厦2楼', '95369');

-- ----------------------------
-- Table structure for flight
-- ----------------------------
DROP TABLE IF EXISTS `flight`;
CREATE TABLE `flight`  (
  `FlightNumber` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `companyID` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `FlightName` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `fromCity` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `toCity` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `mileAge` int NULL DEFAULT NULL,
  `departureTime` time NULL DEFAULT NULL,
  PRIMARY KEY (`FlightNumber`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of flight
-- ----------------------------
INSERT INTO `flight` VALUES ('3U8848', '8', '空客320', '成都', '上海', 2800, '12:01:00');
INSERT INTO `flight` VALUES ('3U8948', '8', '空客320', '济南', '昆明', 2080, '21:30:00');
INSERT INTO `flight` VALUES ('3U8962', '8', '空客320', '上海', '成都', 2800, '11:35:00');
INSERT INTO `flight` VALUES ('CA1947', '3', '空客340', '上海', '成都', 2800, '08:03:00');
INSERT INTO `flight` VALUES ('CZ3117', '1', '波音738', '武汉', '北京', 1100, '08:06:00');
INSERT INTO `flight` VALUES ('CZ3907', '1', '空客333', '北京', '上海', 1130, '18:05:00');
INSERT INTO `flight` VALUES ('CZ6356', '1', '波音738', '海口', '郑州', 1873, '11:51:00');
INSERT INTO `flight` VALUES ('CZ6553', '1', '空客320', '长春', '上海', 1698, '16:30:00');
INSERT INTO `flight` VALUES ('HO1252', '7', '空客320', '北京', '上海', 1130, '06:40:00');
INSERT INTO `flight` VALUES ('HO1284', '7', '空客320', '长春', '上海', 1698, '14:45:00');
INSERT INTO `flight` VALUES ('HU7188', '4', '波音738', '武汉', '北京', 1100, '15:55:00');
INSERT INTO `flight` VALUES ('HU7291', '4', '波音738', '海口', '郑州', 1873, '13:05:00');
INSERT INTO `flight` VALUES ('HU7309', '4', '波音738', '海口', '郑州', 1873, '08:31:00');
INSERT INTO `flight` VALUES ('MF8069', '6', '波音738', '南宁', '沈阳', 2780, '08:03:00');
INSERT INTO `flight` VALUES ('MF8073', '6', '波音738', '厦门', '沈阳', 2242, '07:41:00');
INSERT INTO `flight` VALUES ('MU2451', '2', '波音738', '武汉', '北京', 1100, '09:04:00');
INSERT INTO `flight` VALUES ('MU2453', '2', '波音738', '武汉', '北京', 1100, '15:00:00');
INSERT INTO `flight` VALUES ('MU2540', '2', '波音738', '上海', '成都', 2800, '19:30:00');
INSERT INTO `flight` VALUES ('MU2885', '2', '空客320', '南京', '西安', 1104, '19:50:00');
INSERT INTO `flight` VALUES ('MU294', '2', '空客320', '上海', '成都', 2800, '11:15:00');
INSERT INTO `flight` VALUES ('MU5102', '2', '空客333', '北京', '上海', 1130, '08:39:00');
INSERT INTO `flight` VALUES ('MU5680', '2', '空客332', '长春', '上海', 1698, '13:50:00');
INSERT INTO `flight` VALUES ('SC1191', '9', '波音738', '济南', '昆明', 2080, '20:00:00');
INSERT INTO `flight` VALUES ('SC4873', '9', '波音738', '济南', '昆明', 2080, '08:45:00');
INSERT INTO `flight` VALUES ('ZH9438', '5', '空客320', '海口', '郑州', 1873, '17:00:00');
INSERT INTO `flight` VALUES ('ZH9516', '5', '空客320', '厦门', '沈阳', 2242, '16:20:00');
INSERT INTO `flight` VALUES ('ZH9602', '5', '空客320', '厦门', '沈阳', 2242, '14:55:00');

-- ----------------------------
-- Table structure for passenger
-- ----------------------------
DROP TABLE IF EXISTS `passenger`;
CREATE TABLE `passenger`  (
  `PassengerIdentity` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `PName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `gender` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `birthday` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `PTele` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `IdentityStyle` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `PaymentState` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `PTicketNumber` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`PassengerIdentity`) USING BTREE,
  INDEX `FK_Passenger_Ticket`(`PTicketNumber` ASC) USING BTREE,
  CONSTRAINT `FK_Passenger_Ticket` FOREIGN KEY (`PTicketNumber`) REFERENCES `ticket` (`TicketNumber`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of passenger
-- ----------------------------
INSERT INTO `passenger` VALUES ('100506198304161675', '苏地方', '男', '1983-04-16', '13867886598', '护照', '1', '4');
INSERT INTO `passenger` VALUES ('100712197303186681', '额外可', '女', '1973-03-18', '15367211029', '护照', '1', '3');
INSERT INTO `passenger` VALUES ('101009197404104775', '委任为', '男', '1974-04-10', '13578983521', '护照', '1', '2');
INSERT INTO `passenger` VALUES ('280103198309125341', '刘亚蒙', '男', '1976-10-20', '13565888845', '大陆居民身份证', '1', '2');
INSERT INTO `passenger` VALUES ('280103199806195832', '郝琼琼', '女', '1987-03-12', '15334564321', '大陆居民身份证', '1', '1');
INSERT INTO `passenger` VALUES ('280104198703125883', '雷亚波', '男', '1988-06-19', '13686035678', '大陆居民身份证', '1', '2');
INSERT INTO `passenger` VALUES ('280105197610200914', '魏国兰', '女', '1983-09-12', '13827653456', '大陆居民身份证', '1', '5');
INSERT INTO `passenger` VALUES ('640104198703125881', '他亚波', '男', '1988-06-19', '13686035678', '港澳通行证', '1', '1');
INSERT INTO `passenger` VALUES ('640105197610200916', '贴国兰', '女', '1983-09-12', '13827653456', '港澳通行证', '1', '2');
INSERT INTO `passenger` VALUES ('770103198309125344', '万亚蒙', '男', '1976-10-20', '13565888845', '大陆居民身份证', '1', '1');
INSERT INTO `passenger` VALUES ('770103199806195830', '任琼琼', '女', '1987-03-12', '15334564321', '港澳通行证', '1', '2');
INSERT INTO `passenger` VALUES ('770106199208113735', '李慧娟', '女', '1992-08-11', '13967341256', '大陆居民身份证', '1', '6');
INSERT INTO `passenger` VALUES ('770107198307762086', '吕兰梦', '女', '1983-04-26', '13878292910', '大陆居民身份证', '1', '1');
INSERT INTO `passenger` VALUES ('770211197905122417', '郝嘉志', '男', '1979-05-12', '15945673771', '大陆居民身份证', '1', '2');
INSERT INTO `passenger` VALUES ('770506198304161678', '苏彦博', '男', '1983-04-16', '13867886598', '大陆居民身份证', '1', '2');
INSERT INTO `passenger` VALUES ('770712197303186689', '严雅可', '女', '1973-03-18', '15367217729', '大陆居民身份证', '1', '5');
INSERT INTO `passenger` VALUES ('771009197404104770', '傅明远', '男', '1974-04-10', '13578983521', '大陆居民身份证', '1', '3');
INSERT INTO `passenger` VALUES ('800211197905122415', '郝大纲', '男', '1979-05-12', '15945673801', '护照', '1', '5');
INSERT INTO `passenger` VALUES ('820106199208113738', '发慧娟', '女', '1992-08-11', '13967341256', '港澳通行证', '1', '3');
INSERT INTO `passenger` VALUES ('820107198308062089', '才兰梦', '女', '1983-04-26', '13878292910', '港澳通行证', '1', '5');

-- ----------------------------
-- Table structure for ticket
-- ----------------------------
DROP TABLE IF EXISTS `ticket`;
CREATE TABLE `ticket`  (
  `TicketNumber` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Price` int NULL DEFAULT NULL,
  `discount` double NULL DEFAULT NULL,
  `Condition` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '预售状态',
  `worker` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '经受业务员',
  `TflightNumber` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`TicketNumber`) USING BTREE,
  INDEX `FK_Ticket_Flight`(`TflightNumber` ASC) USING BTREE,
  CONSTRAINT `FK_Ticket_Flight` FOREIGN KEY (`TflightNumber`) REFERENCES `flight` (`FlightNumber`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ticket
-- ----------------------------
INSERT INTO `ticket` VALUES ('1', 3245, 0.4, '1', '1', '3U8948');
INSERT INTO `ticket` VALUES ('10', 312, 0.3, '0', '4', 'MF8073');
INSERT INTO `ticket` VALUES ('11', 453, 0.2, '0', '4', 'MU2885');
INSERT INTO `ticket` VALUES ('12', 312, 0.6, '0', '4', 'MU2885');
INSERT INTO `ticket` VALUES ('13', 546, 0.3, '0', '4', 'MU294');
INSERT INTO `ticket` VALUES ('14', 2334, 0.5, '1', '2', 'CZ3907');
INSERT INTO `ticket` VALUES ('15', 2145, 0.4, '1', '4', 'MU2540');
INSERT INTO `ticket` VALUES ('2', 3252, 0.8, '1', '1', '3U8948');
INSERT INTO `ticket` VALUES ('3', 6588, 0.5, '1', '1', 'HO1284');
INSERT INTO `ticket` VALUES ('4', 2356, 0.4, '1', '1', 'HO1284');
INSERT INTO `ticket` VALUES ('5', 6484, 0.6, '1', '3', 'CZ6356');
INSERT INTO `ticket` VALUES ('6', 25476, 0.7, '1', '3', 'HU7188');
INSERT INTO `ticket` VALUES ('7', 2466, 0.4, '0', '3', 'HU7188');
INSERT INTO `ticket` VALUES ('8', 352, 0.4, '0', '3', 'HU7188');
INSERT INTO `ticket` VALUES ('9', 535, 0.2, '0', '3', 'MF8073');

SET FOREIGN_KEY_CHECKS = 1;
