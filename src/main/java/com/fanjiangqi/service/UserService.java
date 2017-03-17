package com.fanjiangqi.service;

import com.fanjiangqi.dao.UserDAO;
import com.fanjiangqi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
