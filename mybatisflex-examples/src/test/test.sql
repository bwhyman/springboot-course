--
select * from teacher t where t.id=29790033938497;
--
SET enable_seqscan = off;
explain
select * from address a where a.user_id=31078024548416;
--
select *
from user_x u join address a on u.id=a.user_id
where u.id=29790033938496;
--
explain
select *
from user_x u join address a on u.id=a.user_id
where u.id=31078024548416;
--
select * from address a, user_x u
where a.user_id=u.id and u.id=29790033938497;
--
explain
select * from teacher t
where (t.department ->> 'depId')::bigint=31082306553000;
--
select jsonb_build_object('id', u.id, 'name', u.name) as user_info, a.detail
from address a join user_x u
on a.user_id=u.id
where a.id=31084804403264;

-- 将多条记录以uid分组
-- 将分组中user信息封装为json对象；将地址聚合封装为json数组
select jsonb_build_object('id', u.id, 'name', u.name) as user_info,
jsonb_agg(jsonb_build_object('id', a.id, 'detail', a.detail)) as addresses
from user_x u join address a on u.id=a.user_id
where u.id=31078024548416
group by u.id;