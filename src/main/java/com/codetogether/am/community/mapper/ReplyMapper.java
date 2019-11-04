package com.codetogether.am.community.mapper;

import com.codetogether.am.community.Model.Reply;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ReplyMapper {
    @Insert("insert into reply (question_id,account_id,gmt_create,description) values (#{questionId},#{accountId},#{gmtCreate},#{description})")
    void insert(Reply reply);
    @Select("select * from reply where question_id = #{questionId}")
    List<Reply> findByQuestionId(@Param("questionId") Integer questionId);
    @Select("select * from reply where id = #{id}")
    Reply findById(@Param("id") Integer id);
}
