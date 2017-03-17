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
;