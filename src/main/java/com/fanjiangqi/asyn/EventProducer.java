package com.fanjiangqi.asyn;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fanjiangqi.util.JedisAdapter;
import com.fanjiangqi.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by fanjiangqi on 2017/4/29.
 */
@Service
public class EventProducer {

    @Autowired
    JedisAdapter jedisAdapter;

    /**
     * 把事件添加到队列中
     * @param model
     * @return
     */
    public boolean fireEvent(EventModel model){
        try{
            String key = RedisKeyUtil.getEventQueueKey();
            String json = JSON.toJSONString(model);
            jedisAdapter.lpush(key, json);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
