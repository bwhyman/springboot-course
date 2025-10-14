package org.example.redisexamples;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.redisson.api.search.index.FieldIndex;
import org.redisson.api.search.index.IndexOptions;
import org.redisson.api.search.query.QueryOptions;
import org.redisson.api.search.query.SearchResult;
import org.redisson.client.codec.StringCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class RedisSearchTest {
    @Autowired
    private RedissonClient redissonClient;
    final String index = "st_index";

    // 创建索引，必须是支持结构化数据的文档模型，hash/JSON；string等类型不支持。
    @Test
    void initIndex() {
        var search = redissonClient.getSearch(StringCodec.INSTANCE);
        if(search.getIndexes().contains(index)) {
            return;
        }
        // ft.create st_index_v1 prefix 1 st: language 'chinese' schema  title text body text
        // 等效命令，对指定前缀键添加索引
        IndexOptions op = IndexOptions.defaults()
                .prefix("st:")
                .language("chinese");
        // 建索引的字段与类型
        search.createIndex(index, op, FieldIndex.text("title"), FieldIndex.text("content"));
    }

    // 初始化测试数据
    @Test
    void initData() {
        var val1 = redissonClient.getMap("st:1001", StringCodec.INSTANCE);
        val1.put("title","Java 24 正式版发布");
        val1.put("content","Java 24（Oracle JDK 24）已于 2025 年 3 月 18 日正式发布，包含 24 项功能。作为标准 Java 的短期支持版本，JDK 24 将仅获得 Oracle 六个月的Premier 级支持，而长期支持 (LTS) 版本将获得至少五年的 Premier 级支持。");

        var val2 = redissonClient.getMap("st:2002", StringCodec.INSTANCE);
        val2.put("title", "ntpdate 同步系统时间");
        val2.put("content", "在 CentOS 7 上使用 ntpdate 同步系统时间' body 'ntpdate 是一个传统的命令行工具，用于手动同步系统时间。虽然在更高版本的 Linux 发行版中逐渐被弃用，但在 CentOS 7 上仍然可以使用。以下是详细步骤");
    }


    @Test
    void search() {
        // ft.search st_index '正式版'
        SearchResult search = redissonClient
                .getSearch(StringCodec.INSTANCE)
                .search(index, "正式版", QueryOptions.defaults());

        search.getDocuments()
                .forEach(document -> {
                    document.getAttributes()
                            .forEach((k, v) -> {
                                log.debug(k);
                                log.debug(v.toString());
                            });
                });
    }

    // 数据中并没有完全匹配`同步时间`的字符串
    // 检索时会将关键词分词，再检索
    @Test
    void search2() {
        SearchResult search = redissonClient
                .getSearch(StringCodec.INSTANCE)
                .search(index, "同步时间", QueryOptions.defaults());

        search.getDocuments()
                .forEach(document -> {
                    document.getAttributes()
                            .forEach((k, v) -> {
                                log.debug(k);
                                log.debug(v.toString());
                            });
                });
    }

    // 中文分词`发布`，没有`布`，因此无法命中
    // ft.explain st_index "Java 24 正式版发布"
    @Test
    void search3() {
        SearchResult search = redissonClient
                .getSearch(StringCodec.INSTANCE)
                .search(index, "布", QueryOptions.defaults());

        search.getDocuments()
                .forEach(document -> {
                    document.getAttributes()
                            .forEach((k, v) -> {
                                log.debug(k);
                                log.debug(v.toString());
                            });
                });
    }
}
