-- region
create table if not exists region
(
    id   bigint primary key,
    name varchar(20) not null,
    path ltree

);
create index on region using gist (path);

-- 插入测试数据
INSERT INTO region (id, name, path) VALUES (31082306553920, '中国', '31082306553920') on conflict (id) do nothing;
INSERT INTO region (id, name, path) VALUES (31082306553921, '黑龙江省', '31082306553920.31082306553921') on conflict (id) do nothing;
INSERT INTO region (id, name, path) VALUES (31082306553922, '哈尔滨市', '31082306553920.31082306553921.31082306553922') on conflict (id) do nothing;
INSERT INTO region (id, name, path) VALUES (31082306553923, '和兴路街道', '31082306553920.31082306553921.31082306553922.31082306553923') on conflict (id) do nothing;
INSERT INTO region (id, name, path) VALUES (31082306553924, '征仪路', '31082306553920.31082306553921.31082306553922.31082306553923.31082306553924') on conflict (id) do nothing;
INSERT INTO region (id, name, path) VALUES (31082306553925, '测绘路', '31082306553920.31082306553921.31082306553922.31082306553923.31082306553925') on conflict (id) do nothing;
INSERT INTO region (id, name, path) VALUES (31082306553926, '辽宁省', '31082306553920.31082306553926') on conflict (id) do nothing;
INSERT INTO region (id, name, path) VALUES (31082306553927, '沈阳市', '31082306553920.31082306553926.31082306553927') on conflict (id) do nothing;

--------------------------------------------------
-- <@，查询指定节点下全部节点
explain analyze
select * from region r
where r.path <@ '31082306553920.31082306553921.31082306553922';

explain analyze
select * from region r
where r.path <@ '31082306553920.31082306553921.31082306553922.31082306553923';

-- @>，查询指定节点上全部节点
explain analyze
select * from region r
where r.path @> '31082306553920.31082306553921.31082306553922.31082306553923';

explain analyze
select jsonb_agg(jsonb_build_object('id', r.id, 'name', r.name) order by r.id) as path
from region r
where r.path @> '31082306553920.31082306553921.31082306553922.31082306553923';