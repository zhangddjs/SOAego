package com.ego.cart.interceptor;

import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.HttpClientUtil;
import com.ego.commons.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
/*
    @Value("${passport.url}")
    private String passportUrl;
*/


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        String token = CookieUtils.getCookieValue(httpServletRequest, "TT_TOKEN");
        if(token != null && !token.equals("")){
            String json = HttpClientUtil.doPost("http://passport.ego.com:30080/user/token/" + token);
            EgoResult er = JsonUtils.jsonToPojo(json, EgoResult.class);
            if(er.getStatus() == 200){
                return true;
            }
        }
        String num = httpServletRequest.getParameter("num");
        StringBuffer interurl = httpServletRequest.getRequestURL();
        interurl.insert(interurl.indexOf("/" ,8), ":30080");
        System.out.println(interurl);
        httpServletResponse.sendRedirect("http://passport.ego.com:30080/user/showLogin?interurl="+interurl+"%3Fnum=" + num);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
