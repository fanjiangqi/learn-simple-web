package com.fanjiangqi.service;

import com.fanjiangqi.dao.UserDAO;
import com.fanjiangqi.model.User;
import com.fanjiangqi.util.ToutiaoUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * Created by fanjiangqi on 2017/3/7.
 */
@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;
    public User getUser(int id){
        return userDAO.selectByUserId(id);
    }
    public Map<String, Object> register(String username, String password){
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isBlank(username)){
            map.put("msgname","用户名不能为空");
            return map;
        }
        if (StringUtils.isBlank(password)){
            map.put("msgname","密码不能为空");
            return map;
        }
        User user = userDAO.selectByUserName(username);
        if (user != null){
            map.put("msgname","用户名已经存在");
            return map;
        }
        user = new User();
        user.setName(username);
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png",
                new Random().nextInt(1000)));
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        user.setPassword(ToutiaoUtil.MD5(password + user.getSalt()));
        userDAO.addUser(user);
        //登录
        return map;

    }
}
