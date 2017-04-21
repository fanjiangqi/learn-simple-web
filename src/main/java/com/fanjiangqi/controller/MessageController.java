package com.fanjiangqi.controller;

import com.fanjiangqi.model.HostHolder;
import com.fanjiangqi.model.Message;
import com.fanjiangqi.model.User;
import com.fanjiangqi.model.ViewObject;
import com.fanjiangqi.service.MessageService;
import com.fanjiangqi.service.UserService;
import com.fanjiangqi.util.ToutiaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by fanjiangqi on 2017/4/10.
 */
@Controller
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    MessageService messageService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    UserService userService;

    @RequestMapping(path ={"/msg/addMessage"}, method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String addMessage(@RequestParam("fromId") int fromId, @RequestParam("toId") int toId,
                             @RequestParam("content") String content){
        Message msg = new Message();
        msg.setFromId(fromId);
        msg.setToId(toId);
        msg.setContent(content);
        msg.setCreatedDate(new Date());
        msg.setConversationId(fromId < toId ? String.format("%d_%d",fromId, toId)
                : String.format("%d_%d",toId, fromId));
        messageService.addMessage(msg);
        return ToutiaoUtil.getJSONString(msg.getId());//为什么getId()可以获得值呢？？？
    }
    @RequestMapping(path = {"/msg/list"}, method = RequestMethod.GET)
    public String conversationList(Model model){
        try {
            int localUserId = hostHolder.getUser().getId();
            List<Message> conversationList = new ArrayList<>();
            conversationList = messageService.getConversationList(localUserId,0,10);
            List<ViewObject> conversations = new ArrayList<>();
            for (Message msg: conversationList){
                ViewObject vo = new ViewObject();
                vo.set("conversation", msg);
                //找到发送信息的用户id
                int targetId = msg.getFromId() == localUserId ? msg.getToId():msg.getFromId();
                vo.set("userId", targetId);
                vo.set("headUrl",userService.getUser(targetId).getHeadUrl());
                vo.set("userName",userService.getUser(targetId).getName());
                vo.set("unreadCount",messageService.getUnreadCount(localUserId, msg.getConversationId()));
                conversations.add(vo);
            }
            model.addAttribute("conversations",conversations);
        }catch (Exception e){
            logger.error("会话列表显示错误"+e.getMessage());
        }
        return "letter";
    }
    @RequestMapping(path = {"/msg/detail"}, method = RequestMethod.GET)
    public String conversationDetails(Model model,@RequestParam("conversationId") String conversationId){
        try {
            List<Message> conversationDetail = messageService.getConversationDetail(conversationId, 0 ,10);
           List<ViewObject> messages = new ArrayList<>();
           for (Message msg : conversationDetail){
               ViewObject vo = new ViewObject();
               vo.set("message",msg);
               User user = userService.getUser(msg.getFromId());
               vo.set("headUrl",user.getHeadUrl());
               vo.set("userName",user.getName());
               messages.add(vo);
           }
           model.addAttribute("messages",messages);
        }catch (Exception e){
            logger.error("会话详情显示错误"+ e.getMessage());
        }
        return "letterDetail";
    }
}
