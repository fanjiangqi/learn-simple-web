package com.fanjiangqi;

import com.fanjiangqi.dao.NewsDAO;
import com.fanjiangqi.dao.UserDAO;
import com.fanjiangqi.model.News;
import com.fanjiangqi.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.Random;


/**
 * Created by fanjiangqi on 2017/3/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(LearnSimpleWebApplication.class)
@Sql("/init-schema.sql")
public class InitDatabaseTests {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private NewsDAO newsDAO;
    @Test
    public void getData(){
        Assert.assertEquals("654321",userDAO.selectByUserName("fan").getPassword());
    }
    @Test
    public void initData(){
        Random random = new Random();
        for (int i = 0; i < 10; ++i){
            User user = new User();
            user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png",  random.nextInt(1000)));
            user.setName(String.format("USER%d", i));
            user.setPassword("");
            user.setSalt("");
            userDAO.addUser(user);

            News news = new News();
            news.setCommentCount(i);
            Date date = new Date();
            date.setTime(date.getTime() + 1000*3600*5*i);
            news.setCreatedDate(date);
            news.setImage(String.format("http://images.nowcoder.com/head/%dm.png", random.nextInt(1000) ));
            news.setLikeCount(i+1);
            news.setUserId(i+1);
            news.setTitle(String.format("TITLE{%d}", i));
            news.setLink(String.format("http://www.nowcoder.com/%d.html", i));
            newsDAO.addNews(news);
            user.setPassword("newpassword");
            userDAO.updatePassword(user);
        }
        Assert.assertEquals("newpassword",userDAO.selectByUserId(2).getPassword());
        userDAO.deleteById(1);
        Assert.assertNull(userDAO.selectByUserId(1));

    }


}
