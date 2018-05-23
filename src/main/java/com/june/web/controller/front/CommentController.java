package com.june.web.controller.front;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.june.dao.UserMapper;
import com.june.model.Comment;
import com.june.model.User;
import com.june.service.CommentService;
import com.june.util.Constants;
import com.june.util.ResponseUtils;
import com.june.util.StringUtils;

@Controller
@RequestMapping("/comment")
public class CommentController {

	@Resource
	private UserMapper userMapper;
	
	@Resource
	private CommentService commentService;
	
	/**
	 * 用户评论
	 * @param comment
	 * @param user
	 * @param response
	 */
	@RequestMapping("/save")
	public void save(String vCode, Integer blogId, Comment comment, User user,
			HttpServletResponse response, HttpSession session) {
		String validateCode = (String) session.getAttribute(Constants.VALIDATE_CODE);
		JSONObject jsonObj = new JSONObject();
		if (StringUtils.isEmpty(validateCode) || !validateCode.equalsIgnoreCase(vCode)) {
			jsonObj.put("success", false);
			jsonObj.put("errorInfo", "验证码错误");
			ResponseUtils.writeJson(response, jsonObj.toString());
			return;
		}
		int result = 0;
		Integer userId = user.getId();
		if (userId == null) {
			userId = userMapper.insertSelective(user);
		}
		comment.setBlogId(blogId);
		comment.setUserId(userId);
		comment.setUserName(user.getUserName());
		comment.setContent(StringEscapeUtils.escapeHtml4(comment.getContent()));
		result = commentService.add(comment);
		jsonObj.put("success", result > 0);
		ResponseUtils.writeJson(response, jsonObj.toString());
	}
	
}
