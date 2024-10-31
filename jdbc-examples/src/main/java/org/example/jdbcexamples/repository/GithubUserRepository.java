package org.example.jdbcexamples.repository;

import org.example.jdbcexamples.dox.GithubUser;
import org.example.jdbcexamples.dto.GithubOptionType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface GithubUserRepository extends CrudRepository<GithubUser, String> {
    // 注入原生JdbcTemplate组件手动实现复杂查询
    // 组件为接口，无法通过构造函数注入其他组件
    default List<GithubUser> findByDynamic(JdbcTemplate jdbcTemplate, GithubUser user) {
        // `1=1` 是为了在参数全部为空时，`where`子句仍然成立
        var sql = "select * from github_user t1 where 1=1";
        List<Object> args = new ArrayList<>();
        if (user.getFollowers() != null) {
            sql += " and t1.followers >= ?";
            args.add(user.getFollowers());
        }
        if (user.getStars() != null) {
            sql += " and t1.stars >= ?";
            args.add(user.getStars());
        }
        if (user.getGender() != null) {
            sql += " and t1.gender = ?";
            args.add(user.getGender());
        }
        // `queryForList()`方法返回的是单列字段
        // RowMapper，手动映射
        // 返回单值，可以用queryObject()方法
        return jdbcTemplate.query(sql, mapper, args.toArray());
    }
    default List<GithubUser> findByDynamic(JdbcTemplate jdbcTemplate, List<GithubOptionType> options) {
        var sql = new StringBuilder("select * from github_user t1 where 1=1");
        List<Object> args = new ArrayList<>();
        for (GithubOptionType option : options) {
            if (option.propName().equals("followers")) {
                String operator = option.operator();
                Object value = option.value();
                sql.append(" and t1.followers %s ?".formatted(operator));
                args.add(value);
            }
            if (option.propName().equals("stars")) {
                String operator = option.operator();
                Object value = option.value();
                sql.append(" and t1.stars %s ?".formatted(operator));
                args.add(value);
            }
            if (option.propName().equals("gender")) {
                String operator = option.operator();
                Object value = option.value();
                sql.append(" and t1.gender %s ?".formatted(operator));
                args.add(value);
            }
        }
        return jdbcTemplate.query(sql.toString(), mapper, args.toArray());
    }
    // 避免每次创建mapper对象
    RowMapper<GithubUser> mapper = (rs, rowNum) -> GithubUser.builder()
            .id(rs.getString("id"))
            .name(rs.getString("name"))
            .stars(rs.getInt("stars"))
            .followers(rs.getInt("followers"))
            .gender(rs.getString("gender"))
            .repos(rs.getInt("repos"))
            .build();
    // 当字段与属性名称全部相同时，可使用spring提供的封装工具简化开发
    RowMapper<GithubUser> m = BeanPropertyRowMapper.newInstance(GithubUser.class);

}
