package com.qimiao.graphlishproject.Mapper;

import com.qimiao.graphlishproject.Entity.WordExplanation;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface WordExplanationMapper {

    @Select("select id, word_id, explanation, example, created_at, updated_at " +
            "from Graphlish.word_explanation " +
            "where word_id = #{wordId}")
    WordExplanation queryByWordId(@Param("wordId") Long wordId);

    @Insert("insert into Graphlish.word_explanation " +
            "(word_id, explanation, example)" +
            "values " +
            "(#{wordId}, #{explanation}, #{example})")
    void insert(WordExplanation wordExplanation);

}
