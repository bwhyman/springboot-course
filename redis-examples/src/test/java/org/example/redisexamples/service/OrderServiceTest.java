package org.example.redisexamples.service;

import lombok.extern.slf4j.Slf4j;
import org.example.redisexamples.dox.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


@SpringBootTest
@Slf4j
class OrderServiceTest {
    @Autowired
    private OrderService orderService;

    @Test
    void addItemsTest() {
        List<Item> items = List.of(
                Item.builder().id("01HN7JNHG93N3RSPF60MEG4WE2").total(50).build(),
                Item.builder().id("01HN7JNHG93N3RSPF60MEG4WE3").total(30).build()
        );
        orderService.addItems(items);
    }

    @Test
    void rushBuyTest() throws InterruptedException {
        // 模拟抢购的商品，抢购用户
        Item item = Item.builder().id("01HN7JNHG93N3RSPF60MEG4WE2").build();
        final int AMOUNT = 200;
        CountDownLatch latch = new CountDownLatch(AMOUNT);
        var random = new Random();
        for (int i = 0; i < AMOUNT; i++) {
            Thread.startVirtualThread(() -> {
                long quantity = orderService.rushBuy(item, String.valueOf(random.nextInt(100)));
                log.debug("剩余数量：{}", quantity);
                latch.countDown();
            });
        }
        latch.await();
        // 让消息监听器有时间，读取消息队列中的消息
        TimeUnit.SECONDS.sleep(10);

    }
}