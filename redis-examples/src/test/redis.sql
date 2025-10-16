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