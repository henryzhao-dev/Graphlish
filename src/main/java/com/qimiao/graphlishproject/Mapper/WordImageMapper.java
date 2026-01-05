package com.qimiao.graphlishproject.Mapper;

import com.qimiao.graphlishproject.Entity.WordImage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WordImageMapper {

    //query word images by word id
    @Select("""
            select id, word_id, image_path, source, sort_order, is_active, created_at
            from Graphlish.word_image
            where word_id = #{wordId}
            order by sort_order
            """)
    List<WordImage> queryByWordId(@Param("wordId") Long wordId);

    //insert information such as image_path
    @Insert("insert into Graphlish.word_image (word_id, image_path, sort_order) " +
            "values (#{wordId}, #{imagePath}, #{sortOrder})")
    void insert(WordImage wordImage);



}
