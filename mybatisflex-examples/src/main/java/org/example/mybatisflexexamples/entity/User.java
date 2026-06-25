package org.example.mybatisflexexamples.entity;


import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.mybatisflexexamples.component.SnowflakeGenerator;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("user_x")
public class User {
    @Id(keyType = KeyType.Generator, value = SnowflakeGenerator.MINI_SNOWFLAKE)
    private Long id;
    private String name;
    @Column(onInsertValue = "current_timestamp")
    private LocalDateTime createTime;
    @Column(onInsertValue = "current_timestamp", onUpdateValue = "current_timestamp")
    private LocalDateTime updateTime;
}
