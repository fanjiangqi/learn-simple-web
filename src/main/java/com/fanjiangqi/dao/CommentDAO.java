package com.fanjiangqi.dao;

import com.fanjiangqi.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by fanjiangqi on 2017/4/7.
 */
@Mapper
public interface CommentDAO {
    String TABLE_NAME = "comment";
    String SELECT_FIELDS = " id, content, entity_id, entity_type, created_date, status, user_id ";
    String INSERT_FIELDS = " content, entity_id, entity_type, created_date, status, user_id ";

    @Insert({"insert into", TABLE_NAME, "(", INSERT_FIELDS, ")",
            "value(#{content}, #{entityId}, #{entityType}, #{createdDate}, #{status}, #{userId})"})
    int insertComment(Comment comment);

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME,
            "where entity_id=#{entityId} and entity_type=#{entityType} order by created_date desc"})
    List<Comment> selectComment(@Param("entityId") int entityId, @Param("entityType") int entityType);

    @Select({"select count(id) from", TABLE_NAME, "where entity_id=#{entityId} and entity_type=#{entityType}"})
    int getCommentCount(@Param("entityId") int entityId, @Param("entityType") int entityType);

}
