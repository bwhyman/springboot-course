create table if not exists `user`
(
    id char(19) not null primary key ,
    name varchar(45),
    create_time datetime not null default current_timestamp,
    update_time datetime not null default current_timestamp on update current_timestamp
);

create table if not exists `address`
(
    id char(19) not null primary key ,
    detail varchar(45),
    user_id char(19),
    create_time datetime not null default current_timestamp,
    update_time datetime not null default current_timestamp on update current_timestamp,

    index (user_id)
);

create table if not exists `account`
(
    id char(19) not null primary key ,
    name varchar(20),
    balance float,
    version int default 0
);

create table if not exists `github_user`
(
    id char(19) not null primary key ,
    name varchar(20),
    followers int default 0,
    stars int default 0,
    gender varchar(10),
    repos int default 0
)