CREATE TABLE IF NOT EXISTS `sys_user`
(
    `id`          varchar(32)  not null,
    `username`    VARCHAR(30)  NOT NULL unique,
    `password`    VARCHAR(200) NOT NULL,
    `nickname`    VARCHAR(30)  NOT NULL,
    `create_time` datetime,
    `update_time` datetime,
    `create_user` varchar(32),
    `update_user` varchar(32),
    PRIMARY KEY (`id`)) ENGINE = InnoDB
    DEFAULT CHARSET = utf8;

INSERT INTO olcp.sys_user (id, username, password, nickname, create_time, update_time, create_user, update_user) VALUES ('1', 'test', '$2a$10$BUuG0a5K7cVd6ktES5.oEOVtGQFnIg3csRLL28bBzTaGWl4rGimyO', '测试用户', null, null, null, null);
