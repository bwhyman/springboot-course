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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("account")
public class Account {
    @Id(keyType = KeyType.Generator, value = SnowflakeGenerator.MINI_SNOWFLAKE)
    private Long id;
    private String name;
    private double balance;
    @Column(version = true)
    private int version;
}

