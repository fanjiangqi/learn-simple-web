package com.fanjiangqi.dao;

import com.fanjiangqi.model.News;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by fanjiangqi on 2017/3/17.
 */
@Mapper
public interface NewsDAO {
    String TABLE_NAME = "news";
    String INSERT_FIELDS = " title, link, image, like_count, comment_count, created_date, user_id";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into", TABLE_NAME, "(", INSERT_FIELDS, ") values (#{title},#{link},#{image},#{likeCount}," +
            "#{commentCount},#{createdDate},#{userId})"})
    void addNews(News news);

    List<News> selectByUserIdAndOffset(@Param("userId") int userId, @Param("offset") int offset,
                                       @Param("limit") int limit);

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "where id=#{id}"})
    News selectById(@Param("id") int id);

    @Update({"update", TABLE_NAME, "set comment_count=#{count} where id=#{id}"})
    int updateCommentCount(@Param("count") int count, @Param("id") int id);

    @Update({"update", TABLE_NAME, "set like_count=#{likeCount} where id=#{id}"})
    int updateLikeCount(@Param("likeCount") int count, @Param("id") int id);
}
