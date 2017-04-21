package com.fanjiangqi.service;

import com.fanjiangqi.controller.LoginController;
import com.fanjiangqi.dao.NewsDAO;
import com.fanjiangqi.model.News;
import com.fanjiangqi.util.ToutiaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

/**
 * Created by fanjiangqi on 2017/3/17.
 */
@Service
public class NewsService {
    private static final Logger logger = LoggerFactory.getLogger(NewsService.class);
    @Autowired
    private NewsDAO newsDAO;

    public List<News> getLatestNews(int userId, int offset, int limit){
        return newsDAO.selectByUserIdAndOffset(userId,offset,limit);
    }

    //保存在本地服务器
    public String saveImage(MultipartFile file) throws IOException {
        int dosPos = file.getOriginalFilename().lastIndexOf(".");
        if (dosPos < 0){
            return null;
        }
        String fileExt = file.getOriginalFilename().substring(dosPos+1).toLowerCase();//取拓展名
        if (!ToutiaoUtil.isFileAllowed(fileExt)){
            return null;
        }
        String fileName = UUID.randomUUID().toString().replaceAll("-", "")+ "." + fileExt;

        Files.copy(file.getInputStream(), new File(ToutiaoUtil.IMAGE_DIR+fileName).toPath(),
                    StandardCopyOption.REPLACE_EXISTING);
        return ToutiaoUtil.TOUTIAO_DOMAIN + "image?name=" + fileName; //返回图片在服务器中的地址

    }
    public void addNews(News news){
        newsDAO.addNews(news);
    }

    public News getNewsById(int id){
        return  newsDAO.selectById(id);
    }
    public int updateCommentCount(int count, int id){
        return newsDAO.updateCommentCount(count, id);
    }
    public int updateLikeCount(int count, int id){
        return newsDAO.updateLikeCount(count, id);
    }

}
