package com.fanjiangqi.dao;

import com.fanjiangqi.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Created by fanjiangqi on 2017/3/6.
 */
@Mapper
public interface UserDao {
     String TABLE_NAME = "user";
     String SELECT_FIELDS = " name, password ";

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where name=#{name}"})
     User selectByUserName(String name);
}
