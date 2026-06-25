select *
from user u join address a
                 on u.id=a.user_id
where u.id=29790033938496;




select * from teacher t where t.id=29790033938497;

explain
select * from teacher t
where t.department ->> '$.depId'='29790033938496';

explain
select * from process_score_3 ps
                  join json_table(ps.detail,
                                  '$[*]'
                                  columns (
                                      `id` for ordinality ,
                                      `tid` bigint unsigned path '$.teacherId'
                                      )
                       ) as jt
where ps.process_id=1 and jt.tid=1;


select *
from user u join address a on u.id=a.user_id
where u.id=29790033938496;


select json_object('id', u.id, 'name', u.name) as user,
       json_arrayagg(json_object('id', a.id, 'detail', a.detail)) as addresses
from user u join address a on u.id=a.user_id
where u.id=29790033938496;

explain
select * from address2 a join user u
on u.id=a.user ->> '$.id'
where a.id=29958394622017;

explain
select * from address2 a
where a.user ->> '$.id'=29790033938496;


explain
select * from address2 a join user u
on u.id=a.user ->> '$.id'
where u.id=29790033938496;

explain
select * from address a join user u
on u.id=a.user_id
where u.id=29790033938496;
