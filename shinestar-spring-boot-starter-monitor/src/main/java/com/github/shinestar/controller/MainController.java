package com.github.shinestar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 主页面接口
 * @author yangcj
 *
 * 
 */
@Controller
@RequestMapping("/main")
public class MainController {

    /**
     * 仪表盘主页展示
     */
    @RequestMapping(value = "/index", consumes = "application/json")
    public void index(){
        return;
    }
}
