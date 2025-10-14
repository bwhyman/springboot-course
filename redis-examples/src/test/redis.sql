ft.dropindex st_index

ft.create st_index_v1 prefix 1 st: language 'chinese' schema  title text body text

ft.aliasadd st_index st_index_v1

hset st:1 title 'Java 24 正式版发布' content 'Java 24（Oracle JDK 24）已于 2025 年 3 月 18 日正式发布，包含 24 项功能。作为标准 Java 的短期支持版本，JDK 24 将仅获得 Oracle 六个月的Premier 级支持，而长期支持 (LTS) 版本将获得至少五年的 Premier 级支持。'

hset st:2 title 'ntpdate 同步系统时间' content '在 CentOS 7 上使用 ntpdate 同步系统时间'' body ''ntpdate 是一个传统的命令行工具，用于手动同步系统时间。虽然在更高版本的 Linux 发行版中逐渐被弃用，但在 CentOS 7 上仍然可以使用。以下是详细步骤'

ft.search  st_index 'java'

ft.search  st_index '正式版'

ft.search st_index '时间'

ft.info st_index

hgetall st:2

FT._LIST

ft.explain st_index "中国人工智能"

ft.explain st_index "同步时间"

ft.explain st_index "Java 24 正式版发布"

ft.search  st_index '布'