package com.fanjiangqi.dao;

import com.fanjiangqi.model.User;
import org.apache.ibatis.annotations.*;

/**
 * Created by fanjiangqi on 2017/3/6.
 */
@Mapper
public interface UserDAO {
     String TABLE_NAME = "user";
     String SELECT_FIELDS = " id, name, password, salt, head_url ";
     String INSERT_FIELDS = " name, password, salt, head_url ";

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where name=#{name}"})
     User selectByUserName(String name);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    User selectByUserId(int id);

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ")", "values(#{name},#{password},#{salt},#{headUrl})"})
    void addUser(User user);

    @Update({"update ", TABLE_NAME, "set password=#{password} where id=#{id} "})
    void updatePassword(User user);

    @Delete({"delete from", TABLE_NAME, "where id=#{id}"})
    void deleteById(int id);
}
