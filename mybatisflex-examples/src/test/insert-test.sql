

insert into department (id, name) values (31082306553000, '软件工程') on conflict (id) do nothing;
insert into teacher (id, name, department)
values (31082306553001, 'BO','{"depId": 31082306553000, "name": "软件工程"}') on conflict (id) do nothing;
insert into teacher (id, name, department)
values (31082306553002, 'SUN','{"depId": 31082306553000, "name": "软件工程"}') on conflict (id) do nothing;
