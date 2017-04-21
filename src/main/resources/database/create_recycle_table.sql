USE `pas`;

DROP TABLE IF EXISTS `recycle`;

CREATE TABLE `pas`.`recycle` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `pet_id` INT NOT NULL,
    `user_id` INT NOT NULL,
    `state` INT NOT NULL DEFAULT 0,
    `sponsorship` INT NOT NULL DEFAULT 0,
    `money` DOUBLE NOT NULL,
    `reason` VARCHAR(300) NOT NULL,
    `address` VARCHAR(100) NOT NULL,
    `phone` VARCHAR(45) NOT NULL,
    `create_time` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);