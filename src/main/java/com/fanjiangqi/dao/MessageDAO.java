package com.fanjiangqi.dao;

import com.fanjiangqi.model.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by fanjiangqi on 2017/4/10.
 */
@Mapper
public interface MessageDAO {
    String TABLE_NAME = "message";
    String SELECT_FIELDS = " id, from_id, to_id, created_date, content, has_read, conversation_id ";
    String INSERT_FIELDS = " from_id, to_id, created_date, content, has_read, conversation_id ";

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values(#{fromId},#{toId},#{createdDate},#{content},#{hasRead},#{conversationId})"})
    int addMessage(Message message);

    @Select({"select",INSERT_FIELDS, ",count(id) as id from (select * from ",
            TABLE_NAME, "where from_id=#{userId} or to_id=#{userId} order by id desc) as tt ",
    "group by conversation_id order by id desc limit #{offset},#{limit}"})
    List<Message> getConversationList(@Param("userId") int userId, @Param("offset") int offset,
                                      @Param("limit") int limit);

    @Select({"select count(id) from", TABLE_NAME, "where has_read=0 and to_id=#{userId} and conversation_id=#{conversationId}"})
    int getConversationUnReadCount(@Param("userId") int userId, @Param("conversationId") String conversationId);

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "where conversation_id=#{conversationId} order by id desc limit #{offset},#{limit}"})
    List<Message> getConversationDetail(@Param("conversationId") String  conversationId, @Param("offset") int offset,
                                        @Param("limit") int limit);
}
