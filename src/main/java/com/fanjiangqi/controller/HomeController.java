package com.fanjiangqi.controller;

import com.fanjiangqi.model.EntityType;
import com.fanjiangqi.model.HostHolder;
import com.fanjiangqi.model.News;
import com.fanjiangqi.model.ViewObject;
import com.fanjiangqi.service.LikeService;
import com.fanjiangqi.service.NewsService;
import com.fanjiangqi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fanjiangqi on 2017/3/17.
 */
@Controller
public class HomeController {
    @Autowired
    NewsService newsService;
    @Autowired
    UserService userService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    LikeService likeService;

    private List<ViewObject> getNews(int userId, int offset, int limit){
        List<News> newsList = newsService.getLatestNews(userId,0,10);
        int localUserId = hostHolder.getUser() != null ? hostHolder.getUser().getId() : 0;
        List<ViewObject> vos = new ArrayList<>();
        for (News news : newsList){
            ViewObject vo = new ViewObject();
            vo.set("news", news);
            vo.set("user", userService.getUser(news.getUserId()));
            if (localUserId != 0) {
                vo.set("like", likeService.getLikeStatus(EntityType.NEWS_ENTITY, news.getId(),localUserId ));
            } else {
                vo.set("like", 0);
            }
            vos.add(vo);
        }
        return vos;
    }

    @RequestMapping(path = {"/","/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model){
        model.addAttribute("vos",getNews(0,0,10));
        return "home";
    }
    @RequestMapping(path = {"/user/{userId}"},method = RequestMethod.GET)
    public String userIndex(Model model, @PathVariable("userId") int userId){
        model.addAttribute("vos",getNews(userId,0,10));
        return "home";
    }
}
