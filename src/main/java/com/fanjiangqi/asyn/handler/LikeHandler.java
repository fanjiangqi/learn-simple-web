package com.fanjiangqi.asyn.handler;

import com.fanjiangqi.asyn.EventHandler;
import com.fanjiangqi.asyn.EventModel;
import com.fanjiangqi.asyn.EventType;
import com.fanjiangqi.model.Message;
import com.fanjiangqi.model.User;
import com.fanjiangqi.service.MessageService;
import com.fanjiangqi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by fanjiangqi on 2017/4/29.
 */
@Component
public class LikeHandler implements EventHandler {
    @Autowired
    UserService userService;
    @Autowired
    MessageService messageService;

    @Override
    public void doHandle(EventModel model) {
        System.out.println("like");
        Message message = new Message();
        message.setFromId(3);//表示系统发送
        //message.setToId(model.getEntityOwnerId());
        message.setToId(model.getActorId());
        User user = userService.getUser(model.getActorId());
        message.setContent("用户"+ user.getName() +"赞了你的资讯,http://127.0.0.1:8080/news/" + model.getEntityId());
        message.setCreatedDate(new Date());
       /* String conversationId = model.getActorId() < model.getEntityOwnerId() ? ( String.valueOf(model.getActorId())+ "_" + String.valueOf(model.getEntityOwnerId())) :
                ( String.valueOf(model.getEntityOwnerId())+ "_" + String.valueOf(model.getActorId()));*/
        String conversationId = String.valueOf(message.getFromId()) + "_" +String.valueOf(model.getEntityOwnerId());
        message.setConversationId(conversationId);
        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}
