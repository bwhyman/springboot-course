/*
    使用idea `ctrl+/`创建的`#`注释是错误的
    应使用类似Java的注解。`ctrl+shift+/`
*/
create table if not exists user_x
(
    id          bigint primary key,
    name        varchar(45),
    create_time timestamp not null default current_timestamp,
    update_time timestamp not null default current_timestamp
);

create table if not exists address
(
    id          bigint primary key,
    detail      varchar(45),
    user_id     bigint    not null,
    create_time timestamp not null default current_timestamp,
    update_time timestamp not null default current_timestamp
);
create index if not exists address_user_id_idx on address (user_id);
----------------------------
-- JSON
create table if not exists department
(
    id   bigint primary key,
    name varchar(20) not null
);

/**
  正确的声明决定查询json属性是否命中索引
 */
create table if not exists teacher
(
    id         bigint primary key,
    name       varchar(10) not null,
    department jsonb
);
create index if not exists teacher_department_idx on teacher ((("department" ->> 'depId')::bigint));

/**
  乐观锁
 */
/*create table if not exists account
(
    id      bigint primary key,
    name    varchar(20),
    balance float,
    version int default 0
);*/

/*
  悲观锁
*/
/*create table if not exists account_pess
(
    id      bigint primary key,
    name    varchar(20),
    balance float
);
*/
-- 子项以JSON数组保存
-- [{"number", "name", "point", "description"}]
/*create table if not exists process
(
    id    bigint primary key,
    name  varchar(20) not null,
    items jsonb       null
);*/

-- 每名教师在每个过程下为每名学生的评分是唯一的，因此添加唯一约束或复合索引便于查询
-- {"teacherName", detail: [{"number", "score"}]}
/*create table if not exists process_score
(
    id         bigint primary key,
    student_id bigint not null,
    process_id bigint not null,
    teacher_id bigint not null,
    detail     jsonb  not null,

    unique (process_id, student_id, teacher_id)
);*/


/** 也可将teacherId封装到JSON对象，同样支持创建复唯一约束/复合索引 */
/** {"teacherId", "teacherName", "detail": [{"number", "score"}]} */
/*create table if not exists process_score_2
(
    id         bigint primary key,
    student_id bigint not null,
    process_id bigint not null,
    detail     jsonb  not null,

  unique (process_id, student_id, ((detail ->> 'teacherId')::bigint))
);*/

