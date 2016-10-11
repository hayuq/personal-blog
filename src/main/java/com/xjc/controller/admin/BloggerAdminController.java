package com.xjc.controller.admin;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xjc.model.Blogger;
import com.xjc.service.BloggerService;
import com.xjc.service.CommentService;
import com.xjc.util.MD5EncodeUtils;
import com.xjc.util.ResponseUtils;

@Controller
@RequestMapping("/blogger")
public class BloggerAdminController {

	@Resource
	CommentService commentService;
	
	@Resource
	BloggerService bloggerService;
	
	@RequestMapping("/toModifyInfo")
	public String toModifyInfo() {
		return "blogger/modifyInfo";
	}
	
	@RequestMapping("/toModifyPassword")
	public String toModifyPassword() {
		return "blogger/modifyPassword";
	}
	
	@RequestMapping("/modifyInfo")
	public String modifyInfo() {
		return "blogger/modifyInfo";
	}
	
	@RequestMapping("/modifyPassword")
	public String modifyPassword(String newpwd,String oldpwd,String repwd,
			HttpServletResponse response,HttpServletRequest request) {
		
		Blogger blogger = bloggerService.findById(1);
		System.out.println(blogger);
		if(!blogger.getPassword().equals(MD5EncodeUtils.encrypt(oldpwd, "xjc"))){
			ResponseUtils.writeText(response, "原密码输入不正确");
			return null;
		}
		if(!newpwd.equals(repwd)){
			ResponseUtils.writeText(response, "两次密码输入不一致");
			return null;
		}
		blogger.setPassword(MD5EncodeUtils.encrypt(newpwd, "xjc"));
		bloggerService.update(blogger);
		ResponseUtils.writeText(response,"修改成功");
		return null;
	}
}
