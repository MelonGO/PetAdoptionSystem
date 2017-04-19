USE `pas`;

DROP TABLE IF EXISTS `receiving_info`;

CREATE TABLE `pas`.`receiving_info` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `user_id` INT NOT NULL,
    `real_name` VARCHAR(64) NOT NULL,
    `address` VARCHAR(64) NOT NULL,
    `phone` VARCHAR(64) NOT NULL,
    `create_time` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);