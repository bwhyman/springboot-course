package com.example.redisexamples.service;

import com.example.redisexamples.dox.Item;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


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
    void rushBuyTest() {
        long quantity = orderService.rushBuy(Item.builder().id("01HN7JNHG93N3RSPF60MEG4WE2").build(), "152");
        log.debug("剩余数量：{}", quantity);
    }
}