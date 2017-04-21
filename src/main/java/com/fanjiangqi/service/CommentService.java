package com.fanjiangqi.service;

import com.fanjiangqi.dao.CommentDAO;
import com.fanjiangqi.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fanjiangqi on 2017/4/7.
 */
@Service
public class CommentService {
    @Autowired
    CommentDAO commentDAO;

    public List<Comment> getCommentsByEntity(int entityId, int entityType){
        return commentDAO.selectComment(entityId,entityType);
    }
    public int addComment(Comment comment){
        return commentDAO.insertComment(comment);
    }
    public int getCommentCount(int entityId, int entityType){
        return commentDAO.getCommentCount(entityId,entityType);
    }
}
