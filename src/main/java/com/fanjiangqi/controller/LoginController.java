package com.fanjiangqi.controller;


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

import java.util.Map;

/**
 * Created by fanjiangqi on 2017/3/22.
 */
@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    UserService userService;

    @RequestMapping(value = {"/reg/"}, method = {RequestMethod.GET, RequestMethod.POST} )
    @ResponseBody
    public String reg(Model model, @RequestParam("username") String username,
                      @RequestParam("password") String password,
                      @RequestParam(value = "rember",defaultValue = "0") int rember){
        try {
            Map<String,Object> map = userService.register(username,password);
            if (map.isEmpty())
                return ToutiaoUtil.getJSONString(0,"注册成功");
            else
                return ToutiaoUtil.getJSONString(1,map);
        }catch (Exception e){
            logger.error("注册异常"+e.getMessage());
            return ToutiaoUtil.getJSONString(1,"注册异常");
        }


    }

}
