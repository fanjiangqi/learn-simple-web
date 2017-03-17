package com.fanjiangqi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by fanjiangqi on 2017/3/17.
 */
@Controller
public class HomeController {
    @RequestMapping(value = {"/","/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model){

        return "home";
    }
}
