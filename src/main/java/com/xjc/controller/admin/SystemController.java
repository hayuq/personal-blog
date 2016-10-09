package com.xjc.controller.admin;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xjc.service.BloggerService;

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
	public String login(Model model){

		
		return "redirect:index.do";
	}
	
}
