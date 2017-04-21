USE `pas`;

DROP TABLE IF EXISTS `message`;

CREATE TABLE `pas`.`message` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(64) NOT NULL,
  `targetUsername` VARCHAR(64) NOT NULL,
  `content` VARCHAR(300) NOT NULL,
  `readed`	TINYINT(1) NOT NULL DEFAULT 0,
  `sendTime` VARCHAR(100) NOT NULL,
  
  `create_time` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`));