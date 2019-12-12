package com.sifo.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sifo.entity.User;
import com.sifo.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sso")
public class SsoController {
    @Reference
    IUserService userService;

    @RequestMapping("/toRegister")
    public String toRegister(){

        return "user_register";
    }

    @RequestMapping("/toLogin")
    public String toLogin(){

        return "user_login";
    }


    @RequestMapping("/register")
    public String register(User user, Model model){
        int result = userService.register(user);
        if(result == 0)
        {
            return "user_login";
        }else if(result == -1){
            model.addAttribute("error","用户名已经存在!");
        } else if(result == -2){
            model.addAttribute("error","邮箱已被注册过!");
        }
        return "user_register";
    }
}
