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
            "(title, description, gmt_create, gmt_modified,creator,tag, creator_name) " +
            "values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag},#{creatorName})")
    public void create(Question question);

    @Select("select * from question limit #{limit} offset #{offset}")
    List<Question> list(@Param(value = "offset") Integer offset, @Param(value = "limit")Integer limit);

    @Select("select count(1) from question")
    Integer count();

    @Select("select * from question where creator = #{userId} limit #{limit} offset #{offset}")
    List<Question> listByUserId(@Param("userId") Integer userId, @Param(value = "offset") Integer offset, @Param(value = "limit")Integer limit);

    @Select("select count(1) from question where creator = #{userId}")
    Integer countByUserId(@Param("userId") Integer userId);

    @Select("select * from question where id = #{id}")
    Question getById(@Param("id") Integer id);

    @Select("select * from question where creator_name = #{userName}")
    Question getByUserName(@Param("userName") String userName);

    @Select("select count(1) from question where creator_name = #{userName}")
    Integer countByUserName(@Param("userName") String userName);

    @Select("select * from question where creator_name = #{userName} limit #{limit} offset #{offset}")
    List<Question> listByUserName(@Param("userName") String userName, @Param(value = "offset") Integer offset, @Param(value = "limit")Integer limit);

    @Select("select count(1) from question where title like '%${search}%' ")
    Integer countBySearch(@Param("search") String search);

    @Select("select * from question where title like '%${search}%' limit #{limit} offset #{offset}")
    List<Question> listBySearch(@Param("search")String search, @Param(value = "offset") Integer offset, @Param(value = "limit")Integer limit);
}
