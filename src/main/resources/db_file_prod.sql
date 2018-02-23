/*
Source Server         : engwen
Source Database       : db_file_prod

Target Server Type    : MYSQL
Target Server Version : 50051
File Encoding         : utf-8

*/

SET FOREIGN_KEY_CHECKS=0;
DROP DATABASE IF EXISTS `db_file_prod`;

CREATE DATABASE `db_file_prod`
    CHARACTER SET 'utf8'
    COLLATE 'utf8_general_ci';

USE `db_file_prod`;

-- ----------------------------
-- Table structure for `omfile`
-- ----------------------------
DROP TABLE IF EXISTS `omfile`;
CREATE TABLE `omfile`(
  `md5` varchar(100) NOT NULL COMMENT 'MD5值',
  `upload_time` datetime NOT NULL COMMENT '上传时间'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文件表';
