package com.owl.magicFile.conf;


import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 添加标头
 * author engwen
 * email xiachanzou@outlook.com
 * 2017/4/15.
 */
public class BMSecurity extends HandlerInterceptorAdapter {
    private static Logger logger = Logger.getLogger(BMSecurity.class.getName());
    //不需要登录即可访问的方法.

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.addHeader("Access-Control-Allow-Origin", "*");
        return true;
    }

}
