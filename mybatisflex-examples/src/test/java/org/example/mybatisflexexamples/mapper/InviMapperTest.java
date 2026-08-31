package org.example.mybatisflexexamples.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class InviMapperTest {
    @Autowired
    private InviMapper inviMapper;

    @Test
    void list() {
        inviMapper.list(1001)
                .forEach(IO::println);
    }

    @Test
    void listByDepId() {
        inviMapper.listByDepId(2001)
                .forEach(IO::println);
    }
}