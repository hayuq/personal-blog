package com.june.web.interceptor;

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
 */
public @Slf4j class AuthorizationInterceptor extends HandlerInterceptorAdapter {
	
	@Resource
	private BloggerService bloggerService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("拦截请求路径：{}", request.getRequestURI());
		}
		
        Blogger blogger = (Blogger) request.getSession(false).getAttribute(Constants.CURRENT_USER);
        if (Objects.isNull(blogger) || blogger != bloggerService.find()) {
        	log.info("未登录");
        	response.sendRedirect("/admin/login.do");
        	return false;
        }
		return true;
	}
	
}
