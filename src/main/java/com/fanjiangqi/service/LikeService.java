package com.fanjiangqi.service;

import com.fanjiangqi.util.JedisAdapter;
import com.fanjiangqi.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by fanjiangqi on 2017/4/20.
 */
@Service
public class LikeService {
    @Autowired
    JedisAdapter jedisAdapter;
    public long like(int entityType, int entityId, int userId){
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType, entityId);
        jedisAdapter.sadd(likeKey, String.valueOf(userId));//在喜欢的集合中添加
        long likeCount = jedisAdapter.scard(likeKey);
        jedisAdapter.srem(disLikeKey,String.valueOf(userId));//在 不喜欢的集合中删除
        return likeCount;
    }
    public long dislike(int entityType, int entityId, int userId){
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType, entityId);
        jedisAdapter.sadd(disLikeKey,String.valueOf(userId));//在 不喜欢的集合中添加
        jedisAdapter.srem(likeKey, String.valueOf(userId));//在喜欢的集合中删除
        long likeCount = jedisAdapter.scard(likeKey);

        return likeCount;
    }

    /**
     * 1表示喜欢 -1 表示不喜欢 0 表示没有操作
     * @param entityType
     * @param entityId
     * @param userId
     * @return
     */
    public int getLikeStatus(int entityType, int entityId, int userId){
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        if (jedisAdapter.sismember(likeKey,String.valueOf(userId))){
            return 1;
        }
        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType, entityId);
        return jedisAdapter.sismember(disLikeKey,String.valueOf(userId)) ? -1 : 0;
    }

}
