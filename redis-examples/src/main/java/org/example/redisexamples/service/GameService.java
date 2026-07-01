package org.example.redisexamples.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.redisexamples.dox.GameUser;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameService {
    private final RedissonClient client;

    // 获取倒序指定范围元素
    public List<GameUser> listTopSpending(String gameId, int start, int end) {
        return client.<String>getScoredSortedSet("topspenders:" + gameId, StringCodec.INSTANCE)
                // 从高到底倒序
                .entryRangeReversed(start, end)
                .stream()
                // 映射为游戏用户
                .map(entry -> GameUser.builder()
                        .id(entry.getValue())
                        .score(entry.getScore())
                        .build())
                .toList();
    }

    // 倒序获取指定氪金范围用户
    public List<GameUser> listBySpendingRange(String gameId, double startScore, double endScore) {
        return client.<String>getScoredSortedSet("topspenders:" + gameId, StringCodec.INSTANCE)
                // 倒序获取指定score范围用户
                .entryRangeReversed(startScore, true, endScore, true)
                .stream()
                .map(entry -> GameUser.builder()
                        .id(entry.getValue())
                        .score(entry.getScore())
                        .build())
                .toList();
    }



    // 指定游戏氪金榜
    // 更新指定游戏氪金榜用户数据
    public void updateUserSpending(String gameId, String uid, double spending) {
        // member按string保存
        client.getScoredSortedSet("topspenders:" + gameId, StringCodec.INSTANCE)
                .addScore(uid, spending);
    }

}
