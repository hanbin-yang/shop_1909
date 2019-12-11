package com.sifo.exception;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
public class SystemExceptionHandler implements ErrorController {

    @RequestMapping("/error")
    public String exceptionHandler(HttpServletResponse response){
        //获得系统出错的状态码
        int status = response.getStatus();
        switch (status)
        {
            case 401:
                return "401";
            case 402:
                return "402";
            case 403:
                return "403";
            case 404:
                return "404";
        }
        return "myError";
    }

    @Override
    public String getErrorPath() {
        return "error";
    }
}
