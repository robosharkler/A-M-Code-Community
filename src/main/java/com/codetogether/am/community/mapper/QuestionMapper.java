package com.codetogether.am.community.mapper;

import com.codetogether.am.community.Model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question" +
            "(title, description, gmt_create, gmt_modified,creator,tag) " +
            "values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    public void create(Question question);
}
