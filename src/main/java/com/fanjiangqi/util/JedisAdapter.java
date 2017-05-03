package com.fanjiangqi.util;

import com.alibaba.fastjson.JSON;
import com.fanjiangqi.controller.NewsController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

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
    public long lpush(String key, String value){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.lpush(key, value);

        }catch (Exception e){
            logger.error("redis 异常"+ e.getMessage());
            return 0;
        }finally {
            if (jedis != null)
                jedis.close();
        }
    }

    public List<String> brpop(int timeout, String key){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.brpop(timeout, key);

        }catch (Exception e){
            logger.error("redis 异常"+ e.getMessage());
            return null;
        }finally {
            if (jedis != null)
                jedis.close();
        }
    }

    public void set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.set(key, value);
        } catch (Exception e) {
            logger.error("redis 异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public String get(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.get(key);
        } catch (Exception e) {
            logger.error("redis 异常" + e.getMessage());
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
    public void setObject(String key, Object obj){
       set(key, JSON.toJSONString(obj));
    }

    //需要好好理解
    public <T> T getObjetct(String key, Class<T> clazz){
        String value = get(key);
        if (value != null){
            return JSON.parseObject(value, clazz);
        }
        return null;
    }

}
