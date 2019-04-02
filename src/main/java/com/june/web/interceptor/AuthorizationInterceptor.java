package com.june.web.interceptor;

import java.io.PrintWriter;
import java.util.Objects;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.june.model.Blogger;
import com.june.service.BloggerService;
import com.june.util.Constants;

import lombok.extern.slf4j.Slf4j;

/**
 * 登录验证拦截器
 * @author June
 */
public @Slf4j class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    @Resource
    private BloggerService bloggerService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        if (AuthorizationInterceptor.log.isDebugEnabled()) {
            AuthorizationInterceptor.log.debug("拦截请求路径：{}", request.getRequestURI());
        }

        Blogger blogger = (Blogger) request.getSession().getAttribute(Constants.CURRENT_USER);
        if (Objects.isNull(blogger) || !blogger.equals(bloggerService.find())) {
            AuthorizationInterceptor.log.info("未登录，跳转至登录页面");
            String loginUrl = request.getContextPath() + "/admin/login.do";
            String script = "<script>window.parent.location='" + loginUrl + "'</script>";
            try (PrintWriter out = response.getWriter()) {
                out.write(script);
                out.flush();
            }
            return false;
        }
        return true;
    }

}
