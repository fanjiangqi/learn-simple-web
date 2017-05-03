package com.fanjiangqi.controller;

import com.fanjiangqi.asyn.EventHandler;
import com.fanjiangqi.asyn.EventModel;
import com.fanjiangqi.asyn.EventProducer;
import com.fanjiangqi.asyn.EventType;
import com.fanjiangqi.model.EntityType;
import com.fanjiangqi.model.HostHolder;
import com.fanjiangqi.service.LikeService;
import com.fanjiangqi.service.NewsService;
import com.fanjiangqi.util.ToutiaoUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by fanjiangqi on 2017/4/20.
 */
@Controller
public class LikeController {
    @Autowired
    HostHolder hostHolder;
    @Autowired
    LikeService likeService;
    @Autowired
    NewsService newsService;
    @Autowired
    EventProducer eventProducer;

    @RequestMapping(value = {"/like"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String like(@RequestParam("newsId") int newsId){
        long likeCount = likeService.like(EntityType.NEWS_ENTITY, newsId, hostHolder.getUser().getId());
        newsService.updateLikeCount( (int)likeCount, newsId);
        eventProducer.fireEvent(new EventModel(EventType.LIKE).setActorId( hostHolder.getUser().getId())
        .setEntityOwnerId(newsService.getNewsById(newsId).getUserId()).setEntityId(newsId));
        return ToutiaoUtil.getJSONString(0,String.valueOf(likeCount));
    }
    @RequestMapping(value = {"/dislike"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String dislike(@RequestParam("newsId") int newsId){
        long likeCount = likeService.dislike(EntityType.NEWS_ENTITY, newsId, hostHolder.getUser().getId());
        newsService.updateLikeCount((int)likeCount, newsId);
        return ToutiaoUtil.getJSONString(0,String.valueOf(likeCount));
    }
}
