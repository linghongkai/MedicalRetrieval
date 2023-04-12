package com.medicalretrieval.Interceptor;

import com.medicalretrieval.pojo.user.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean flag = true;

        User user = (User) request.getSession().getAttribute("user");
        if (user==null){
            //跳转到登录页面
            response.sendRedirect("");
            flag = false;
        }

        return flag;
    }
}
