package com.codetogether.am.community.mapper;

import com.codetogether.am.community.Model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question" +
            "(title, description, gmt_create, gmt_modified,creator,tag) " +
            "values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    public void create(Question question);

    @Select("select * from question limit #{limit} offset #{offset}")
    List<Question> list(@Param(value = "offset") Integer offset, @Param(value = "limit")Integer limit);

    @Select("select count(1) from question")
    Integer count();
}
