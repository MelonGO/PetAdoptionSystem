USE `pas`;

DROP TABLE IF EXISTS `adopt_info`;

CREATE TABLE `pas`.`adopt_info` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `pet_id` INT NOT NULL,
    `user_id` INT NOT NULL,
    `user_name` VARCHAR(64) NOT NULL,
    `real_name` VARCHAR(64) NOT NULL,
    `address` VARCHAR(64) NOT NULL,
    `sex` VARCHAR(64) NOT NULL,
    `state` INT NOT NULL DEFAULT 0,
    `create_time` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);