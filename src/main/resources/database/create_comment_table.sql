USE `schema_name`;

DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `petID` int(11) NOT NULL,
  `username` varchar(64) NOT NULL,
  `content` varchar(64) NOT NULL,
  `fatherCommentID` int(11) NOT NULL,
  `replyCommentID` int(11) NOT NULL,
  `support` int(11) NOT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;