package com.june.web.controller.admin;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.june.service.BloggerService;
import com.june.util.ResponseUtils;
import com.june.util.ShiroUtils;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/admin")
public @Slf4j class SysLoginOrOutController {

	@Resource
	private BloggerService bloggerService;

	@RequestMapping("/{page}")
	public String page(@PathVariable String page) {
		return page;
	}
	
	@RequestMapping("/logout")
	public String logout() {
		ShiroUtils.logout();
		return "login";
	}
	
	//登录
	@RequestMapping("/userLogin")
	public void login(String username, String password, HttpServletResponse response) {
		try {			
			// shiro 登录
			Subject subject = SecurityUtils.getSubject();
			if (!subject.isAuthenticated()) {
				if (log.isDebugEnabled()) {
					log.debug("执行 shiro 登录操作...");
				}
				subject.login(new UsernamePasswordToken(username, ShiroUtils.encryptPassword(password)));				
			}
		} catch (UnknownAccountException | IncorrectCredentialsException e) {
			ResponseUtils.writeText(response, "用户名或密码错误");
		} catch (Exception e) {
			ResponseUtils.writeText(response, "登录失败");
		}
	}
	
}
