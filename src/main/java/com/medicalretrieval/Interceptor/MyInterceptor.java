package com.medicalretrieval.Interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medicalretrieval.pojo.user.User;
import com.medicalretrieval.utils.Result;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class MyInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean flag = true;
        User user = (User) request.getSession().getAttribute("user");
        if (user==null){
            response.setContentType("application/json;charset=UTF-8");
            //跳转到登录页面
            Result result = Result.NoLogin("用户未登录",null);
            PrintWriter writer = response.getWriter();
            writer.write(new ObjectMapper().writeValueAsString(result));
            flag = false;
            writer.close();
        }
        return flag;
    }
}
