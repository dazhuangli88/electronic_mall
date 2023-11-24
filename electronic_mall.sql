/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : localhost:3306
 Source Schema         : electronic_mall

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 24/11/2023 14:48:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_cart
-- ----------------------------
DROP TABLE IF EXISTS `tb_cart`;
CREATE TABLE `tb_cart`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `userId` int NULL DEFAULT NULL COMMENT '用户id',
  `productCategory` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品种类',
  `productName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品名称',
  `factory` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '厂家',
  `priceSale` double(10, 2) NULL DEFAULT NULL COMMENT '销售价格',
  `discount` double(10, 2) NULL DEFAULT NULL COMMENT '折扣',
  `buyNum` int NULL DEFAULT NULL COMMENT '购买数量',
  `totalPrice` double(10, 2) NULL DEFAULT NULL COMMENT '总金额',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系电话',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '收货地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_cart
-- ----------------------------
INSERT INTO `tb_cart` VALUES (12, 5, '电脑', '联想电脑', '联想厂家', 12341.00, 1.00, 3, 37023.00, '321', '123');

-- ----------------------------
-- Table structure for tb_category
-- ----------------------------
DROP TABLE IF EXISTS `tb_category`;
CREATE TABLE `tb_category`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `categoryName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品分类的名字',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品分类表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_category
-- ----------------------------
INSERT INTO `tb_category` VALUES (1, '电脑');
INSERT INTO `tb_category` VALUES (2, '电脑外设');
INSERT INTO `tb_category` VALUES (3, '主板');
INSERT INTO `tb_category` VALUES (6, '冰箱');
INSERT INTO `tb_category` VALUES (7, '电脑');

-- ----------------------------
-- Table structure for tb_collect
-- ----------------------------
DROP TABLE IF EXISTS `tb_collect`;
CREATE TABLE `tb_collect`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `userId` int NULL DEFAULT NULL COMMENT '用户id',
  `productCategory` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品种类',
  `productName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品名称',
  `factory` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '厂家',
  `priceSale` double(10, 2) NULL DEFAULT NULL COMMENT '销售价格',
  `discount` double(10, 2) NULL DEFAULT NULL COMMENT '折扣',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_collect
-- ----------------------------
INSERT INTO `tb_collect` VALUES (6, 5, '冰箱', '三星双开门冰箱', '三星厂家', 2000.00, 8.00);
INSERT INTO `tb_collect` VALUES (7, 5, '电脑', '联想电脑', '联想厂家', 12341.00, 1.00);

-- ----------------------------
-- Table structure for tb_order
-- ----------------------------
DROP TABLE IF EXISTS `tb_order`;
CREATE TABLE `tb_order`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `userId` int NULL DEFAULT NULL COMMENT '用户id',
  `productCategory` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品种类',
  `productName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品名称',
  `factory` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '厂家',
  `priceSale` double(10, 2) NULL DEFAULT NULL COMMENT '销售价格',
  `discount` double(10, 2) NULL DEFAULT NULL COMMENT '折扣',
  `buyNum` int NULL DEFAULT NULL COMMENT '购买数量',
  `totalPrice` double(10, 2) NULL DEFAULT NULL COMMENT '总金额',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系电话',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '收货地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_order
-- ----------------------------
INSERT INTO `tb_order` VALUES (1, 4, '电脑', '神州', '神州厂家', 132142.00, 8.00, 11, 1453562.00, '13131313311', '广西');
INSERT INTO `tb_order` VALUES (2, 4, '电脑', '华硕电脑', '华硕厂家', 12341.00, 8.00, 2, 24682.00, '13131313311', '西北');
INSERT INTO `tb_order` VALUES (3, 4, '电脑', '华硕电脑', '华硕厂家', 12341.00, 7.00, 1, 12341.00, '13131313311', '珠海');
INSERT INTO `tb_order` VALUES (4, 5, '电脑', '华硕电脑', '华硕厂家', 12341.00, 6.00, 1, 12341.00, '123', '四川');
INSERT INTO `tb_order` VALUES (5, 5, '电脑', '华硕电脑', '华硕厂家', 12341.00, 6.00, 1, 12341.00, '123', '云南');
INSERT INTO `tb_order` VALUES (6, 5, '电脑', '联想电脑', '联想厂家', 12341.00, 8.00, 1, 12341.00, '13', '广东');
INSERT INTO `tb_order` VALUES (7, 5, '冰箱', '三星双开门冰箱', '三星厂家', 1324.00, 1.00, 1, 1324.00, '123', '广东');
INSERT INTO `tb_order` VALUES (8, 5, '主板', '主板', '微软', 2000.00, 8.00, 1, 16000.00, '2', '23');

-- ----------------------------
-- Table structure for tb_product
-- ----------------------------
DROP TABLE IF EXISTS `tb_product`;
CREATE TABLE `tb_product`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `productInventoryId` int NOT NULL COMMENT '商品库存主键',
  `price` double(10, 2) NOT NULL COMMENT '销售价格',
  `discount` double(10, 2) NOT NULL DEFAULT 1.00 COMMENT '折扣',
  `productStatus` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品状态（正常，降价）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_product
-- ----------------------------
INSERT INTO `tb_product` VALUES (3, 1, 132142.00, 1.00, '正常');
INSERT INTO `tb_product` VALUES (8, 2, 12341.00, 1.00, '正常');
INSERT INTO `tb_product` VALUES (9, 3, 1324.00, 1.00, '正常');
INSERT INTO `tb_product` VALUES (10, 3, 2000.00, 8.00, '正常');
INSERT INTO `tb_product` VALUES (11, 5, 2000.00, 8.00, '正常');

-- ----------------------------
-- Table structure for tb_product_inventory
-- ----------------------------
DROP TABLE IF EXISTS `tb_product_inventory`;
CREATE TABLE `tb_product_inventory`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `productCategory` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品分类',
  `productName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品名称',
  `manufacturer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '厂家',
  `productNum` int NULL DEFAULT NULL COMMENT '商品数量',
  `productBuyer` double(10, 2) NULL DEFAULT NULL COMMENT '商品进价',
  `isShelf` int NULL DEFAULT 0 COMMENT '是否上架(0：待上架    1：已上架    2：已下架)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品库存管理' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_product_inventory
-- ----------------------------
INSERT INTO `tb_product_inventory` VALUES (1, '电脑', '华硕电脑', '华硕厂家', 100, 5000.00, 0);
INSERT INTO `tb_product_inventory` VALUES (2, '电脑', '联想电脑', '联想厂家', 50, 6000.00, 0);
INSERT INTO `tb_product_inventory` VALUES (3, '冰箱', '三星双开门冰箱', '三星厂家', 39, 4000.00, 0);
INSERT INTO `tb_product_inventory` VALUES (4, '电脑外设', '显示屏', '小米', 50, 3000.00, 0);
INSERT INTO `tb_product_inventory` VALUES (5, '主板', '主板', '微软', 49, 4000.00, 1);

-- ----------------------------
-- Table structure for tb_stock_goods
-- ----------------------------
DROP TABLE IF EXISTS `tb_stock_goods`;
CREATE TABLE `tb_stock_goods`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `categoryName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分类名称',
  `productName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品名',
  `buyer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '进货人',
  `buyerPhone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '进货人联系方式',
  `productNum` int NULL DEFAULT NULL COMMENT '商品数量',
  `productPrice` double(10, 2) NULL DEFAULT NULL COMMENT '商品价格',
  `totalPurchasePrice` double(10, 2) NULL DEFAULT NULL COMMENT '商品总价',
  `purchasingManufacturer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '进货厂商',
  `manufacturerPhone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '厂商电话',
  `manufacturerAddress` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '厂商地址',
  `purchaseInstructions` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '进货说明',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品进货' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_stock_goods
-- ----------------------------
INSERT INTO `tb_stock_goods` VALUES (1, '电脑', '联想', '富贵', '456', 13, 5.00, 65.00, '联想', '777', '上海', '轻薄');
INSERT INTO `tb_stock_goods` VALUES (2, '电脑', '华硕电脑', '张三', '123', 12, 6000.00, 72000.00, '华硕', '888', '中山', '高配置');
INSERT INTO `tb_stock_goods` VALUES (3, '电脑', '神州电脑', '李四', '789', 553, 35.00, 19355.00, '神州', '999', '珠海', '锐龙');
INSERT INTO `tb_stock_goods` VALUES (4, '电脑外设', '显示屏', '王五', '000', 50, 3000.00, 150000.00, '小米', '000', '广西', '24寸');
INSERT INTO `tb_stock_goods` VALUES (5, '主板', '主板', '老六', '111', 50, 4000.00, 200000.00, '微软', '111', '北京', '双核');
INSERT INTO `tb_stock_goods` VALUES (6, '冰箱', '三星双开门冰箱', '小红', '222', 501, 4000.00, 200000.00, '三星', '666', '西北', '节能');

-- ----------------------------
-- Table structure for tb_users
-- ----------------------------
DROP TABLE IF EXISTS `tb_users`;
CREATE TABLE `tb_users`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `userName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `passWord` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `phone` char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '电话号码',
  `nickName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '昵称',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '姓名',
  `userType` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户类型',
  `createTime` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_users
-- ----------------------------
INSERT INTO `tb_users` VALUES (3, '系统管理员', 'e10adc3949ba59abbe56e057f20f883e', '13131313311', '系统管理员', '系统管理员', 'ADMIN', '2023-05-31 20:54:16', '2023-05-31 20:54:19');
INSERT INTO `tb_users` VALUES (4, '张三', 'e10adc3949ba59abbe56e057f20f883e', '13131313311', '张三', '张三', 'ORDINARY', '2023-06-14 22:42:21', '2023-06-14 22:42:21');
INSERT INTO `tb_users` VALUES (5, '富贵', '202cb962ac59075b964b07152d234b70', '123', '富贵', '富贵', 'ORDINARY', '2023-06-19 01:48:54', '2023-06-19 01:48:54');

SET FOREIGN_KEY_CHECKS = 1;
