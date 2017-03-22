package com.fanjiangqi.controller;

import com.fanjiangqi.model.News;
import com.fanjiangqi.model.ViewObject;
import com.fanjiangqi.service.NewsService;
import com.fanjiangqi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    private List<ViewObject> getNews(int userId, int offset, int limit){
        List<News> newsList = newsService.getLatestNews(0,0,10);
        List<ViewObject> vos = new ArrayList<>();
        for (News news : newsList){
            ViewObject vo = new ViewObject();
            vo.set("news", news);
            vo.set("user", userService.getUser(news.getUserId()));
            vos.add(vo);
        }
        return vos;
    }

    @RequestMapping(path = {"/","/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model){
        model.addAttribute("vos",getNews(0,0,10));
        return "home";
    }
}
