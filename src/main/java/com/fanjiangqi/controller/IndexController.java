package com.fanjiangqi.controller;

import com.fanjiangqi.model.User;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by fanjiangqi on 2017/3/5.
 */
//@Controller
public class IndexController {
    @RequestMapping(path = {"/","/welcome"})
    @ResponseBody
    public String index(){
        return "Hello TouTiao";
    }

    ///profile/11/22?key=xx&type=33 访问这种类型的网页
    @RequestMapping(value = {"/profile/{groupId}/{userId}"})
    @ResponseBody
    public String profile(@PathVariable("groupId") String groupId,
                          @PathVariable("userId") int userId,
                          @RequestParam(value = "type", defaultValue = "1") int type,
                          @RequestParam(value = "key", defaultValue = "nowcoder") String key){
        return String.format("GID{%s}, UID{%s}, TYPE{%d}, KET{%S}", groupId, userId, type, key);
    }

    @RequestMapping(value ={"/vm"} )
    public String news(Model model){
        model.addAttribute("value1", "vv1");
        List<String> colors = Arrays.asList(new String[] {"RED", "GREEN", "BLUE"});
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < 4; i++){
            map.put(String.valueOf(i),String.valueOf(i*i));
        }
        model.addAttribute("colors", colors);
        model.addAttribute("map", map);
        model.addAttribute("user", new User());

        return "News";
    }
    @RequestMapping(value = {"/request"})
    @ResponseBody
    public String request(HttpServletRequest request,
                          HttpServletResponse response,
                          HttpSession session){
        StringBuilder sb = new StringBuilder();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()){
            String name = headerNames.nextElement();
            sb.append(name + ":" + request.getHeader(name) + "<br>");
        }
        for (Cookie cookie : request.getCookies()){
            sb.append("Cookie:");
            sb.append(cookie.getName());
            sb.append(":");
            sb.append(cookie.getValue());
            sb.append("<br>");

        }
        sb.append("getMethod:" + request.getMethod() + "<br>");
        sb.append("getPathInfo:" + request.getPathInfo() + "<br>");
        sb.append("getQueryString:" + request.getQueryString() + "<br>");
        sb.append("getRequestURI:" + request.getRequestURI() + "<br>");
        return sb.toString();
    }
    @RequestMapping(value = {"/respone"})
    @ResponseBody
    public String respone(@CookieValue(value = "nowcoderid", defaultValue = "a") String nowcodeId,
                          @RequestParam(value = "key", defaultValue = "key") String key,
                          @RequestParam(value = "value", defaultValue = "value") String value,
                          HttpServletResponse response){
        response.addCookie(new Cookie(key, value));
        response.addHeader(key, value);
        return "NowCoderId From Cookie:" + nowcodeId;
    }
    @RequestMapping("/redirect/{code}")
    public RedirectView redirect(@PathVariable("code") int code){
        RedirectView red = new RedirectView("/", true);
        if (code == 301){
            red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        }
        return  red;
    }
    @RequestMapping(value = {"admin"})
    @ResponseBody
    public String admin(@RequestParam(value = "key",required = false) String key){
        if ("admin".equals(key)){
            return "hello admin";
        }
        else throw new IllegalArgumentException("key 错误");
    }
    @ExceptionHandler()
    @ResponseBody
    public String error(Exception e){
        return "error:" + e.getMessage();
    }
}
