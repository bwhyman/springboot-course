package org.example.mybatisflexexamples.mapper;

import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.query.RawQueryColumn;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.mybatisflexexamples.entity.Invi;

import java.util.List;

@Mapper
public interface InviMapper extends BaseMapper<Invi> {

    default List<Invi> list(long depid) {
        return QueryChain.of(this)
                .select("*")
                .select(new RawQueryColumn("(allocation ->> ?)::int as count", String.valueOf(depid)))
                .where("allocation ?? ?", String.valueOf(depid))
                .list();
    }

    @Select("""
            select * from invi t1
            WHERE jsonb_path_query_array(t1.executors, '$[*].id') @> jsonb_build_array(#{depid}::bigint);
            """)
    List<Invi> listByDepId(long depid);
}
