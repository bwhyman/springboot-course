/*
    使用idea `ctrl+/`创建的`#`注释是错误的
    应使用类似Java的注解。`ctrl+shift+/`
*/
create table if not exists `user`
(
    id          char(19) not null primary key,
    name        varchar(45),
    create_time datetime not null default current_timestamp,
    update_time datetime not null default current_timestamp on update current_timestamp
);

create table if not exists `address`
(
    id          char(19) not null primary key,
    detail      varchar(45),
    user_id     char(19),
    create_time datetime not null default current_timestamp,
    update_time datetime not null default current_timestamp on update current_timestamp,

    index (user_id)
);
/**
  乐观锁
 */
create table if not exists `account`
(
    id      char(19) not null primary key,
    name    varchar(20),
    balance float,
    version int default 0
);
/*
  悲观锁
*/
create table if not exists `account_pess`
(
    id      char(19) not null primary key,
    name    varchar(20),
    balance float
);
/**
  动态查询
 */
create table if not exists `github_user`
(
    id        char(19) not null primary key,
    name      varchar(20),
    followers int default 0,
    stars     int default 0,
    gender    varchar(10),
    repos     int default 0
);

-- ---------------------------------
/**
  JSON
 */
create table if not exists `department`
(
    id          char(19)    not null primary key,
    name        varchar(20) not null,
    insert_time datetime    not null default current_timestamp,
    update_time datetime    not null default current_timestamp on update current_timestamp
);

/**
  将department字段json中depId属性按char(19) UTF8字符集以二进制存储建索引。因此区分大小写
  正确的声明决定是否命中索引
 */
create table if not exists `teacher`
(
    id          char(19)    not null primary key,
    name        varchar(10) not null,
    department  json comment '{"depId", "name"}',
    insert_time datetime    not null default current_timestamp,
    update_time datetime    not null default current_timestamp on update current_timestamp,

    index ((cast(department ->> '$.depId' as char(19)) collate utf8mb4_bin))
);

/**
  子项以JSON数组保存
 */
create table if not exists `process`
(
    id          char(19)    not null primary key,
    name        varchar(20) not null,
    items       json        null comment '[{"number", "name", "point", "description"}]',
    insert_time datetime    not null default current_timestamp,
    update_time datetime    not null default current_timestamp on update current_timestamp
);

/**
  每名教师在每个过程下为每名学生的评分是唯一的，因此添加唯一约束或复合索引便于查询
 */
create table if not exists `process_score`
(
    id          char(19) not null primary key,
    student_id  char(19) not null,
    process_id  char(19) not null,
    teacher_id  char(19) not null,
    detail      json     not null comment '{"teacherName", detail: [{"number", "score"}]}',
    insert_time datetime not null default current_timestamp,
    update_time datetime not null default current_timestamp on update current_timestamp,

    unique (process_id, student_id, teacher_id)
);

/**
  也可将teacherId封装到JSON对象，同样支持创建复唯一约束/复合索引
 */
/*create table if not exists `process_score_2`
(
    id          char(19) not null primary key,
    student_id  char(19) not null,
    process_id  char(19) not null,
    detail      json     not null comment '{"teacherId, "teacherName", detail: [{"number", "score"}]}',
    insert_time datetime not null default current_timestamp,
    update_time datetime not null default current_timestamp on update current_timestamp,

    unique (process_id, student_id, (cast(detail ->> '$.teacherId' as char(19)) collate utf8mb4_bin))
);*/

/**
  每过程每学生评分唯一，所有教师评分置于JSON数组。可通过JSON_TABLE()将JSON数组映射为数据表操作
 */
/*create table if not exists `process_score_3`
(
    id          char(19) not null primary key,
    student_id  char(19) not null,
    process_id  char(19) not null,
    detail      json     not null comment '[{"teacherId", "teacherName", "detail": [{"number", "score"}]}]',
    insert_time datetime not null default current_timestamp,
    update_time datetime not null default current_timestamp on update current_timestamp,

    unique (process_id, student_id)
);*/