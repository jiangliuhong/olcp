CREATE TABLE IF NOT EXISTS sys_app
(
    id          varchar(32)  not null,
    name        VARCHAR(30)  NOT NULL unique,
    title       VARCHAR(200) NOT NULL,
    create_time datetime,
    update_time datetime,
    create_user varchar(32),
    update_user varchar(32),
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS sys_code_type
(
    id          varchar(32)  not null,
    name        VARCHAR(30)  NOT NULL unique,
    title       VARCHAR(200) NOT NULL,
    app_id      VARCHAR(32)  NOT NULL,
    active      boolean,
    create_time datetime,
    update_time datetime,
    create_user varchar(32),
    update_user varchar(32),
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS sys_code_value
(
    id          varchar(32)  not null,
    name        VARCHAR(30)  NOT NULL unique,
    title       VARCHAR(200) NOT NULL,
    active      boolean,
    create_time datetime,
    update_time datetime,
    create_user varchar(32),
    update_user varchar(32),
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS sys_field
(
    id                 varchar(32)  not null,
    name               VARCHAR(30)  NOT NULL unique,
    title              VARCHAR(200) NOT NULL,
    table_id           varchar(32)  not null,
    max_length         int          not null,
    max_precision      int default 0,
    default_value      varchar(200),
    short_description  varchar(200),
    required           boolean,
    reference_table_id varchar(200),
    create_time        datetime,
    update_time        datetime,
    create_user        varchar(32),
    update_user        varchar(32),
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS sys_form
(
    id          varchar(32)  not null,
    name        VARCHAR(30)  NOT NULL unique,
    title       VARCHAR(200) NOT NULL,
    type        VARCHAR(30)  NOT NULL,
    app_id      VARCHAR(32)  NOT NULL,
    create_time datetime,
    update_time datetime,
    create_user varchar(32),
    update_user varchar(32),
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS sys_list
(
    id          varchar(32)  not null,
    name        VARCHAR(30)  NOT NULL unique,
    title       VARCHAR(200) NOT NULL,
    app_id      VARCHAR(32)  NOT NULL,
    table_id    varchar(32)  not null,
    create_time datetime,
    update_time datetime,
    create_user varchar(32),
    update_user varchar(32),
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS sys_table
(
    id                varchar(32)  not null,
    name              VARCHAR(30)  NOT NULL unique,
    title             VARCHAR(200) NOT NULL,
    app_id            VARCHAR(32)  NOT NULL,
    short_description varchar(200),
    parent            varchar(32),
    virtual_table     BOOLEAN,
    primary_key       varchar(32),
    engine_type       varchar(30),
    default_charset   varchar(30),
    create_time       datetime,
    update_time       datetime,
    create_user       varchar(32),
    update_user       varchar(32),
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS sys_user
(
    id          varchar(32)  not null,
    username    VARCHAR(30)  NOT NULL unique,
    password    VARCHAR(200) NOT NULL,
    nickname    VARCHAR(30)  NOT NULL,
    app_id      VARCHAR(32)  NOT NULL,
    active      boolean,
    email       varchar(100),
    phone       varchar(100),
    create_time datetime,
    update_time datetime,
    create_user varchar(32),
    update_user varchar(32),
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
