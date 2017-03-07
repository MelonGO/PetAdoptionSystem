USE `pas`;

DROP TABLE IF EXISTS `pet`;

CREATE TABLE `pas`.`pet` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(64) NOT NULL,
  `age` INT NOT NULL,
  `type` VARCHAR(64) NOT NULL,
  `create_time` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`));
  
insert into pet (name, age, type) values ("小明", 10, "牧羊犬");
insert into pet (name, age, type) values ("小红", 4, "波斯猫");
insert into pet (name, age, type) values ("小张", 2, "藏獒");