package com.fanjiangqi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by fanjiangqi on 2017/3/16.
 */
@Controller
public class SettingController {
    @RequestMapping(value = {"/setting"})
    @ResponseBody
    public String setting(){
        return "setting page";
    }
}
