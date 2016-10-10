package com.xjc.controller.admin;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Request;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xjc.model.Blogger;
import com.xjc.service.BloggerService;
import com.xjc.util.MD5EncodeUtils;
import com.xjc.util.ResponseUtils;

@Controller
@RequestMapping("/admin")
public class SystemController {

	@Resource
	BloggerService bloggerService;
	
	//页面跳转
	@RequestMapping("/{page}")
	public String page(@PathVariable String page) {
		return page;
	}
	
	//登录
	@RequestMapping("/userLogin")
	public String login(String username,String password,String vCode,Model model,
			HttpServletRequest request,HttpServletResponse response){

		Blogger blogger = bloggerService.findByName(username);
		boolean flag = blogger.getPassword().equals(MD5EncodeUtils.encrypt(password, "xjc"));
		if(blogger == null || !flag){
			ResponseUtils.writeText(response, "用户名或密码错误");
			return null;
		}
		request.getSession().setAttribute("user", username);
		return null;
	}
	
}
