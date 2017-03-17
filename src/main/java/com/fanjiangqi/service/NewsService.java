package com.fanjiangqi.service;

import com.fanjiangqi.dao.NewsDAO;
import com.fanjiangqi.model.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fanjiangqi on 2017/3/17.
 */
@Service
public class NewsService {
    @Autowired
    private NewsDAO newsDAO;
    public List<News> getLatestNews(int userId, int offset, int limit){
        return newsDAO.selectByUserIdAndOffset(userId,offset,limit);
    }

}
