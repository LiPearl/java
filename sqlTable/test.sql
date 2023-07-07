/*
Navicat MySQL Data Transfer

Source Server         : root
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-12-26 12:51:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for applyorder
-- ----------------------------
DROP TABLE IF EXISTS `applyorder`;
CREATE TABLE `applyorder` (
  `order_id` int(11) NOT NULL,
  `username` varchar(10) NOT NULL,
  `apply_time` datetime NOT NULL,
  PRIMARY KEY (`order_id`),
  CONSTRAINT `apply_orderid` FOREIGN KEY (`order_id`) REFERENCES `taskorder` (`order_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for city
-- ----------------------------
DROP TABLE IF EXISTS `city`;
CREATE TABLE `city` (
  `city_name` varchar(20) NOT NULL,
  `task_num` int(11) NOT NULL,
  `fin_order_num` int(11) NOT NULL,
  PRIMARY KEY (`city_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for login_record
-- ----------------------------
DROP TABLE IF EXISTS `login_record`;
CREATE TABLE `login_record` (
  `rid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(10) NOT NULL,
  `login_time` datetime NOT NULL,
  PRIMARY KEY (`rid`),
  KEY `loginrecord_username_idx` (`username`),
  CONSTRAINT `loginrecord_username` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=310 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for logs
-- ----------------------------
DROP TABLE IF EXISTS `logs`;
CREATE TABLE `logs` (
  `logid` int(11) NOT NULL AUTO_INCREMENT,
  `time` datetime NOT NULL,
  `table_name` varchar(20) NOT NULL,
  `operation` varchar(10) NOT NULL,
  `key_value` varchar(10) NOT NULL,
  PRIMARY KEY (`logid`)
) ENGINE=InnoDB AUTO_INCREMENT=1651 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for photo
-- ----------------------------
DROP TABLE IF EXISTS `photo`;
CREATE TABLE `photo` (
  `photo_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL,
  `photo_blob` longblob NOT NULL,
  PRIMARY KEY (`photo_id`),
  KEY `photo_orderid` (`order_id`),
  CONSTRAINT `photo_orderid` FOREIGN KEY (`order_id`) REFERENCES `taskorder` (`order_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for portrait
-- ----------------------------
DROP TABLE IF EXISTS `portrait`;
CREATE TABLE `portrait` (
  `username` varchar(10) NOT NULL,
  `portrait_blob` longblob NOT NULL,
  PRIMARY KEY (`username`),
  CONSTRAINT `portrait_username` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for publisher
-- ----------------------------
DROP TABLE IF EXISTS `publisher`;
CREATE TABLE `publisher` (
  `publisher_name` varchar(10) NOT NULL,
  `task_num` int(11) NOT NULL,
  `last_pub_time` datetime DEFAULT NULL,
  PRIMARY KEY (`publisher_name`),
  KEY `publishname_username` (`publisher_name`),
  CONSTRAINT `publishname_username` FOREIGN KEY (`publisher_name`) REFERENCES `user` (`username`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for reflag
-- ----------------------------
DROP TABLE IF EXISTS `reflag`;
CREATE TABLE `reflag` (
  `flag` int(11) NOT NULL,
  PRIMARY KEY (`flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rns
-- ----------------------------
DROP TABLE IF EXISTS `rns`;
CREATE TABLE `rns` (
  `username` varchar(20) NOT NULL,
  `realname` varchar(45) NOT NULL,
  `sex` varchar(4) NOT NULL,
  `idnumber` varchar(18) NOT NULL,
  `phonenum` varchar(11) NOT NULL,
  `address` varchar(512) NOT NULL,
  PRIMARY KEY (`username`),
  CONSTRAINT `rns_username` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for taskinfo
-- ----------------------------
DROP TABLE IF EXISTS `taskinfo`;
CREATE TABLE `taskinfo` (
  `task_id` int(11) NOT NULL AUTO_INCREMENT,
  `task_name` text NOT NULL,
  `city_name` varchar(20) NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `price` int(11) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `introduction` text NOT NULL,
  `applies_limit` int(11) NOT NULL,
  PRIMARY KEY (`task_id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for taskorder
-- ----------------------------
DROP TABLE IF EXISTS `taskorder`;
CREATE TABLE `taskorder` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT,
  `task_id` int(11) NOT NULL,
  `lastload_time` datetime DEFAULT NULL,
  `finish_time` datetime DEFAULT NULL,
  `is_accept` int(1) DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `taskorder_taskid` (`task_id`),
  CONSTRAINT `taskorder_taskid` FOREIGN KEY (`task_id`) REFERENCES `taskinfo` (`task_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for taskpublish
-- ----------------------------
DROP TABLE IF EXISTS `taskpublish`;
CREATE TABLE `taskpublish` (
  `task_id` int(11) NOT NULL,
  `publisher_name` varchar(10) NOT NULL,
  `publish_time` datetime NOT NULL,
  `applies` int(11) NOT NULL,
  PRIMARY KEY (`task_id`),
  KEY `taskpublish_taskid` (`task_id`),
  KEY `taskpublish_publishername` (`publisher_name`),
  CONSTRAINT `taskpublish_publishername` FOREIGN KEY (`publisher_name`) REFERENCES `publisher` (`publisher_name`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `taskpublish_taskid` FOREIGN KEY (`task_id`) REFERENCES `taskinfo` (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `username` varchar(10) NOT NULL,
  `password` varchar(255) NOT NULL,
  `credit` int(11) NOT NULL,
  `wallet` int(11) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Procedure structure for acceptOrder
-- ----------------------------
DROP PROCEDURE IF EXISTS `acceptOrder`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `acceptOrder`(order_id int)
BEGIN
	declare flag int;
	if exists(select 0 from taskorder where taskorder.order_id=order_id and finish_time is not null) then
		update taskorder set is_accept=1 where taskorder.order_id=order_id;
        update user set wallet=wallet+(select price from taskinfo where taskinfo.task_id=(select taskorder.task_id from taskorder where taskorder.order_id=order_id)),credit=credit+1
		where user.username=(select applyorder.username from applyorder where applyorder.order_id=order_id);
		select 1 into flag;
	else
		select 0 into flag;
    end if;
    select flag;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for addCity
-- ----------------------------
DROP PROCEDURE IF EXISTS `addCity`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `addCity`(cityName varchar(20))
BEGIN
	insert into city(cityName,taskNum) values(cityName,0);
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for addLog
-- ----------------------------
DROP PROCEDURE IF EXISTS `addLog`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `addLog`(table_name varchar(20),operation varchar(10),key_value varchar(10))
BEGIN
	declare nowtime datetime DEFAULT CURRENT_TIMESTAMP;
	insert into logs(time,table_name,operation,key_value) values(nowtime,table_name,operation,key_value);
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for addMoney
-- ----------------------------
DROP PROCEDURE IF EXISTS `addMoney`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `addMoney`(username varchar(10),money int)
BEGIN
	update user set wallet=wallet+money where user.username=username;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for addOrder
-- ----------------------------
DROP PROCEDURE IF EXISTS `addOrder`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `addOrder`(username varchar(20),task_id int)
BEGIN
	declare order_id int;
	declare nowtime datetime DEFAULT CURRENT_TIMESTAMP;
    declare flag int;
	if (select applies from taskpublish where taskpublish.task_id=task_id)!=(select applies_limit from taskinfo where taskinfo.task_id=task_id) and exists(select user.credit from user where user.username=username and credit>100) then
		insert into taskOrder(task_id)	values(task_id);
        select LAST_INSERT_ID() into order_id;
        insert into applyorder	values(order_id,username,nowtime);
		select 1 into flag;
	else
		select 0 into flag;
	end if;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for addPublisher
-- ----------------------------
DROP PROCEDURE IF EXISTS `addPublisher`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `addPublisher`(username varchar(10))
BEGIN
	declare credit int;
	declare isPublisher int;
	declare flag int;
    select user.credit into credit from user where user.username=username;
	if credit>=200 and not exists(select 0 from publisher where publisher_name=username)then
		insert into publisher(publisher_name,task_num) values(username,0);
		select 1 into flag;
	else
		select 0 into flag;
	end if;
	select flag;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for addRNS
-- ----------------------------
DROP PROCEDURE IF EXISTS `addRNS`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `addRNS`(username varchar(10),realname varchar(45),sex varchar(2),idnumber varchar(18),phonenum varchar(11),address varchar(512))
BEGIN
	insert into rns values(username,sex,realname,idnumber,phonenum,address);
	update user set credit=credit+100 where user.username=username;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for addTask
-- ----------------------------
DROP PROCEDURE IF EXISTS `addTask`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `addTask`(task_name varchar(45),publisher_name varchar(10),city_name varchar(20),latitude double,longitude double,price int,start_date date,end_date date,introduction varchar(1024),applies_limit int)
BEGIN
	declare task_id int;
	declare nowtime datetime DEFAULT CURRENT_TIMESTAMP;
    declare flag int;
    if exists(select publisher_name from publisher,user where publisher.publisher_name=publisher_name and username=publisher_name and user.credit>=200 and user.wallet>=price*applies_limit)then
		insert into taskinfo(task_name,city_name,latitude,longitude,price,start_date,end_date,introduction,applies_limit)
			values(task_name,city_name,latitude,longitude,price,start_date,end_date,introduction,applies_limit);
        
		select LAST_INSERT_ID() into task_id;
		insert into taskpublish(publisher_name,task_id,publish_time,applies) 
			values(publisher_name,task_id,nowtime,0);
		update user set wallet=wallet-price*applies_limit where username=publisher_name;
		select 1 into flag;
	else
		select 0 into flag;
    end if;
    select flag;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for addUser
-- ----------------------------
DROP PROCEDURE IF EXISTS `addUser`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `addUser`(username varchar(10),password varchar(255))
BEGIN
	declare flag int;
	if exists(select user.username from user where user.username=username) then
		select 0 into flag;
	else
		insert into user(username,password,credit,wallet) VALUES(username,password(password),100,0);
        select 1 into flag;
    end if;
    select flag;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for checkDuplicate
-- ----------------------------
DROP PROCEDURE IF EXISTS `checkDuplicate`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `checkDuplicate`()
BEGIN
	select a.photo_id,b.photo_id from photo a,photo b 
		where a.photo_id!=b.photo_id and a.photo_blob=b.photo_blob;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for delOrderByOrder_id
-- ----------------------------
DROP PROCEDURE IF EXISTS `delOrderByOrder_id`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `delOrderByOrder_id`(order_id int)
BEGIN
	if exists(select 0 from taskorder where taskorder.order_id=order_id and finish_time is not null)then
		delete from applyorder where applyorder.order_id=order_id;
	else
		delete from taskorder where taskorder.order_id=order_id;
	end if;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for delPhotoByPhoto_id
-- ----------------------------
DROP PROCEDURE IF EXISTS `delPhotoByPhoto_id`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `delPhotoByPhoto_id`(photo_id int)
BEGIN
	delete from photo where photo.photo_id=photo_id;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for delPublish
-- ----------------------------
DROP PROCEDURE IF EXISTS `delPublish`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `delPublish`(task_id int)
BEGIN
	declare flag int;
    declare nowdate date DEFAULT CURRENT_TIMESTAMP;
    if	(select end_date from taskinfo where taskinfo.task_id=task_id)<nowdate then
		delete from taskpublish where taskpublish.task_id=task_id;
		select 1 into flag;
	else
		select 0 into flag;
	end if;
    select flag;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for downloadPhotoByPhoto_id
-- ----------------------------
DROP PROCEDURE IF EXISTS `downloadPhotoByPhoto_id`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `downloadPhotoByPhoto_id`(photo_id int)
BEGIN
	select photo_blob from photo where photo.photo_id=photo_id;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for downloadPortrait
-- ----------------------------
DROP PROCEDURE IF EXISTS `downloadPortrait`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `downloadPortrait`(portrait_id int)
BEGIN
	select portrait_blob from portrait where portrait.portrait_id=portrait_id;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for finishOrder
-- ----------------------------
DROP PROCEDURE IF EXISTS `finishOrder`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `finishOrder`(order_id int)
BEGIN
	declare flag int;
    declare nowtime datetime DEFAULT CURRENT_TIMESTAMP;
	if exists(select order_id from photo where photo.order_id=order_id) then
		update taskorder set finish_time=nowtime where taskorder.order_id=order_id;
		select 1 into flag;
	else
		select 0 into flag;
	end if;
	select flag;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for getAllTaskInfoByPublisher_name
-- ----------------------------
DROP PROCEDURE IF EXISTS `getAllTaskInfoByPublisher_name`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllTaskInfoByPublisher_name`(publisher_name varchar(10))
BEGIN
	select * from taskinfo where taskinfo.task_id
		in (select task_id from taskpublish where taskpublish.publisher_name=publisher_name);
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for getAllTask_idByPublisher_name
-- ----------------------------
DROP PROCEDURE IF EXISTS `getAllTask_idByPublisher_name`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllTask_idByPublisher_name`(publisher_name varchar(10))
BEGIN
    select task_id from taskpublish where taskpublish.publisher_name=publisher_name;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for getApplyOrder
-- ----------------------------
DROP PROCEDURE IF EXISTS `getApplyOrder`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `getApplyOrder`(username varchar(10))
BEGIN
	select * from applyorder where applyorder.username=username;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for getApplyOrderByOrder_id
-- ----------------------------
DROP PROCEDURE IF EXISTS `getApplyOrderByOrder_id`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `getApplyOrderByOrder_id`(order_id int)
BEGIN
	select * from applyorder where applyorder.order_id=order_id;

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for getArroundTaskByGPS
-- ----------------------------
DROP PROCEDURE IF EXISTS `getArroundTaskByGPS`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `getArroundTaskByGPS`(username varchar(10),city_name varchar(20),latitude double,longitude double)
BEGIN
	declare nowdate date DEFAULT CURRENT_TIMESTAMP;
	select * from taskinfo 
	where taskinfo.city_name=city_name 
		and not exists (select 0 from taskpublish 
						where (taskpublish.task_id=taskinfo.task_id
								and (taskpublish.publisher_name=username
										or taskpublish.applies=taskinfo.applies_limit)
                                )
						)
		and taskinfo.task_id not in (select taskorder.task_id from taskorder 
						where taskorder.order_id in
							(select applyorder.order_id from applyorder
							where applyorder.username=username) and taskorder.finish_time is null
						)
		and taskinfo.end_date>nowdate
	order by (taskinfo.latitude-latitude)*(taskinfo.latitude-latitude)+(taskinfo.longitude-longitude)*(taskinfo.longitude-longitude)
    limit 0,100;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for getComApplyorderByUsername
-- ----------------------------
DROP PROCEDURE IF EXISTS `getComApplyorderByUsername`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `getComApplyorderByUsername`(username varchar(10))
BEGIN
	select * from applyorder,taskorder where applyorder.username=username and taskorder.order_id=applyorder.order_id and taskorder.finish_time is not null;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for getComOrderByUsername
-- ----------------------------
DROP PROCEDURE IF EXISTS `getComOrderByUsername`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `getComOrderByUsername`(username varchar(10))
BEGIN
	select * from taskorder where taskorder.order_id in (select order_id from applyorder where applyorder.username=username) and finish_time is not null;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for getInComApplyorderByUsername
-- ----------------------------
DROP PROCEDURE IF EXISTS `getInComApplyorderByUsername`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `getInComApplyorderByUsername`(username varchar(10))
BEGIN
	select * from applyorder,taskorder where applyorder.username=username and taskorder.order_id=applyorder.order_id and taskorder.finish_time is null;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for getInComOrderByUsername
-- ----------------------------
DROP PROCEDURE IF EXISTS `getInComOrderByUsername`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `getInComOrderByUsername`(username varchar(10))
BEGIN
	select * from taskorder where taskorder.order_id in (select order_id from applyorder where applyorder.username=username) and finish_time is null;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for getOrderByTask_id
-- ----------------------------
DROP PROCEDURE IF EXISTS `getOrderByTask_id`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `getOrderByTask_id`(task_id int)
BEGIN
	select * from taskorder
		where taskorder.task_id=task_id;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for getOrderPartByTask_id
-- ----------------------------
DROP PROCEDURE IF EXISTS `getOrderPartByTask_id`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `getOrderPartByTask_id`(task_id int,start int,over int)
BEGIN
	declare length int;
    set length=over-start;
	select * from taskorder
		where taskorder.task_id=task_id limit start,length;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for getPhotoNumByOrder_id
-- ----------------------------
DROP PROCEDURE IF EXISTS `getPhotoNumByOrder_id`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `getPhotoNumByOrder_id`(order_id int)
BEGIN
	select count(*) from photo where photo.order_id=order_id;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for getPhoto_idByOrder_id
-- ----------------------------
DROP PROCEDURE IF EXISTS `getPhoto_idByOrder_id`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `getPhoto_idByOrder_id`(order_id int)
BEGIN
	select photo_id from photo where photo.order_id=order_id;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for getPortraitByUsername
-- ----------------------------
DROP PROCEDURE IF EXISTS `getPortraitByUsername`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `getPortraitByUsername`(username varchar(10))
BEGIN
	select portrait.portrait_blob from portrait where portrait.username=username;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for getRNS
-- ----------------------------
DROP PROCEDURE IF EXISTS `getRNS`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `getRNS`(username varchar(10))
BEGIN
	select * from rns where rns.username=username;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for getTask
-- ----------------------------
DROP PROCEDURE IF EXISTS `getTask`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `getTask`(task_id int)
BEGIN
	select * from taskinfo where taskinfo.task_id=task_id;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for getTaskByTask_id
-- ----------------------------
DROP PROCEDURE IF EXISTS `getTaskByTask_id`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `getTaskByTask_id`(task_id int)
BEGIN
	select * from taskinfo where taskinfo.task_id=task_id;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for getTaskinfobyOrder_id
-- ----------------------------
DROP PROCEDURE IF EXISTS `getTaskinfobyOrder_id`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `getTaskinfobyOrder_id`(order_id int)
BEGIN
	select * from taskinfo where taskinfo.task_id=
		(select taskorder.task_id from taskorder where taskorder.order_id=order_id);
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for getTaskPart
-- ----------------------------
DROP PROCEDURE IF EXISTS `getTaskPart`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `getTaskPart`(task_id int,start int,over int)
BEGIN
	declare length int;
    set length=over-start;
	select * from taskinfo where taskinfo.task_id=task_id limit start,length;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for getTaskPublish
-- ----------------------------
DROP PROCEDURE IF EXISTS `getTaskPublish`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `getTaskPublish`(task_id int)
BEGIN
	select * from taskpublish where taskpublish.task_id=task_id;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for getTaskPublishPart
-- ----------------------------
DROP PROCEDURE IF EXISTS `getTaskPublishPart`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `getTaskPublishPart`(task_id int,start int,over int)
BEGIN
	declare length int;
    set length=over-start;
	select * from taskpublish where taskpublish.task_id=task_id limit start,length;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for getUser
-- ----------------------------
DROP PROCEDURE IF EXISTS `getUser`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getUser`(username varchar(20))
BEGIN
	select * from user where user.username=username;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for login
-- ----------------------------
DROP PROCEDURE IF EXISTS `login`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `login`(username varchar(10))
BEGIN
	declare nowtime datetime DEFAULT CURRENT_TIMESTAMP;
	if exists(select user.username from user where user.username=username)then
		insert into login_record(username,login_time) values(username,nowtime);
	end if;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for rejectOrder
-- ----------------------------
DROP PROCEDURE IF EXISTS `rejectOrder`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `rejectOrder`(order_id int)
BEGIN
	declare flag int;
	if exists(select 0 from taskorder where taskorder.order_id=order_id and finish_time is not null) then
		update taskorder set is_accept=0 where taskorder.order_id=order_id;
		update user set credit=credit-10
		where user.username=(select taskorder.username from taskorder where taskorder.order_id=order_id);
        update user set credit=credit-3
		where user.username=(select taskpublish.username where taskpublish.task_id=(select taskorder.task_id from taskorder where taskorder.order_id=order_id));
		select 1 into flag;
	else
		select 0 into flag;
    end if;
	select flag;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for updatePsd
-- ----------------------------
DROP PROCEDURE IF EXISTS `updatePsd`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `updatePsd`(username varchar(10),psd varchar(255))
BEGIN
	update user set user.password=password(psd);
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for uploadPhoto
-- ----------------------------
DROP PROCEDURE IF EXISTS `uploadPhoto`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `uploadPhoto`(order_id int,image longblob)
BEGIN
	insert into photo(order_id,photo_blob) values(order_id,image);
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for uploadPortrail
-- ----------------------------
DROP PROCEDURE IF EXISTS `uploadPortrail`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `uploadPortrail`(username varchar(10),image longblob)
BEGIN
	if exists(select 1 from portrait where portrait.username=username)then
		update portrait set portrait.portrait_blob=image where portrait.username=username;
	else
		insert into portrait values(username,image);
	end if;
END
;;
DELIMITER ;

-- ----------------------------
-- Event structure for cron
-- ----------------------------
DROP EVENT IF EXISTS `cron`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `cron` ON SCHEDULE EVERY 1 MINUTE STARTS '2017-12-24 00:00:00' ON COMPLETION NOT PRESERVE ENABLE DO call addLog('test','test','test')
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `applyorder_AFTER_INSERT`;
DELIMITER ;;
CREATE TRIGGER `applyorder_AFTER_INSERT` AFTER INSERT ON `applyorder` FOR EACH ROW BEGIN
	call addLog('applyorder','insert',new.order_id);
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `applyorder_AFTER_UPDATE`;
DELIMITER ;;
CREATE TRIGGER `applyorder_AFTER_UPDATE` AFTER UPDATE ON `applyorder` FOR EACH ROW BEGIN
	call addLog('applyorder','update',new.order_id);
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `applyorder_AFTER_DELETE`;
DELIMITER ;;
CREATE TRIGGER `applyorder_AFTER_DELETE` AFTER DELETE ON `applyorder` FOR EACH ROW BEGIN
	call addLog('applyorder','delete',old.order_id);
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `city_AFTER_INSERT`;
DELIMITER ;;
CREATE TRIGGER `city_AFTER_INSERT` AFTER INSERT ON `city` FOR EACH ROW BEGIN
	call addLog('city','insert',new.city_name);
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `city_AFTER_UPDATE`;
DELIMITER ;;
CREATE TRIGGER `city_AFTER_UPDATE` AFTER UPDATE ON `city` FOR EACH ROW BEGIN
	call addLog('city','update',new.city_name);
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `city_AFTER_DELETE`;
DELIMITER ;;
CREATE TRIGGER `city_AFTER_DELETE` AFTER DELETE ON `city` FOR EACH ROW BEGIN
	call addLog('city','delete',old.city_name);
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `login_record_BEFORE_INSERT`;
DELIMITER ;;
CREATE TRIGGER `login_record_BEFORE_INSERT` BEFORE INSERT ON `login_record` FOR EACH ROW BEGIN
	declare nowdate datetime;
    SELECT CAST((CAST(SYSDATE()AS DATE) - INTERVAL 1 DAY)AS DATETIME) into nowdate;
	if not exists(select login_record.username from login_record where login_time>=nowdate and login_record.username=new.username)then
		update user set credit=credit+1 where user.username=new.username;
	end if;
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `photo_AFTER_INSERT`;
DELIMITER ;;
CREATE TRIGGER `photo_AFTER_INSERT` AFTER INSERT ON `photo` FOR EACH ROW BEGIN
	call addLog('photo','insert',new.photo_id);
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `photo_AFTER_UPDATE`;
DELIMITER ;;
CREATE TRIGGER `photo_AFTER_UPDATE` AFTER UPDATE ON `photo` FOR EACH ROW BEGIN
	call addLog('photo','update',new.photo_id);
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `photo_AFTER_DELETE`;
DELIMITER ;;
CREATE TRIGGER `photo_AFTER_DELETE` AFTER DELETE ON `photo` FOR EACH ROW BEGIN
	call addLog('photo','delete',old.photo_id);
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `portrait_AFTER_INSERT`;
DELIMITER ;;
CREATE TRIGGER `portrait_AFTER_INSERT` AFTER INSERT ON `portrait` FOR EACH ROW BEGIN
	call addLog('portrait','insert',new.username);
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `portrait_AFTER_UPDATE`;
DELIMITER ;;
CREATE TRIGGER `portrait_AFTER_UPDATE` AFTER UPDATE ON `portrait` FOR EACH ROW BEGIN
	call addLog('portrait','update',new.username);
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `portrait_AFTER_DELETE`;
DELIMITER ;;
CREATE TRIGGER `portrait_AFTER_DELETE` AFTER DELETE ON `portrait` FOR EACH ROW BEGIN
	call addLog('portrait','delete',old.username);
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `publisher_AFTER_INSERT`;
DELIMITER ;;
CREATE TRIGGER `publisher_AFTER_INSERT` AFTER INSERT ON `publisher` FOR EACH ROW BEGIN
	call addLog('publisher','insert',new.publisher_name);
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `publisher_AFTER_UPDATE`;
DELIMITER ;;
CREATE TRIGGER `publisher_AFTER_UPDATE` AFTER UPDATE ON `publisher` FOR EACH ROW BEGIN
	call addLog('publisher','update',new.publisher_name);
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `publisher_AFTER_DELETE`;
DELIMITER ;;
CREATE TRIGGER `publisher_AFTER_DELETE` AFTER DELETE ON `publisher` FOR EACH ROW BEGIN
	call addLog('publisher','delete',old.publisher_name);
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `rns_AFTER_INSERT`;
DELIMITER ;;
CREATE TRIGGER `rns_AFTER_INSERT` AFTER INSERT ON `rns` FOR EACH ROW BEGIN
	call addLog('rns','insert',new.username);
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `rns_AFTER_UPDATE`;
DELIMITER ;;
CREATE TRIGGER `rns_AFTER_UPDATE` AFTER UPDATE ON `rns` FOR EACH ROW BEGIN
	call addLog('rns','insert',new.username);
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `rns_AFTER_DELETE`;
DELIMITER ;;
CREATE TRIGGER `rns_AFTER_DELETE` AFTER DELETE ON `rns` FOR EACH ROW BEGIN
	call addLog('rns','delete',old.username);
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `taskinfo_AFTER_INSERT`;
DELIMITER ;;
CREATE TRIGGER `taskinfo_AFTER_INSERT` AFTER INSERT ON `taskinfo` FOR EACH ROW BEGIN
	update city set task_num=task_num+1 where city.city_name=new.city_name;
	call addLog('taskinfo','insert',new.task_id);
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `taskinfo_AFTER_UPDATE`;
DELIMITER ;;
CREATE TRIGGER `taskinfo_AFTER_UPDATE` AFTER UPDATE ON `taskinfo` FOR EACH ROW BEGIN
	call addLog('taskinfo','update',new.task_id);
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `taskinfo_AFTER_DELETE`;
DELIMITER ;;
CREATE TRIGGER `taskinfo_AFTER_DELETE` AFTER DELETE ON `taskinfo` FOR EACH ROW BEGIN	
	call addLog('taskinfo','delete',old.task_id);
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `taskorder_AFTER_INSERT`;
DELIMITER ;;
CREATE TRIGGER `taskorder_AFTER_INSERT` AFTER INSERT ON `taskorder` FOR EACH ROW BEGIN
	update taskpublish set applies=applies+1 where task_id=new.task_id;
    call addLog('taskorder','insert',new.task_id);
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `taskorder_AFTER_UPDATE`;
DELIMITER ;;
CREATE TRIGGER `taskorder_AFTER_UPDATE` AFTER UPDATE ON `taskorder` FOR EACH ROW BEGIN
	call addLog('taskorder','update',new.task_id);
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `taskorder_BEFORE_DELETE`;
DELIMITER ;;
CREATE TRIGGER `taskorder_BEFORE_DELETE` BEFORE DELETE ON `taskorder` FOR EACH ROW BEGIN
	delete from photo where order_id=old.order_id;
    delete from applyorder where order_id=old.order_id;
    update taskpublish set applies=applies-1 where taskpublish.task_id=(select old.task_id);
    call addLog('taskorder','delete',old.task_id);
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `taskpublish_AFTER_INSERT`;
DELIMITER ;;
CREATE TRIGGER `taskpublish_AFTER_INSERT` AFTER INSERT ON `taskpublish` FOR EACH ROW BEGIN
	update publisher set task_num=task_num+1 where publisher.publisher_name=new.publisher_name;
    call addLog('taskpublish','insert',new.task_id);
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `taskpublish_AFTER_UPDATE`;
DELIMITER ;;
CREATE TRIGGER `taskpublish_AFTER_UPDATE` AFTER UPDATE ON `taskpublish` FOR EACH ROW BEGIN
	call addLog('taskpublish','update',new.task_id);
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `taskpublish_AFTER_DELETE`;
DELIMITER ;;
CREATE TRIGGER `taskpublish_AFTER_DELETE` AFTER DELETE ON `taskpublish` FOR EACH ROW BEGIN
	call addLog('taskpublish','delete',old.task_id);
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `user_AFTER_INSERT`;
DELIMITER ;;
CREATE TRIGGER `user_AFTER_INSERT` AFTER INSERT ON `user` FOR EACH ROW BEGIN
	call addLog('user','insert',new.username);
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `user_AFTER_UPDATE`;
DELIMITER ;;
CREATE TRIGGER `user_AFTER_UPDATE` AFTER UPDATE ON `user` FOR EACH ROW BEGIN
	call addLog('user','update',new.username);
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `user_BEFORE_DELETE`;
DELIMITER ;;
CREATE TRIGGER `user_BEFORE_DELETE` BEFORE DELETE ON `user` FOR EACH ROW BEGIN
	delete from publisher where publisher_name=old.username;
    call addLog('user','delete',old.username);
END
;;
DELIMITER ;
