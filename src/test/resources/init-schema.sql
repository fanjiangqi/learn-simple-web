DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id`  int(11) NOT NULL AUTO_INCREMENT ,
  `name`  varchar(64) NOT NULL DEFAULT '' ,
  `password`  varchar(128) NOT NULL DEFAULT '' ,
  `salt`  varchar(32) NOT NULL DEFAULT '' ,
  `head_url`  varchar(256) NOT NULL DEFAULT '' ,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name` (`name`)
)
  ENGINE=InnoDB
  DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
  AUTO_INCREMENT=1
  ROW_FORMAT=COMPACT
;

DROP TABLE IF EXISTS `news`;
CREATE TABLE `news` (
`id`  int(11) UNSIGNED NOT NULL AUTO_INCREMENT ,
`title`  varchar(255) NOT NULL DEFAULT '' ,
`link`  varchar(255) NOT NULL DEFAULT '' ,
`image`  varchar(255) NOT NULL DEFAULT '' ,
`like_count`  int NOT NULL ,
`comment_count`  int NOT NULL ,
`created_date`  datetime NOT NULL ,
`user_id`  int(11) NOT NULL ,
PRIMARY KEY (`id`)
)
  ENGINE=InnoDB
  DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
  AUTO_INCREMENT=1
  ROW_FORMAT=COMPACT
;
DROP TABLE IF EXISTS `login_ticket`;
CREATE TABLE `login_ticket` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `ticket` VARCHAR(45) NOT NULL,
  `expired` DATETIME NOT NULL,
  `status` INT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `ticket_UNIQUE` (`ticket` ASC))
  ENGINE=InnoDB
  DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
  AUTO_INCREMENT=1
  ROW_FORMAT=COMPACT
;

DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id`  int NOT NULL AUTO_INCREMENT ,
  `content`  text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
  `entity_id`  int NOT NULL ,
  `entity_type`  int NOT NULL ,
  `created_date`  datetime NOT NULL ,
  `status`  int NOT NULL DEFAULT 0 ,
  `user_id`  int NOT NULL ,
  PRIMARY KEY (`id`),
  INDEX `entity_index` (`entity_id`, `entity_type`)
)
  ENGINE=InnoDB
  DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
  AUTO_INCREMENT=1
  ROW_FORMAT=COMPACT
;
CREATE TABLE `message` (
  `id`  int NOT NULL AUTO_INCREMENT ,
  `from_id`  int NULL ,
  `to_id`  int NULL ,
  `created_date`  datetime NULL ,
  `content`  text NULL ,
  `has_read`  int NULL ,
  `conversation_id`  varchar(255) NOT NULL ,
  PRIMARY KEY (`id`),
  INDEX `conversation_index` (`conversation_id`) ,
  INDEX `created_date` (`created_date`)
)
  ENGINE=InnoDB
  DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
;
