package com.xjc.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xjc.dao.UserMapper;
import com.xjc.model.Blog;
import com.xjc.model.Comment;
import com.xjc.model.User;
import com.xjc.service.BlogService;
import com.xjc.service.CommentService;
import com.xjc.util.ResponseUtils;

@Controller
@RequestMapping("/comment")
public class CommentController {

	@Resource
	UserMapper userMapper;
	
	@Resource
	BlogService blogService;
	
	@Resource
	CommentService commentService;
	/**
	 * 用户评论
	 * @param comment
	 * @param user
	 * @param response
	 */
	@RequestMapping("/save")
	public String save(String vCode,Integer blogId,Comment comment,String userName,String email,String website,HttpServletResponse response,
			HttpSession session){
		
		String validateCode = (String)session.getAttribute("validateCode");
		JSONObject jsonObj = new JSONObject();
		if(!vCode.equals(validateCode)){
			jsonObj.put("success", false);
			jsonObj.put("errorInfo", "验证码错误！");
		}else{
			int result = 0;
			User user = new User();
			user.setUserName(userName);
			user.setEmail(email);
			user.setWebsite(website);
			if (user.getId() == null) {
				userMapper.insertSelective(user);
			}
			if (comment.getId() == null) {
				comment.setUser(user);
				comment.setBlogId(blogId);
				comment.setContent(StringEscapeUtils.escapeHtml4(comment.getContent()));
				result = commentService.add(comment);
				// 博客的评论次数加1
				Blog blog=blogService.findById(blogId);
				blog.setReview(blog.getReview()+1);
				blogService.update(blog);
			}
			
			if (result > 0) {
				jsonObj.put("success", true);
			}
			else {
				jsonObj.put("success", false);
			}
		}
		ResponseUtils.writeJson(response, jsonObj.toString());
		return null;
	}
	
}
