package org.example.mybatisflexexamples.mapper;

import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.mybatisflexexamples.entity.Account;

@Mapper
public interface AccountMapper extends BaseMapper<Account> {
}
