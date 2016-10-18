/*
SQLyog Ultimate v11.25 (64 bit)
MySQL - 5.7.13-log : Database - myblog
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`myblog` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `myblog`;

/*Table structure for table `blog` */

CREATE TABLE `blog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) DEFAULT NULL COMMENT '博客标题',
  `release_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
  `content` text COMMENT '博客内容',
  `review` int(11) DEFAULT '0' COMMENT '评论条数',
  `keyword` varchar(50) DEFAULT NULL COMMENT '关键词',
  `summary` varchar(300) DEFAULT NULL COMMENT '摘要',
  `reading` int(11) DEFAULT '0' COMMENT '阅读次数',
  `type_id` int(11) DEFAULT '1' COMMENT '博客类型',
  `image` varchar(100) DEFAULT '20161008/10082133851721.jpg' COMMENT '图片',
  PRIMARY KEY (`id`),
  KEY `type_id` (`type_id`),
  CONSTRAINT `blog_ibfk_1` FOREIGN KEY (`type_id`) REFERENCES `blog_type` (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8;

/*Table structure for table `blog_type` */

CREATE TABLE `blog_type` (
  `type_id` int(11) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(30) DEFAULT NULL COMMENT '类型名称',
  `order_no` int(11) DEFAULT NULL COMMENT '序号',
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*Table structure for table `blogger` */

CREATE TABLE `blogger` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nick_name` varchar(30) DEFAULT NULL COMMENT '昵称',
  `signature` varchar(50) DEFAULT NULL COMMENT '个性签名',
  `image_url` varchar(50) DEFAULT NULL COMMENT '头像地址',
  `profile` text COMMENT '简介',
  `user_name` varchar(30) DEFAULT NULL COMMENT '登录名',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Table structure for table `comment` */

CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` text COMMENT '评论内容',
  `comment_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '评论时间',
  `is_pass` tinyint(1) DEFAULT '0' COMMENT '是否通过',
  `blog_id` int(11) DEFAULT NULL COMMENT '博客',
  `user_name` varchar(30) DEFAULT NULL COMMENT '用户',
  `reply` text COMMENT '作者回复',
  `parent_id` int(11) DEFAULT NULL COMMENT '父级评论id',
  `reply_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '评论回复时间',
  PRIMARY KEY (`id`),
  KEY `blog_id` (`blog_id`),
  KEY `parent_id` (`parent_id`),
  CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`blog_id`) REFERENCES `blog` (`id`),
  CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`parent_id`) REFERENCES `comment` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

/*Table structure for table `link` */

CREATE TABLE `link` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `link_name` varchar(50) DEFAULT NULL COMMENT '链接名称',
  `link_url` varchar(100) DEFAULT NULL COMMENT '链接地址',
  `order_no` int(11) DEFAULT NULL COMMENT '序号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

/*Table structure for table `user` */

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(30) DEFAULT NULL COMMENT '用户名',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `website` varchar(100) DEFAULT NULL COMMENT '网站',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
