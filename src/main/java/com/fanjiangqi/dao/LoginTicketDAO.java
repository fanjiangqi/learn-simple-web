package com.fanjiangqi.dao;

import com.fanjiangqi.model.LoginTicket;
import org.apache.ibatis.annotations.*;

/**
 * Created by fanjiangqi on 2017/3/27.
 */
@Mapper
public interface LoginTicketDAO {
    String TABLE_NAME = "login_ticket";
    String INSERT_FIELDS = " user_id, ticket, expired, status ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where ticket=#{ticket}"})
    LoginTicket selectByTicket(String ticket);



    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ")", "values(#{userId},#{ticket},#{expired},#{status})"})
    void addTicket(LoginTicket ticket);

    @Update({"update ", TABLE_NAME, "set status=#{status} where ticket=#{ticket} "})
    void updateStatus(@Param("ticket") String ticket, @Param("status") int status);

}
