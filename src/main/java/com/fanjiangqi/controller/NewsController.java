package com.fanjiangqi.controller;

import com.fanjiangqi.dao.CommentDAO;
import com.fanjiangqi.model.*;
import com.fanjiangqi.service.*;
import com.fanjiangqi.util.ToutiaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by fanjiangqi on 2017/3/29.
 */
@Controller
public class NewsController {
    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    NewsService newsService;
    @Autowired
    QiNiuService qiNiuService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    UserService userService;
    @Autowired
    CommentService commentService;
    @Autowired
    LikeService likeService;

    @RequestMapping(path = {"/uploadImage/"}, method = {RequestMethod.POST})
    @ResponseBody
    public String uploadImage(@RequestParam("file") MultipartFile file){
        try {
            String fileUrl = qiNiuService.saveImage(file);
           // System.out.println(file.getContentType());
            if (fileUrl == null){
                return ToutiaoUtil.getJSONString(1,"上传图片失败");
            }
            return ToutiaoUtil.getJSONString(0,fileUrl);
        } catch (IOException e) {
            logger.error("图片上传失败",e.getMessage());
            return ToutiaoUtil.getJSONString(1,"上传图片失败");
        }
    }

    @RequestMapping(path = {"/image"}, method = {RequestMethod.GET})
    public void getImage(@RequestParam("name") String imageName,
                           HttpServletResponse response){
        response.setContentType("image/jpeg");
        try {
            StreamUtils.copy(new FileInputStream(new File(ToutiaoUtil.IMAGE_DIR+imageName)),
                    response.getOutputStream());
        } catch (IOException e) {
          logger.error("获取图片失败",e.getMessage());
        }
    }

    @RequestMapping(path = {"/user/addNews"}, method = {RequestMethod.POST})
    @ResponseBody
    public String addNews(@RequestParam("image") String image,
                          @RequestParam("title") String title,
                          @RequestParam("link") String link){
        try {
            News news = new News();
            news.setCreatedDate(new Date());
            news.setLink(link);
            news.setTitle(title);
            news.setImage(image);
            if (hostHolder.getUser() ==null)
                news.setUserId(1000);//表示匿名
            else
                news.setUserId(hostHolder.getUser().getId());
            newsService.addNews(news);
            return ToutiaoUtil.getJSONString(0);
        }catch (Exception e){
            logger.error("添加资讯失败",e.getMessage());
            return ToutiaoUtil.getJSONString(1,"添加资讯失败");
        }

    }

    @RequestMapping(path = {"/news/{newsId}"}, method = RequestMethod.GET)
    public String newsDetail(@PathVariable("newsId") int newsId ,Model model){
        try{
            News news = newsService.getNewsById(newsId);
            int localUserId = hostHolder.getUser() != null ? hostHolder.getUser().getId() : 0;
            if (news != null){
                List<Comment> comments = commentService.getCommentsByEntity(news.getId(), EntityType.NEWS_ENTITY);
                List<ViewObject> commentVOs = new ArrayList<>();
                for (Comment comment : comments){
                    ViewObject commentVO = new ViewObject();
                    commentVO.set("comment", comment);
                    commentVO.set("user",userService.getUser(comment.getUserId()));
                    commentVOs.add(commentVO);
                }
                model.addAttribute("like", likeService.getLikeStatus(EntityType.NEWS_ENTITY, news.getId(),localUserId ));
                model.addAttribute("comments", commentVOs);
            }
            model.addAttribute("news",news);
            model.addAttribute("owner", userService.getUser(news.getUserId()));
        }catch (Exception e){
            logger.error("获取资讯明细错误"+ e.getMessage());
        }

        return "detail";
    }

    @RequestMapping(path = {"/addComment"}, method = RequestMethod.POST)
    public String addComment(@RequestParam("newsId") int newsId,
                             @RequestParam("content") String content){
        try {
            News news = newsService.getNewsById(newsId);
            //可以添加过滤content
            Comment comment = new Comment();
            comment.setEntityId(news.getId());
            comment.setEntityType(EntityType.NEWS_ENTITY);
            comment.setContent(content);
            comment.setUserId(hostHolder.getUser().getId());
            Date date = new Date();
            comment.setCreatedDate(date);
            commentService.addComment(comment);
            //更新news表中评论数
            int count = commentService.getCommentCount(newsId,EntityType.NEWS_ENTITY);
            newsService.updateCommentCount(count, newsId);
        }catch (Exception e){
            logger.error("添加评价失败"+ e.getMessage());
        }

        return "redirect:/news/"+String.valueOf(newsId);
    }
}
