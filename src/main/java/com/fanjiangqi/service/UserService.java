package com.fanjiangqi.service;

import com.fanjiangqi.dao.LoginTicketDAO;
import com.fanjiangqi.dao.UserDAO;
import com.fanjiangqi.model.LoginTicket;
import com.fanjiangqi.model.User;
import com.fanjiangqi.util.ToutiaoUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by fanjiangqi on 2017/3/7.
 */
@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private LoginTicketDAO loginTicketDAO;
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
        user.setPassword(ToutiaoUtil.MD5(password+user.getSalt()));
        userDAO.addUser(user);

        String ticket = addLoginTicket(user.getId());
        map.put("ticket",ticket);
        return map;

    }
    public Map<String, Object> login(String username, String password){
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
        if (user == null){
            map.put("msgname","用户名不存在");
            return map;
        }

        if (!ToutiaoUtil.MD5( password+ user.getSalt()).equals(user.getPassword())){
            map.put("msgname","密码错误");
            return map;
        }

        String ticket = addLoginTicket(user.getId());
        map.put("ticket",ticket);
        return map;

    }
    private String addLoginTicket(int userId){
        LoginTicket ticket = new LoginTicket();
        ticket.setUserId(userId);
        ticket.setTicket(UUID.randomUUID().toString().replace("-",""));
        ticket.setStatus(0);
        Date date = new Date();
        date.setTime(date.getTime()+1000*3600*24);
        ticket.setExpired(date);
        loginTicketDAO.addTicket(ticket);
        return ticket.getTicket();
    }
    public void logout(String ticket){
        loginTicketDAO.updateStatus(ticket,1);
    }
}
