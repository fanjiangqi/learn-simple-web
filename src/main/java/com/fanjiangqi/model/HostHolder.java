package com.fanjiangqi.model;

import org.springframework.stereotype.Component;

/**
 * Created by fanjiangqi on 2017/3/27.
 */
@Component
public class HostHolder {
    //为什么要是静态的？
    private static ThreadLocal<User> users = new ThreadLocal<>();

   public void setUser(User user){
       users.set(user);
    }
    public User getUser(){
       return users.get();
    }
    //防止内存存储太多的变量
    public void clear(){
        users.remove();
    }
}
