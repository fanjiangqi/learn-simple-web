package com.fanjiangqi;

import com.fanjiangqi.dao.CommentDAO;
import com.fanjiangqi.dao.LoginTicketDAO;
import com.fanjiangqi.dao.NewsDAO;
import com.fanjiangqi.dao.UserDAO;
import com.fanjiangqi.model.*;
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
    @Autowired
    private CommentDAO commentDAO;

   @Autowired
   private LoginTicketDAO loginTicketDAO;
    @Test
    public void initData(){
        Random random = new Random();
        for (int i = 0; i < 10; ++i){
            User user = new User();
            user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png",  random.nextInt(1000)));
            user.setName(String.format("USER%d", i+1));
            user.setPassword("");
            user.setSalt("");
            userDAO.addUser(user);

            News news = new News();
            news.setCommentCount(3);
            Date date = new Date();
            date.setTime(date.getTime() + 1000*3600*5*i);
            news.setCreatedDate(date);
            news.setImage(String.format("http://images.nowcoder.com/head/%dm.png", random.nextInt(1000) ));
            news.setLikeCount(i+1);
            news.setUserId(i+1);
            news.setTitle(String.format("TITLE{%d}", i+1));
            news.setLink(String.format("http://www.nowcoder.com/%d.html", i));
            newsDAO.addNews(news);

            user.setPassword("newpassword");
            userDAO.updatePassword(user);

            for (int j = 0; j < 3; j++){
                Comment comment = new Comment();
                comment.setEntityId(i+1);
                comment.setEntityType(EntityType.NEWS_ENTITY);
                comment.setContent(String.format("comment%d",i+1));
                comment.setUserId(i+1);
                Date date1 = new Date();
                date.setTime(date.getTime()+ 1000*3600*j);
                comment.setCreatedDate(date1);
                commentDAO.insertComment(comment);
            }

            LoginTicket ticket = new LoginTicket();
            ticket.setUserId(i+1);
            ticket.setTicket(String.format("ticket%d",i+1));
            //Date date = new Date();
            ticket.setExpired(date);
            ticket.setStatus(0);
            loginTicketDAO.addTicket(ticket);
            loginTicketDAO.updateStatus(ticket.getTicket(),2);
        }
        Assert.assertEquals("newpassword",userDAO.selectByUserId(2).getPassword());
        Assert.assertEquals("newpassword",userDAO.selectByUserName("USER4").getPassword());
        userDAO.deleteById(1);
        Assert.assertNull(userDAO.selectByUserId(1));
        Assert.assertEquals(1,loginTicketDAO.selectByTicket("ticket1").getUserId());
        Assert.assertEquals(2,loginTicketDAO.selectByTicket("ticket1").getStatus());
        Assert.assertEquals("TITLE{1}",newsDAO.selectById(1).getTitle());
        Assert.assertNotNull(commentDAO.selectComment(1,EntityType.NEWS_ENTITY).get(0));
        newsDAO.updateCommentCount(10,1);
        Assert.assertEquals(10,newsDAO.selectById(1).getCommentCount());
        Assert.assertEquals(3,commentDAO.getCommentCount(1,EntityType.NEWS_ENTITY));


    }
        @Test
    public void testNewsDao(){
        newsDAO.selectByUserIdAndOffset(0,0,10);
        }

}
