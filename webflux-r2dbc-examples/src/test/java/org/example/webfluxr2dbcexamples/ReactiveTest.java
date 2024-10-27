package org.example.webfluxr2dbcexamples;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class ReactiveTest {
    @Test
    public void FluxTest1() {
        // 创建一个包含多个元素的发布者
        Flux<Integer> flux1 = Flux.just(1, 2, 3, 4);
        // 当此发布者被订阅时，顺序输出数据流中元素
        flux1.subscribe(System.out::println);
        System.out.println("-------------");
        // 基于Flux发布者，将订阅元素转为聚合为List集合的Mono发布者
        Mono<List<Integer>> listMono = flux1.collectList();
        // 订阅Mono发布者，`推送`集合元素，响应式获取并操作
        listMono.subscribe(ints -> {
            ints.forEach(System.out::println);
        });
        listMono.then();
    }
}
