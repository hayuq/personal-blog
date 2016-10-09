package com.xjc.controller.admin;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xjc.service.CommentService;

@Controller
@RequestMapping("/blogger")
public class BloggerAdminController {

	@Resource
	CommentService commentService;
	
	@RequestMapping("/modifyInfo")
	public String modifyInfo() {
		
		return "blogger/modifyInfo";
	}
	
	@RequestMapping("/modifyPassword")
	public String modifyPassword() {
		
		return "blogger/modifyPassword";
	}
}
