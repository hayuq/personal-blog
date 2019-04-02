package com.june.web.listener;

import java.io.IOException;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.june.util.Constants;

import lombok.extern.slf4j.Slf4j;

@WebListener
public @Slf4j class SessionAttributeListener implements HttpSessionAttributeListener {

	@Override
	public void attributeAdded(HttpSessionBindingEvent event) {
		if (Constants.CURRENT_USER.equals(event.getName())) {
			if (log.isDebugEnabled()) {
				log.debug("创建用户Session成功");
			}
		}
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent event) {
		if (Constants.CURRENT_USER.equals(event.getName())) {
			if (log.isDebugEnabled()) {
				log.debug("用户Session被移除");
			}
			ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			HttpServletRequest request = requestAttributes.getRequest();
			HttpServletResponse response = requestAttributes.getResponse();
			// try {
			// 	response.sendRedirect(request.getContextPath() + "admin/login.do");
			// } catch (IOException e) {
			// 	log.error(e.getMessage(), e.getCause());
			// }
		}
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent event) {
		if (Constants.CURRENT_USER.equals(event.getName())) {
			if (log.isDebugEnabled()) {
				log.debug("更新用户Session成功");
			}
		}
	}

}
