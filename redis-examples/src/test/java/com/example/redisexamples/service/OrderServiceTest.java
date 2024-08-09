package com.example.redisexamples.service;

import com.example.redisexamples.dox.Item;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.CountDownLatch;


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
        for (int i = 0; i < AMOUNT; i++) {
            int x = i;
            Thread.ofVirtual().start(() -> {
                long quantity = orderService.rushBuy(item, String.valueOf(x));
                log.debug("剩余数量：{}", quantity);
                latch.countDown();
            });
        }
        latch.await();
    }
}