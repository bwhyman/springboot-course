-- allocation: {"1001": 2, "1002": 1}
-- importer: {userId, userName, updateTime}
-- dispatchers: [{depId, departmentName, amount, userId, userName, updateTime}]
-- time: {starttime, endtime}
-- course: {courseName, teacherName, location, clazz}
create table if not exists invigilation
(
    id          bigint primary key,
    coll_id     bigint not null,
    importer    jsonb  null,
    status      smallint default 0,
    allocation  jsonb  null,
    dispatchers jsonb  null,
    date        date   not null,
    time        jsonb  not null,
    course      jsonb  not null,
    amount      smallint default 0 check ( amount > 0 )

);
create index if not exists allocation_idx on invigilation using gin (allocation);
create index if not exists dispatchers_idx on invigilation using gin ((jsonb_path_query_array(dispatchers, '$[*].depId')));

create table if not exists invi_detail
(
    id        bigint primary key,
    invi_id   bigint  not null,
    dep_id    bigint  not null,
    user_id   bigint  not null,
    user_name varchar not null,
    ding_id   varchar null,
    union_id  varchar null,
    calendar  varchar null,
    event_id  varchar null
);
--------------------
create table if not exists invigilation
(
    id       bigint primary key,
    coll_id  bigint not null,
    importer jsonb  null,
    status   smallint default 0,
    date     date   not null,
    time     jsonb  not null,
    course   jsonb  not null,
    amount   smallint default 0 check ( amount > 0 )
);
create table if not exists invi_dispatch
(
    id         bigint primary key,
    invi_id    bigint not null,
    dep_id     bigint not null,
    amount     smallint default 0 check ( amount > 0 ),
    dispatcher jsonb  null
);
create table if not exists invi_detail
(
    id        bigint primary key,
    invi_id   bigint  not null,
    dep_id    bigint  not null,
    user_id   bigint  not null,
    user_name varchar not null,
    ding_id   varchar null,
    union_id  varchar null,
    calendar  varchar null,
    event_id  varchar null
);

