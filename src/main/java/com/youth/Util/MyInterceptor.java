package com.youth.Util;

import cn.hutool.core.util.StrUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object user = request.getSession().getAttribute("user");
        // 测试接口放行
        StringBuffer url = request.getRequestURL();
        //System.out.println(url);
        if (StrUtil.contains(url, "test")) {
            return true;
        }

        if (user == null) {
            request.setAttribute("msg", "没有权限");
            request.getRequestDispatcher("/login").forward(request, response);
            //response.sendRedirect("/login");
            return false;
        }
        else{
            return true;
        }
    }
}