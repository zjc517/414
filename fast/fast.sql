/*
 Navicat Premium Data Transfer

 Source Server         : fast
 Source Server Type    : MySQL
 Source Server Version : 80300
 Source Host           : localhost:3306
 Source Schema         : fast

 Target Server Type    : MySQL
 Target Server Version : 80300
 File Encoding         : 65001

 Date: 10/06/2026 16:35:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for address
-- ----------------------------
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address`  (
  `address_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '收货地址ID',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '收货人',
  `phone` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '联系电话',
  `detail` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '收货地址',
  `user_id` int(0) NOT NULL COMMENT '用户ID',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`address_id`) USING BTREE,
  UNIQUE INDEX `address_id`(`address_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '收货地址' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of address
-- ----------------------------
INSERT INTO `address` VALUES ('fe8ead1e-e521-40a9-abe6-8730179cfa2c', '666', '1223424345', '广东省广州市', 105, '2026-06-10 16:32:35');

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`  (
  `book_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '图书ID',
  `category_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分类ID',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '书名',
  `author` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '作者',
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '封面图片路径',
  `price` decimal(10, 2) NOT NULL COMMENT '价格',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '图书描述',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '上架时间',
  PRIMARY KEY (`book_id`(191)) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '图书表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES ('4d4c9860-d331-11f0-a8f9-bc2411d312f6', '642f16b0-59d0-486f-9217-79e8e6c2b9ee', '消失的图书馆', '豪尔赫·路易斯·博尔赫斯', '/profile/upload/2026/06/08/消失的图书馆_20260608131551A001.png', 45.00, '博尔赫斯的经典小说作品', '2025-12-07 13:55:15');
INSERT INTO `book` VALUES ('4d4ca9a2-d331-11f0-a8f9-bc2411d312f6', '642f16b0-59d0-486f-9217-79e8e6c2b9ee', '牛奶与蜂蜜', 'Rupi Kaur', '/profile/upload/2026/06/08/牛奶与蜂蜜_20260608131620A002.png', 42.00, '现代诗歌与散文作品', '2025-12-07 13:55:15');
INSERT INTO `book` VALUES ('4d4cab2b-d331-11f0-a8f9-bc2411d312f6', '642f16b0-59d0-486f-9217-79e8e6c2b9ee', '杀死一只知更鸟', 'Harper Lee', '/profile/upload/2026/06/08/杀死一只知更鸟_20260608131632A003.png', 39.50, '美国文学经典小说', '2025-12-07 13:55:15');
INSERT INTO `book` VALUES ('4d4caba3-d331-11f0-a8f9-bc2411d312f6', '642f16b0-59d0-486f-9217-79e8e6c2b9ee', '百年孤独', '加西亚·马尔克斯', '/profile/upload/2026/06/08/百年孤独_20260608131644A004.png', 42.00, '魔幻现实主义文学代表作', '2025-12-07 13:55:15');
INSERT INTO `book` VALUES ('4d4cac1f-d331-11f0-a8f9-bc2411d312f6', '642f16b0-59d0-486f-9217-79e8e6c2b9ee', '活着', '余华', '/profile/upload/2026/06/08/活着_20260608131654A005.png', 20.00, '中国当代文学经典作品', '2025-12-07 13:55:15');
INSERT INTO `book` VALUES ('4d4cac8e-d331-11f0-a8f9-bc2411d312f6', '642f16b0-59d0-486f-9217-79e8e6c2b9ee', '围城', '钱钟书', '/profile/upload/2026/06/08/围城_20260608131704A006.png', 25.00, '中国现代文学经典讽刺小说', '2025-12-07 13:55:15');
INSERT INTO `book` VALUES ('4d4cacf4-d331-11f0-a8f9-bc2411d312f6', '642f16b0-59d0-486f-9217-79e8e6c2b9ee', '挪威的森林', '村上春树', '/profile/upload/2026/06/08/挪威的森林_20260608131713A007.png', 29.00, '日本现代爱情小说', '2025-12-07 13:55:15');
INSERT INTO `book` VALUES ('4d4cad59-d331-11f0-a8f9-bc2411d312f6', '642f16b0-59d0-486f-9217-79e8e6c2b9ee', '追风筝的人', '卡勒德·胡赛尼', '/profile/upload/2026/06/08/追风筝的人_20260608131724A008.png', 32.00, '阿富汗背景的感人小说', '2025-12-07 13:55:15');
INSERT INTO `book` VALUES ('4d796043-d331-11f0-a8f9-bc2411d312f6', '16b0d3e1-a3ef-4b1f-b24c-5aac3c9facbf', '人类简史', '尤瓦尔·赫拉利', '/profile/upload/2026/06/08/人类简史_20260608131733A009.png', 56.00, '全球畅销的人类历史著作', '2025-12-07 13:55:15');
INSERT INTO `book` VALUES ('4d7964da-d331-11f0-a8f9-bc2411d312f6', '16b0d3e1-a3ef-4b1f-b24c-5aac3c9facbf', '明朝那些事儿', '当年明月', '/profile/upload/2026/06/08/明朝那些事儿_20260608131741A010.png', 28.50, '趣味讲述明朝历史的作品', '2025-12-07 13:55:15');
INSERT INTO `book` VALUES ('4d79656f-d331-11f0-a8f9-bc2411d312f6', '16b0d3e1-a3ef-4b1f-b24c-5aac3c9facbf', '史记', '司马迁', '/profile/upload/2026/06/08/史记_20260608131800A011.png', 48.00, '中国第一部纪传体通史', '2025-12-07 13:55:15');
INSERT INTO `book` VALUES ('4d7965ea-d331-11f0-a8f9-bc2411d312f6', '16b0d3e1-a3ef-4b1f-b24c-5aac3c9facbf', '万历十五年', '黄仁宇', '/profile/upload/2026/06/08/万历十五年_20260608131815A012.png', 22.00, '从万历十五年看明朝历史', '2025-12-07 13:55:15');
INSERT INTO `book` VALUES ('4d79666f-d331-11f0-a8f9-bc2411d312f6', '16b0d3e1-a3ef-4b1f-b24c-5aac3c9facbf', '全球通史', '斯塔夫里阿诺斯', '/profile/upload/2026/06/08/全球通史_20260608131827A013.png', 68.00, '全球视角的历史著作', '2025-12-07 13:55:15');
INSERT INTO `book` VALUES ('4d7966d0-d331-11f0-a8f9-bc2411d312f6', '16b0d3e1-a3ef-4b1f-b24c-5aac3c9facbf', '慈禧全传', '高阳', '/profile/upload/2026/06/08/慈禧全传_20260608131836A014.png', 36.00, '详细记述慈禧太后生平', '2025-12-07 13:55:15');
INSERT INTO `book` VALUES ('4d796734-d331-11f0-a8f9-bc2411d312f6', '16b0d3e1-a3ef-4b1f-b24c-5aac3c9facbf', '曾国藩传', '张宏杰', '/profile/upload/2026/06/08/曾国藩传_20260608131847A015.png', 39.00, '晚清名臣曾国藩的传记', '2025-12-07 13:55:15');
INSERT INTO `book` VALUES ('4d796846-d331-11f0-a8f9-bc2411d312f6', '16b0d3e1-a3ef-4b1f-b24c-5aac3c9facbf', '拿破仑传', '艾米尔·路德维希', '/profile/upload/2026/06/08/拿破仑传_20260608131859A016.png', 35.00, '法国军事家拿破仑的传记', '2025-12-07 13:55:15');
INSERT INTO `book` VALUES ('4d7968bc-d331-11f0-a8f9-bc2411d312f6', '16b0d3e1-a3ef-4b1f-b24c-5aac3c9facbf', '苏东坡传', '林语堂', '/profile/upload/2026/06/08/苏东坡传_20260608131910A017.png', 28.00, '宋代文学家苏轼的传记', '2025-12-07 13:55:15');
INSERT INTO `book` VALUES ('4d796a23-d331-11f0-a8f9-bc2411d312f6', '16b0d3e1-a3ef-4b1f-b24c-5aac3c9facbf', '富兰克林自传', '本杰明·富兰克林', '/profile/upload/2026/06/08/富兰克林自传_20260608131918A018.png', 26.00, '美国开国元勋的自传', '2025-12-07 13:55:15');
INSERT INTO `book` VALUES ('4da585db-d331-11f0-a8f9-bc2411d312f6', '2176371b-0352-4ff2-b503-c3ae727d355d', '乌合之众', '古斯塔夫·勒庞', '/profile/upload/2026/06/08/乌合之众_20260608131927A019.png', 25.00, '研究群体心理学的经典著作', '2025-12-07 13:55:15');
INSERT INTO `book` VALUES ('4da58a0e-d331-11f0-a8f9-bc2411d312f6', '2176371b-0352-4ff2-b503-c3ae727d355d', '社会心理学', '戴维·迈尔斯', '/profile/upload/2026/06/08/社会心理学_20260608131938A020.png', 65.00, '经典的社会心理学教材', '2025-12-07 13:55:15');
INSERT INTO `book` VALUES ('4da58aab-d331-11f0-a8f9-bc2411d312f6', '2176371b-0352-4ff2-b503-c3ae727d355d', '娱乐至死', '尼尔·波兹曼', '/profile/upload/2026/06/08/娱乐至死_20260608131956A021.png', 29.00, '对媒体文化的深刻批判', '2025-12-07 13:55:15');
INSERT INTO `book` VALUES ('4da58b1a-d331-11f0-a8f9-bc2411d312f6', '2176371b-0352-4ff2-b503-c3ae727d355d', '枪炮、病菌与钢铁', '贾雷德·戴蒙德', '/profile/upload/2026/06/08/枪炮、病菌与钢铁_20260608132006A022.png', 45.00, '人类社会发展史的研究', '2025-12-07 13:55:15');
INSERT INTO `book` VALUES ('4da58b88-d331-11f0-a8f9-bc2411d312f6', '2176371b-0352-4ff2-b503-c3ae727d355d', '人类群星闪耀时', '斯蒂芬·茨威格', '/profile/upload/2026/06/08/人类群星闪耀时_20260608132015A023.png', 28.00, '十四篇历史特写', '2025-12-07 13:55:15');
INSERT INTO `book` VALUES ('4da58be9-d331-11f0-a8f9-bc2411d312f6', '2176371b-0352-4ff2-b503-c3ae727d355d', '理想国', '柏拉图', '/profile/upload/2026/06/08/理想国_20260608132024A024.png', 36.00, '古希腊哲学经典著作', '2025-12-07 13:55:15');
INSERT INTO `book` VALUES ('4da58c4c-d331-11f0-a8f9-bc2411d312f6', '2176371b-0352-4ff2-b503-c3ae727d355d', '沉思录', '马可·奥勒留', '/profile/upload/2026/06/08/沉思录_20260608132033A025.png', 22.00, '古罗马皇帝的人生哲学', '2025-12-07 13:55:15');
INSERT INTO `book` VALUES ('4da58cab-d331-11f0-a8f9-bc2411d312f6', '2176371b-0352-4ff2-b503-c3ae727d355d', '梦的解析', '西格蒙德·弗洛伊德', '/profile/upload/2026/06/08/梦的解析_20260608132041A026.png', 38.00, '精神分析学派的奠基之作', '2025-12-07 13:55:15');
INSERT INTO `book` VALUES ('4dd16a3d-d331-11f0-a8f9-bc2411d312f6', '0b949c6f-0a0c-4453-b25d-04892f24574f', '现代汉语词典', '中国社会科学院语言研究所', '/profile/upload/2026/06/08/现代汉语词典_20260608132051A027.png', 89.00, '现代汉语规范词典', '2025-12-07 13:55:16');
INSERT INTO `book` VALUES ('4dd16f4a-d331-11f0-a8f9-bc2411d312f6', '0b949c6f-0a0c-4453-b25d-04892f24574f', '牛津高阶英汉双解词典', '牛津大学出版社', '/profile/upload/2026/06/08/牛津高阶英汉双解词典_20260608132104A028.png', 128.00, '权威英汉双解词典', '2025-12-07 13:55:16');
INSERT INTO `book` VALUES ('4dd16ffc-d331-11f0-a8f9-bc2411d312f6', '0b949c6f-0a0c-4453-b25d-04892f24574f', '新华字典', '中国社会科学院语言研究所', '/profile/upload/2026/06/08/新华字典_20260608132111A029.png', 26.00, '最常用的汉语字典', '2025-12-07 13:55:16');
INSERT INTO `book` VALUES ('4dd1706c-d331-11f0-a8f9-bc2411d312f6', '0b949c6f-0a0c-4453-b25d-04892f24574f', '辞海', '夏征农', '/profile/upload/2026/06/08/辞海_20260608132126A030.png', 98.00, '综合性大型词典', '2025-12-07 13:55:16');
INSERT INTO `book` VALUES ('4dd17170-d331-11f0-a8f9-bc2411d312f6', '0b949c6f-0a0c-4453-b25d-04892f24574f', '中国大百科全书', '中国大百科全书出版社', '/profile/upload/2026/06/08/中国大百科全书_20260608132138A031.png', 780.00, '权威知识性工具书', '2025-12-07 13:55:16');
INSERT INTO `book` VALUES ('4dd171d7-d331-11f0-a8f9-bc2411d312f6', '0b949c6f-0a0c-4453-b25d-04892f24574f', '康熙字典', '张玉书', '/profile/upload/2026/06/08/康熙字典_20260608132146A032.png', 168.00, '清代编纂的汉字字典', '2025-12-07 13:55:16');
INSERT INTO `book` VALUES ('4dd1723d-d331-11f0-a8f9-bc2411d312f6', '0b949c6f-0a0c-4453-b25d-04892f24574f', '英汉翻译大辞典', '林戊荪', '/profile/upload/2026/06/08/英汉翻译大辞典_20260608132155A033.png', 198.00, '专业翻译工具书', '2025-12-07 13:55:16');
INSERT INTO `book` VALUES ('4dd1729b-d331-11f0-a8f9-bc2411d312f6', '0b949c6f-0a0c-4453-b25d-04892f24574f', '中国古代文化常识', '王力', '/profile/upload/2026/06/08/中国古代文化常识_20260608132210A035.png', 45.00, '了解中国古代文化的工具书', '2025-12-07 13:55:16');
INSERT INTO `book` VALUES ('4dfd6467-d331-11f0-a8f9-bc2411d312f6', 'c2238531-a0dd-43c9-9954-4904a09e19d8', '火影忍者', '岸本齐史', '/profile/upload/2026/06/08/火影忍者_20260608132218A036.png', 18.00, '日本经典热血漫画', '2025-12-07 13:55:16');
INSERT INTO `book` VALUES ('4dfd690e-d331-11f0-a8f9-bc2411d312f6', 'c2238531-a0dd-43c9-9954-4904a09e19d8', '海贼王', '尾田荣一郎', '/profile/upload/2026/06/08/海贼王_20260608132227A037.png', 15.00, '航海冒险题材漫画', '2025-12-07 13:55:16');
INSERT INTO `book` VALUES ('4dfd69ac-d331-11f0-a8f9-bc2411d312f6', 'c2238531-a0dd-43c9-9954-4904a09e19d8', '龙珠', '鸟山明', '/profile/upload/2026/06/08/龙珠_20260608132235A038.png', 20.00, '经典格斗漫画', '2025-12-07 13:55:16');
INSERT INTO `book` VALUES ('4dfd6a4d-d331-11f0-a8f9-bc2411d312f6', 'c2238531-a0dd-43c9-9954-4904a09e19d8', '名侦探柯南', '青山刚昌', '/profile/upload/2026/06/08/名侦探柯南_20260608132243A039.png', 12.00, '侦探推理漫画', '2025-12-07 13:55:16');
INSERT INTO `book` VALUES ('4dfd6abf-d331-11f0-a8f9-bc2411d312f6', 'c2238531-a0dd-43c9-9954-4904a09e19d8', '死神', '久保带人', '/profile/upload/2026/06/08/死神_20260608132251A040.png', 16.00, '奇幻战斗漫画', '2025-12-07 13:55:16');
INSERT INTO `book` VALUES ('4dfd6b30-d331-11f0-a8f9-bc2411d312f6', 'c2238531-a0dd-43c9-9954-4904a09e19d8', '银魂', '空知英秋', '/profile/upload/2026/06/08/银魂_20260608132258A041.png', 14.00, '搞笑吐槽漫画', '2025-12-07 13:55:16');
INSERT INTO `book` VALUES ('4dfd6b8a-d331-11f0-a8f9-bc2411d312f6', 'c2238531-a0dd-43c9-9954-4904a09e19d8', '全职猎人', '富坚义博', '/profile/upload/2026/06/08/全职猎人_20260608132312A042.png', 17.00, '冒险战斗漫画', '2025-12-07 13:55:16');
INSERT INTO `book` VALUES ('4dfd6bde-d331-11f0-a8f9-bc2411d312f6', 'c2238531-a0dd-43c9-9954-4904a09e19d8', '网球王子', '许斐刚', '/profile/upload/2026/06/08/网球王子_20260608132320A043.png', 13.00, '体育竞技漫画', '2025-12-07 13:55:16');

-- ----------------------------
-- Table structure for cart
-- ----------------------------
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart`  (
  `cart_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '购物车ID',
  `book_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '图书ID',
  `quantity` int(0) NOT NULL COMMENT '数量',
  `user_id` int(0) NOT NULL COMMENT '用户ID',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '加入购物车时间',
  PRIMARY KEY (`cart_id`) USING BTREE,
  UNIQUE INDEX `cart_id`(`cart_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '购物车' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cart
-- ----------------------------
INSERT INTO `cart` VALUES ('16fc5b7d-782a-4cfb-a1af-83139bc8d95f', '4dd1729b-d331-11f0-a8f9-bc2411d312f6', 2, 105, '2026-06-10 15:45:07');
INSERT INTO `cart` VALUES ('9a80db24-b15a-4dba-ab3e-639b1a324ea5', '4dd171d7-d331-11f0-a8f9-bc2411d312f6', 1, 105, '2026-06-10 16:03:17');
INSERT INTO `cart` VALUES ('fab51a2b-69f1-4682-8106-e80374b51ec0', '4dd1706c-d331-11f0-a8f9-bc2411d312f6', 1, 105, '2026-06-10 15:44:29');

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `category_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分类ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分类名称',
  `sort` int(0) NOT NULL COMMENT '排序',
  PRIMARY KEY (`category_id`) USING BTREE,
  UNIQUE INDEX `category_id`(`category_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '图书分类' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES ('0b949c6f-0a0c-4453-b25d-04892f24574f', '工具书', 4);
INSERT INTO `category` VALUES ('16b0d3e1-a3ef-4b1f-b24c-5aac3c9facbf', '历史传记', 3);
INSERT INTO `category` VALUES ('2176371b-0352-4ff2-b503-c3ae727d355d', '人文社科', 2);
INSERT INTO `category` VALUES ('642f16b0-59d0-486f-9217-79e8e6c2b9ee', '文学小说', 1);
INSERT INTO `category` VALUES ('c2238531-a0dd-43c9-9954-4904a09e19d8', '青春动漫', 5);

-- ----------------------------
-- Table structure for gen_table
-- ----------------------------
DROP TABLE IF EXISTS `gen_table`;
CREATE TABLE `gen_table`  (
  `table_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '表名称',
  `table_comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '表描述',
  `sub_table_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '关联子表的表名',
  `sub_table_fk_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '子表关联的外键名',
  `class_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '实体类名称',
  `tpl_category` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT 'crud' COMMENT '使用的模板（crud单表操作 tree树表操作）',
  `tpl_web_type` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '前端模板类型（element-ui模版 element-plus模版）',
  `package_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '生成包路径',
  `module_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '生成模块名',
  `business_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '生成业务名',
  `function_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '生成功能名',
  `function_author` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '生成功能作者',
  `gen_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '生成代码方式（0zip压缩包 1自定义路径）',
  `gen_path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '/' COMMENT '生成路径（不填默认项目路径）',
  `options` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '其它生成选项',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`table_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '代码生成业务表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_table_column
-- ----------------------------
DROP TABLE IF EXISTS `gen_table_column`;
CREATE TABLE `gen_table_column`  (
  `column_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_id` bigint(0) NULL DEFAULT NULL COMMENT '归属表编号',
  `column_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '列名称',
  `column_comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '列描述',
  `column_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '列类型',
  `java_type` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'JAVA类型',
  `java_field` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'JAVA字段名',
  `is_pk` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '是否主键（1是）',
  `is_increment` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '是否自增（1是）',
  `is_required` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '是否必填（1是）',
  `is_insert` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '是否为插入字段（1是）',
  `is_edit` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '是否编辑字段（1是）',
  `is_list` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '是否列表字段（1是）',
  `is_query` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '是否查询字段（1是）',
  `query_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT 'EQ' COMMENT '查询方式（等于、不等于、大于、小于、范围）',
  `html_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
  `dict_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '字典类型',
  `sort` int(0) NULL DEFAULT NULL COMMENT '排序',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`column_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 176 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '代码生成业务表字段' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of gen_table_column
-- ----------------------------
INSERT INTO `gen_table_column` VALUES (157, 13, 'menu_id', '菜单ID', 'bigint', 'Long', 'menuId', '1', '1', '0', '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, 'admin', '2025-12-05 22:55:22', '', NULL);
INSERT INTO `gen_table_column` VALUES (158, 13, 'menu_name', '菜单名称', 'varchar(50)', 'String', 'menuName', '0', '0', '0', '1', '1', '1', '1', 'LIKE', 'input', '', 2, 'admin', '2025-12-05 22:55:22', '', NULL);
INSERT INTO `gen_table_column` VALUES (159, 13, 'parent_id', '父菜单ID', 'bigint', 'Long', 'parentId', '0', '0', '0', '1', '1', '1', '1', 'EQ', 'input', '', 3, 'admin', '2025-12-05 22:55:22', '', NULL);
INSERT INTO `gen_table_column` VALUES (160, 13, 'order_num', '显示顺序', 'int', 'Long', 'orderNum', '0', '0', '0', '1', '1', '1', '1', 'EQ', 'input', '', 4, 'admin', '2025-12-05 22:55:22', '', NULL);
INSERT INTO `gen_table_column` VALUES (161, 13, 'path', '路由地址', 'varchar(200)', 'String', 'path', '0', '0', '0', '1', '1', '1', '1', 'EQ', 'input', '', 5, 'admin', '2025-12-05 22:55:22', '', NULL);
INSERT INTO `gen_table_column` VALUES (162, 13, 'component', '组件路径', 'varchar(255)', 'String', 'component', '0', '0', '0', '1', '1', '1', '1', 'EQ', 'input', '', 6, 'admin', '2025-12-05 22:55:22', '', NULL);
INSERT INTO `gen_table_column` VALUES (163, 13, 'query', '路由参数', 'varchar(255)', 'String', 'query', '0', '0', '0', '1', '1', '1', '1', 'EQ', 'input', '', 7, 'admin', '2025-12-05 22:55:22', '', NULL);
INSERT INTO `gen_table_column` VALUES (164, 13, 'route_name', '路由名称', 'varchar(50)', 'String', 'routeName', '0', '0', '0', '1', '1', '1', '1', 'LIKE', 'input', '', 8, 'admin', '2025-12-05 22:55:22', '', NULL);
INSERT INTO `gen_table_column` VALUES (165, 13, 'is_frame', '是否为外链（0是 1否）', 'int', 'Long', 'isFrame', '0', '0', '0', '1', '1', '1', '1', 'EQ', 'input', '', 9, 'admin', '2025-12-05 22:55:22', '', NULL);
INSERT INTO `gen_table_column` VALUES (166, 13, 'is_cache', '是否缓存（0缓存 1不缓存）', 'int', 'Long', 'isCache', '0', '0', '0', '1', '1', '1', '1', 'EQ', 'input', '', 10, 'admin', '2025-12-05 22:55:22', '', NULL);
INSERT INTO `gen_table_column` VALUES (167, 13, 'menu_type', '菜单类型（M目录 C菜单 F按钮）', 'char(1)', 'String', 'menuType', '0', '0', '0', '1', '1', '1', '1', 'EQ', 'select', '', 11, 'admin', '2025-12-05 22:55:22', '', NULL);
INSERT INTO `gen_table_column` VALUES (168, 13, 'visible', '菜单状态（0显示 1隐藏）', 'char(1)', 'String', 'visible', '0', '0', '0', '1', '1', '1', '1', 'EQ', 'input', '', 12, 'admin', '2025-12-05 22:55:22', '', NULL);
INSERT INTO `gen_table_column` VALUES (169, 13, 'status', '菜单状态（0正常 1停用）', 'char(1)', 'String', 'status', '0', '0', '0', '1', '1', '1', '1', 'EQ', 'radio', '', 13, 'admin', '2025-12-05 22:55:22', '', NULL);
INSERT INTO `gen_table_column` VALUES (170, 13, 'icon', '菜单图标', 'varchar(100)', 'String', 'icon', '0', '0', '0', '1', '1', '1', '1', 'EQ', 'input', '', 14, 'admin', '2025-12-05 22:55:22', '', NULL);
INSERT INTO `gen_table_column` VALUES (171, 13, 'create_by', '创建者', 'varchar(64)', 'String', 'createBy', '0', '0', '0', '1', NULL, NULL, NULL, 'EQ', 'input', '', 15, 'admin', '2025-12-05 22:55:22', '', NULL);
INSERT INTO `gen_table_column` VALUES (172, 13, 'create_time', '创建时间', 'datetime', 'Date', 'createTime', '0', '0', '0', '1', NULL, NULL, NULL, 'EQ', 'datetime', '', 16, 'admin', '2025-12-05 22:55:22', '', NULL);
INSERT INTO `gen_table_column` VALUES (173, 13, 'update_by', '更新者', 'varchar(64)', 'String', 'updateBy', '0', '0', '0', '1', '1', NULL, NULL, 'EQ', 'input', '', 17, 'admin', '2025-12-05 22:55:22', '', NULL);
INSERT INTO `gen_table_column` VALUES (174, 13, 'update_time', '更新时间', 'datetime', 'Date', 'updateTime', '0', '0', '0', '1', '1', NULL, NULL, 'EQ', 'datetime', '', 18, 'admin', '2025-12-05 22:55:22', '', NULL);
INSERT INTO `gen_table_column` VALUES (175, 13, 'remark', '备注', 'varchar(500)', 'String', 'remark', '0', '0', '0', '1', '1', '1', NULL, 'EQ', 'textarea', '', 19, 'admin', '2025-12-05 22:55:22', '', NULL);

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '菜单名称',
  `parent_id` bigint(0) NULL DEFAULT 0 COMMENT '父菜单ID',
  `order_num` int(0) NULL DEFAULT 0 COMMENT '显示顺序',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '组件路径',
  `query` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '路由参数',
  `route_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '路由名称',
  `is_frame` int(0) NULL DEFAULT 1 COMMENT '是否为外链（0是 1否）',
  `is_cache` int(0) NULL DEFAULT 1 COMMENT '是否缓存（0缓存 1不缓存）',
  `menu_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2008 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '菜单权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '系统管理', 0, 99, 'system', NULL, '', '', 1, 0, 'M', '0', '0', '系统管理', 'admin', '2025-08-30 08:59:54', 'admin', '2026-05-05 10:21:50', '系统管理目录');
INSERT INTO `sys_menu` VALUES (100, '用户管理', 1, 1, 'user', 'system/user/index', '', '', 1, 1, 'C', '0', '0', '用户管理', 'admin', '2025-08-30 08:59:54', 'admin', '2025-12-06 12:10:49', '用户管理菜单');
INSERT INTO `sys_menu` VALUES (101, '角色管理', 1, 2, 'role', 'system/role/index', '', '', 1, 1, 'C', '0', '0', '角色管理', 'admin', '2025-08-30 08:59:54', 'admin', '2025-09-01 11:26:22', '角色管理菜单');
INSERT INTO `sys_menu` VALUES (102, '菜单管理', 1, 3, 'menu', 'system/menu/index', '', '', 1, 1, 'C', '0', '0', '菜单管理', 'admin', '2025-08-30 08:59:54', 'admin', '2025-09-01 11:26:28', '菜单管理菜单');
INSERT INTO `sys_menu` VALUES (116, '代码生成', 1, 4, 'gen', 'tool/gen/index', '', '', 1, 1, 'C', '0', '0', '代码生成', 'admin', '2025-08-30 08:59:54', 'admin', '2026-06-03 16:14:36', '代码生成菜单');
INSERT INTO `sys_menu` VALUES (2006, '图书分类管理', 0, 1, 'category', 'mall/category/index', NULL, '', 1, 1, 'C', '0', '0', '图书分类管理', 'admin', '2026-05-05 10:25:45', 'admin', '2026-05-05 10:53:35', '');
INSERT INTO `sys_menu` VALUES (2007, '图书管理', 0, 2, 'book', 'mall/book/index', NULL, '', 1, 1, 'C', '0', '0', '图书管理', 'admin', '2026-06-03 16:35:55', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2008, '收获地址管理', 0, 4, 'address', 'mall/address/index', NULL, '', 1, 1, 'C', '0', '0', '收获地址', 'admin', '2026-06-10 15:00:37', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2009, '购物车管理', 0, 3, 'cart', 'mall/cart/index', NULL, '', 1, 1, 'C', '0', '0', '购物车空', 'admin', '2026-06-10 15:06:09', 'admin', '2026-06-10 16:05:57', '');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '角色权限字符串',
  `role_sort` int(0) NOT NULL COMMENT '显示顺序',
  `data_scope` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `menu_check_strictly` tinyint(1) NULL DEFAULT 1 COMMENT '菜单树选择项是否关联显示',
  `dept_check_strictly` tinyint(1) NULL DEFAULT 1 COMMENT '部门树选择项是否关联显示',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '角色信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'admin', 1, '1', 1, 1, '0', '0', 'admin', '2025-08-30 08:59:54', '', NULL, '超级管理员');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` bigint(0) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(0) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '角色和菜单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户账号',
  `nick_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户昵称',
  `user_type` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '00' COMMENT '用户类型（00系统用户）',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '手机号码',
  `sex` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '头像地址',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '密码',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '账号状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `login_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime(0) NULL DEFAULT NULL COMMENT '最后登录时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 105 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '系统管理员', '00', 'huacai@163.com', '15888888888', '1', '', 'admin123', '0', '0', '127.0.0.1', '2026-06-10 15:59:40', 'admin', '2025-08-30 08:59:53', '', '2026-06-10 15:59:39', '管理员');
INSERT INTO `sys_user` VALUES (104, '123', '123', '00', '', '', '0', '', '123456', '0', '0', '', '2026-06-03 16:03:37', '', '2026-03-14 11:02:49', '', '2026-06-03 16:03:37', NULL);
INSERT INTO `sys_user` VALUES (105, '666', '666', '00', '', '', '0', '', '666666', '0', '0', '', '2026-06-10 15:56:58', '', '2026-06-10 15:28:40', '', '2026-06-10 15:56:58', NULL);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` bigint(0) NOT NULL COMMENT '用户ID',
  `role_id` bigint(0) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户和角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);

SET FOREIGN_KEY_CHECKS = 1;
