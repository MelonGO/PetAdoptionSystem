USE `pas`;

DROP TABLE IF EXISTS `adoption`;

CREATE TABLE `pas`.`adoption` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `receiving_info_id` INT NOT NULL,
    `pet_id` INT NOT NULL,
    `user_id` INT NOT NULL,
    `state` INT NOT NULL DEFAULT 0,
    `transport_way` VARCHAR(64) NOT NULL, 
    `create_time` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);