package com.xjc.controller.admin;

import javax.annotation.Resource;
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
	public String modifyPassword(String pwd,Integer id,HttpServletResponse response) {
		
		JSONObject jsonObj = new JSONObject();
		Blogger blogger = bloggerService.findById(id);
		blogger.setPassword(MD5EncodeUtils.encrypt(pwd, "xjc"));
		int result = bloggerService.update(blogger);
		if(result > 0)
			jsonObj.put("success", true);
		else
			jsonObj.put("success", false);
		ResponseUtils.writeJson(response, jsonObj.toString());
		return null;
	}
}
