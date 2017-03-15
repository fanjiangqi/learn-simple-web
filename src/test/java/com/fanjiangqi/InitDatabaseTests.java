package com.fanjiangqi;

import com.fanjiangqi.dao.UserDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * Created by fanjiangqi on 2017/3/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(LearnSimpleWebApplication.class)
public class InitDatabaseTests {
    @Autowired
    private UserDao userDao;
    @Test
    public void getData(){
        Assert.assertEquals("654321",userDao.selectByUserName("fan").getPassword());
    }

}
