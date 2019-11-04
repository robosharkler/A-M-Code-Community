package com.codetogether.am.community.mapper;

import com.codetogether.am.community.Model.Notice;
import com.codetogether.am.community.Model.Reply;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface NoticeMapper {
    @Insert("insert into notice (question_id,account_id,visited) values (#{questionId},#{accountId},#{visited})")
    void insert(Notice notice);

    @Select("select * from notice where account_id = #{accountId} and visited = false")
    List<Notice> findByAccountId(@Param("accountId") String accountId);

    @Update("update notice set visited=#{visited} where id=#{id}")
    public int update(Notice notice);

    @Select("select * from notice where id = #{id}")
    Notice findById(@Param("id") Integer id);

    @Select("select count(1) from notice where account_id = #{accountId} and visited = false")
    Integer countByAccountId(@Param("accountId") String accountId);
}
