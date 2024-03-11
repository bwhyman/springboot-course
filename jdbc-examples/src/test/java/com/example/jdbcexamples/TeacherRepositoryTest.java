package com.example.jdbcexamples;

import com.example.jdbcexamples.repository.TeacherRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
@Slf4j
class TeacherRepositoryTest {
    @Autowired
    private TeacherRepository teacherRepository;

    @Test
    void updateDepartmentName() {
        var depId = "1";
        var newName = "控制工程";
        teacherRepository.updateDepartmentName(depId, newName);
    }
}