package com.fanjiangqi.service;

import com.fanjiangqi.dao.MessageDAO;
import com.fanjiangqi.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fanjiangqi on 2017/4/10.
 */
@Service
public class MessageService {
    @Autowired
    MessageDAO messageDAO;
    public  int addMessage(Message message){
        return messageDAO.addMessage(message);
    }
    public List<Message> getConversationList(int userId, int offset, int limit){
       return messageDAO.getConversationList(userId, offset, limit);
    }
    public int getUnreadCount(int userId, String conversationId){
        return messageDAO.getConversationUnReadCount(userId,conversationId);
    }
    public List<Message> getConversationDetail(String conversationId, int offset, int limit){
        return messageDAO.getConversationDetail(conversationId, offset, limit);
    }
}
