-- 登录日志
create table sys_log_login
(
    id           bigint NOT NULL COMMENT 'id',
    operation    tinyint unsigned COMMENT '用户操作   0：用户登录   1：用户退出',
    status       tinyint unsigned NOT NULL COMMENT '状态  0：失败    1：成功    2：账号已锁定',
    user_agent   varchar(500) COMMENT '用户代理',
    ip           varchar(32) COMMENT '操作IP',
    creator_name varchar(50) COMMENT '用户名',
    creator      bigint COMMENT '创建者',
    create_date  datetime COMMENT '创建时间',
    primary key (id),
    key          idx_status (status),
    key          idx_create_date (create_date)
)ENGINE=InnoDB DEFAULT CHARACTER SET utf8mb4 COMMENT='登录日志';

-- 操作日志
create table sys_log_operation
(
    id             bigint NOT NULL COMMENT 'id',
    operation      varchar(50) COMMENT '用户操作',
    request_uri    varchar(200) COMMENT '请求URI',
    request_method varchar(20) COMMENT '请求方式',
    request_params text COMMENT '请求参数',
    request_time   int unsigned NOT NULL COMMENT '请求时长(毫秒)',
    user_agent     varchar(500) COMMENT '用户代理',
    ip             varchar(32) COMMENT '操作IP',
    status         tinyint unsigned NOT NULL COMMENT '状态  0：失败   1：成功',
    creator_name   varchar(50) COMMENT '用户名',
    creator        bigint COMMENT '创建者',
    create_date    datetime COMMENT '创建时间',
    primary key (id),
    key            idx_create_date (create_date)
)ENGINE=InnoDB DEFAULT CHARACTER SET utf8mb4 COMMENT='操作日志';

-- 异常日志
create table sys_log_error
(
    id             bigint NOT NULL COMMENT 'id',
    request_uri    varchar(200) COMMENT '请求URI',
    request_method varchar(20) COMMENT '请求方式',
    request_params text COMMENT '请求参数',
    user_agent     varchar(500) COMMENT '用户代理',
    ip             varchar(32) COMMENT '操作IP',
    error_info     text COMMENT '异常信息',
    creator        bigint COMMENT '创建者',
    create_date    datetime COMMENT '创建时间',
    primary key (id),
    key            idx_create_date (create_date)
)ENGINE=InnoDB DEFAULT CHARACTER SET utf8mb4 COMMENT='异常日志';
