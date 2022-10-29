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
    app_id      VARCHAR(32)  NOT NULL,
    create_time datetime,
    update_time datetime,
    create_user varchar(32),
    update_user varchar(32),
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

CREATE TABLE IF NOT EXISTS sys_list_field
(
    id          varchar(32)  not null,
    title       VARCHAR(200) NOT NULL,
    list_id     VARCHAR(32)  NOT NULL,
    field_id    VARCHAR(32)  NOT NULL,
    app_id      VARCHAR(32)  NOT NULL,
    create_time datetime,
    update_time datetime,
    create_user varchar(32),
    update_user varchar(32)
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
    engine_type       varchar(30),
    default_charset   varchar(30),
    create_time       datetime,
    update_time       datetime,
    create_user       varchar(32),
    update_user       varchar(32),
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS sys_table_field
(
    id                 varchar(32)  not null,
    name               VARCHAR(30)  NOT NULL unique,
    title              VARCHAR(200) NOT NULL,
    table_id           varchar(32)  not null,
    app_id             VARCHAR(32)  NOT NULL,
    type               VARCHAR(30)  NOT NULL,
    max_length         int,
    max_precision      int     default 0,
    default_value      varchar(200),
    short_description  varchar(200),
    required           boolean,
    reference_table_id varchar(200),
    system_field       boolean,
    sn                 integer default 1,
    create_time        datetime,
    update_time        datetime,
    create_user        varchar(32),
    update_user        varchar(32),
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS sys_table_relationship
(
    id          varchar(32)  not null,
    name        VARCHAR(30)  NOT NULL unique,
    title       VARCHAR(200) NOT NULL,
    type        VARCHAR(30)  NOT NULL,
    table_id    varchar(32)  not null,
    field_id    varchar(32)  not null,
    parent      varchar(32)  not null,
    create_time datetime,
    update_time datetime,
    create_user varchar(32),
    update_user varchar(32),
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS sys_table_index
(
    id               varchar(32) not null,
    table_id         varchar(32) not null,
    table_related_id varchar(32) not null,
    table_field_id   varchar(32) not null,
    type             varchar(30) not null,
    `read_only`      boolean,
    create_time      datetime,
    update_time      datetime,
    create_user      varchar(32),
    update_user      varchar(32),
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
