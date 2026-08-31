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
