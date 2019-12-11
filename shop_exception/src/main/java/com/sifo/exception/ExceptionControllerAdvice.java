package com.sifo.exception;

import com.sifo.entity.ResultData;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object exceptionControllerAdvice(HttpServletRequest request,Exception e){

        System.out.println("项目出现异常！" + e.getMessage());

        //获得响应头信息
        String header = request.getHeader("X-Requested-With");

        if("XMLHttpRequest".equals(header)){//是ajax请求异常

            return new ResultData<String>().setCode(ResultData.ResultCodeList.ERROR).setMsg("ajax服务繁忙！");
        }else {
            return new ModelAndView("myError");
        }
    }
}
