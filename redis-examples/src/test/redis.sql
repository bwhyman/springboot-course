-- zset添加测试数据
zadd courses:java 95 users:100
zadd courses:java 80 users:101
zadd courses:java 75 users:102
zadd courses:java 85 users:103
zadd courses:java 98 users:104

-- 倒序((由高到低))获取score最高前三名，以及具体score
zrange courses:java 0 2 rev withscores

-- 倒序获取score范围元素及score
-- 倒序时，startscore > endscore
zrange courses:java 100 90 byscore rev withscores

-- 获取指定member倒序(由高到低)排名
zrevrank courses:java users:100

-- 获取score在指定区间的元素个数
zcount courses:java 90 100
----------------
-- 指定key，自增+1，上限2，5秒过期
increx expires:agentids:6562 byint 1 ubound 2 ex 5
-----------
ft.dropindex st_index

ft.create st_index_v1 prefix 1 st: language 'chinese' schema  title text content text

ft.aliasadd st_index st_index_v1

hset st:1001 title 'Java 24 正式版发布' content 'Java 24（Oracle JDK 24）已于 2025 年 3 月 18 日正式发布，包含 24 项功能。'

hset st:2002 title 'ntpdate 同步系统时间' content '在 CentOS 7 上 ntpdate 是一个传统的命令行工具，用于手动同步系统时间。'

hset st:3003 title 'Redis主从同步机制' content 'Redis 读写分离并不是完全的数据实时同步，因为从节点的数据可能会有一定的延迟。'

ft.search  st_index 'java'

ft.search  st_index '正式版'

ft.search st_index '同步时间'

ft.search st_index '同步'
# OR
ft.search st_index '同步|时间'
# 检索指定属性
ft.search st_index '(@title:同步|@title:时间)'
# 索引信息
ft.info st_index

FT._LIST

ft.explain st_index "中国人工智能"

ft.explain st_index "同步时间"

ft.explain st_index "Java 24 正式版发布"

ft.search  st_index '布'