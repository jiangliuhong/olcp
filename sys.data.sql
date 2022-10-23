# 系统应用
INSERT INTO sys_app (id, name, title)
VALUES ('system_app', 'system', '系统应用');
# 测试用户
INSERT INTO sys_user (id, username, password, nickname, app_id)
VALUES ('1', 'test', '$2a$10$BUuG0a5K7cVd6ktES5.oEOVtGQFnIg3csRLL28bBzTaGWl4rGimyO', '测试用户', 'system_app');
