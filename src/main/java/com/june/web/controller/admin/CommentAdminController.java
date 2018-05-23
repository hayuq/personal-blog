package com.june.web.controller.admin;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.june.model.Comment;
import com.june.model.PageBean;
import com.june.service.BlogService;
import com.june.service.CommentService;
import com.june.util.Constants;
import com.june.util.PageUtils;
import com.june.util.ResponseUtils;
import com.june.util.StringUtils;

@Controller
@RequestMapping("/comment")
public class CommentAdminController {

	@Resource
	private CommentService commentService;
	
	@Resource
	private BlogService blogService;
	
	@RequestMapping("/list")
	public String list(@RequestParam(defaultValue = "1") Integer page, 
			@RequestParam(defaultValue = Constants.BACK_PAGE_SIZE + 1 + "") Integer pageSize,
			String firstDate, String secondDate, String userName, 
			Boolean isPass, Model model, HttpServletRequest request) {
		
		Map<String, Object> params = new HashMap<String, Object>(6);
		params.put("firstDate", firstDate);
		params.put("secondDate", secondDate);
		params.put("userName", userName);
		params.put("isPass", isPass);
		int totalCount = commentService.getCount(params);
		PageBean pageBean = new PageBean(totalCount, page, pageSize);
		params.put("start", pageBean.getStart());
		params.put("size", pageSize);
		List<Comment> commentList = commentService.getCommentList(params);
		commentList.stream().forEach(comment -> {
			String content = comment.getContent();
			if (content.length() > 60) {
				comment.setContent(content.substring(0,60) + "...");
			}
		});
		model.addAttribute("pagination", pageBean);
		StringBuilder param = new StringBuilder(); // 分页查询参数
		param.append(StringUtils.isEmpty(firstDate) ? "" : "&firstDate=" + firstDate);
		param.append(StringUtils.isEmpty(secondDate) ? "" : "&secondDate=" + secondDate);
		param.append(StringUtils.isEmpty(userName) ? "" : "&userName=" + userName);
		param.append(isPass == null ? "" : "&isPass=" + isPass);
		
		String pageCode = PageUtils.genPagination(request.getContextPath() + "/comment/list.do", 
				pageBean, param.toString());
		model.addAttribute("pageCode", pageCode);
		model.addAttribute("entry", params);
		model.addAttribute("commentList", commentList);
		return "comment/list";
	}
	
	@RequestMapping("/toAdd")
	public String toAdd() {
		return "comment/add";
	}
	
	@RequestMapping("/detail")
	public String detail(Integer id,Model model){
		model.addAttribute("comment", commentService.findById(id));
		return "comment/detail";
	}
	
	//评论审核
	@RequestMapping("/audit")
	public void audit(Comment comment, HttpServletResponse response) {
		comment.setReplyDate(new Date());
		int result = commentService.audit(comment);
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("success", result > 0);
		ResponseUtils.writeJson(response, jsonObj.toString());
	}
	
	@RequestMapping("/delete")
	public String delete(Integer id) throws IOException{
		commentService.delete(id);
		return "redirect:/comment/list.do";
	}
	
	@RequestMapping("/deletes")
	public String deletes(String ids) {
		String[] idArr = org.springframework.util.StringUtils.commaDelimitedListToStringArray(ids);
		int len = idArr.length;
		Integer[] commentIds = new Integer[len];
		for (int i = 0; i < len; i++) {
			commentIds[i] = Integer.parseInt(idArr[i]);
		}
		commentService.batchDelete(commentIds);
		return "redirect:/comment/list.do";
	}
}
