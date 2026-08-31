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