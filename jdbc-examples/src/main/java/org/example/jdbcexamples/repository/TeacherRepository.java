package org.example.jdbcexamples.repository;

import org.example.jdbcexamples.dox.Teacher;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends CrudRepository<Teacher, String> {
    // json_set()函数，追加/更新指定JSON字段中的属性值。并返回结果
    @Modifying
    @Query("""
            update teacher t
            set t.department=json_set(t.department, '$.departmentName', :name)
            where t.department ->> '$.depId'=:depId
            """)
    int updateDepartmentName(String depId, String name);
}
