package org.example.redisexamples.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class GameServiceTest {
    @Autowired
    private RedissonClient client;
    @Autowired
    private GameService gameService;

    private final String gameid = "01KWCMKNZ6CHDWJ1025NK4WZSF";
    @Test
    void addUser() {
        // 模拟5个玩家及总花费
        // member按string保存
        RScoredSortedSet<String> zset = client.getScoredSortedSet("topspenders:" + gameid, StringCodec.INSTANCE);
        zset.add(12_000, "01KWCMKNZ8ZM080J77K7WS8MNB");
        zset.add(880_000, "01KWCMKNZ8SFMXT8RWHSDYBCZH");
        zset.add(26_200, "01KWCMQVD1Z3MZ5E72ZDDCFFYX");
        zset.add(18_000, "01KWCMQVD6R1QC52SXM51646VW");
        zset.add(90_000, "01KWCMQVD8D0RNM4AH24MT565H");
    }

    @Test
    void listTopSpending() {
        // 氪金前三名
        gameService.listTopSpending(gameid, 0, 2)
                .forEach(IO::println);
    }

    @Test
    void listBySpendingRange() {
        // 倒序获取score范围氪金用户
        gameService.listBySpendingRange(gameid, 10_000, 20_000)
                .forEach(IO::println);
    }
}