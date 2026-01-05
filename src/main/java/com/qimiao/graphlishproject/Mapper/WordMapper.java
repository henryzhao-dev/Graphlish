package com.qimiao.graphlishproject.Mapper;

import com.qimiao.graphlishproject.Entity.Word;
import org.apache.ibatis.annotations.*;

@Mapper
public interface WordMapper {

    @Select("select id, text, explain_type, created_at " +
            "from Graphlish.word where text = #{text}")
    Word queryByText(@Param("text") String text);

    @Insert("insert into Graphlish.word (text) values (#{text})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Word word);


}
