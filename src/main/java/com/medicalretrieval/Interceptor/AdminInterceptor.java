package com.medicalretrieval.Interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medicalretrieval.pojo.user.User;
import com.medicalretrieval.utils.Result;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class AdminInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean flag = true;

        User user = (User) request.getSession().getAttribute("user");
        if (user == null || user.getPermissionGroupId()==0){
            response.setContentType("application/json;charset=UTF-8");
            flag = false;
            Result result = new Result(1,"未登录或没有权限",null);
            PrintWriter writer = response.getWriter();
            writer.write(new ObjectMapper().writeValueAsString(result));
            writer.close();
        }

        return flag;
    }
}
