package com.fanjiangqi.util;

import com.fanjiangqi.controller.NewsController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by fanjiangqi on 2017/4/20.
 */
@Service
public class JedisAdapter implements InitializingBean{
    private static final Logger logger = LoggerFactory.getLogger(JedisAdapter.class);

    //private Jedis jedis = null;
    private JedisPool pool = null;
    public static void main(String[] args) {
        Jedis jedis = new Jedis();
        jedis.flushAll();
        jedis.sadd("baidu","fan");
        jedis.sadd("baidu","lulu");
        System.out.println(jedis.scard("baidu"));
        System.out.println(jedis.smembers("baidu"));
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        pool = new JedisPool("localhost",6379);
    }
    public long sadd(String key, String value){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.sadd(key,value);

        }catch (Exception e){
            logger.error("redis sadd异常"+ e.getMessage());
            return 0;
        }finally {
            if (jedis != null)
                jedis.close();
        }

    }
    public long srem(String key, String value){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.srem(key,value);

        }catch (Exception e){
            logger.error("redis srem异常"+ e.getMessage());
            return 0;
        }finally {
            if (jedis != null)
                jedis.close();
        }
    }
    public boolean sismember(String key, String value){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.sismember(key,value);

        }catch (Exception e){
            logger.error("redis 异常"+ e.getMessage());
            return false;
        }finally {
            if (jedis != null)
                jedis.close();
        }

    }
    public long scard(String key){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.scard(key);

        }catch (Exception e){
            logger.error("redis 异常"+ e.getMessage());
            return 0;
        }finally {
            if (jedis != null)
                jedis.close();
        }

    }

}
