package com.fanjiangqi.interceptor;

import com.fanjiangqi.dao.LoginTicketDAO;
import com.fanjiangqi.dao.UserDAO;
import com.fanjiangqi.model.HostHolder;
import com.fanjiangqi.model.LoginTicket;
import com.fanjiangqi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by fanjiangqi on 2017/3/27.
 */
@Component
public class PassportInterceptor implements HandlerInterceptor {
    @Autowired
    private LoginTicketDAO loginTicketDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private HostHolder hostHolder;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ticket = null;
        if (request.getCookies() != null){
            for (Cookie cookie: request.getCookies()){
                if (cookie.getName().equals("ticket")){
                    ticket = cookie.getValue();
                    break;
                }

            }
        }
        if (ticket != null){
            LoginTicket loginTicket = loginTicketDAO.selectByTicket(ticket);
            if (loginTicket == null || loginTicket.getExpired().before(new Date()) || loginTicket.getStatus() != 0)
                return true;
            User user = userDAO.selectByUserId(loginTicket.getUserId());
           hostHolder.setUser(user);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
       // System.out.println(hostHolder.getUser());
        if (modelAndView != null && hostHolder.getUser() != null){
            modelAndView.addObject("user",hostHolder.getUser());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.clear();
    }
}
