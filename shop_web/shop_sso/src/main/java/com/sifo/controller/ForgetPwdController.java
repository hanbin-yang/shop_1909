package com.sifo.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sifo.entity.Email;
import com.sifo.entity.ResultData;
import com.sifo.entity.User;
import com.sifo.service.IUserService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
@RequestMapping("/forget")
public class ForgetPwdController {

    @Reference
    private IUserService userService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @RequestMapping("/toForgetPwd")
    public String toForgetPwd(){

        return "user_forgetpwd";
    }

    @RequestMapping("/sendMsg")
    @ResponseBody
    public ResultData<String> sendMsg(String username){

        //通过用户名找到用户信息
        User user = userService.queryByUserName(username);

        if(user==null)
        {
            return new ResultData<String>().setCode(ResultData.ResultCodeList.ERROR).setMsg("用户名不存在");
        }
        String url = "http://localhost:8084/forget/toUpdatePwd?token=";
        //发送邮件
        Email email=new Email();
        email.setSubject("找回密码，非本人操作请忽略！")
                .setTo(user.getEmail())
                .setContext("点击<a href='http://www.baidu.com'>这里</a>找回密码")
                .setSendTime(new Date());

        //rabbitTemplate.convertAndSend("mail_exchange","",email);
        return new ResultData<String>().setCode(ResultData.ResultCodeList.OK).setMsg("发送成功");
    }
}
