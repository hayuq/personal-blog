package com.xjc.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.opensymphony.oscache.util.StringUtil;
import com.xjc.model.Comment;
import com.xjc.model.PageBean;
import com.xjc.service.CommentService;
import com.xjc.util.PageUtils;

@Controller
@RequestMapping("/comment")
public class CommentAdminController {

	@Resource
	CommentService commentService;
	
	@RequestMapping("/list")
	public String list(String page, String firstDate,String secondDate, String userName, 
			Boolean isPass,Model model, HttpServletRequest request) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		Integer currentPage = StringUtil.isEmpty(page) ? 1 : Integer.parseInt(page);
		PageBean pageBean = new PageBean(currentPage, 10);
		int pageSize = pageBean.getPageSize();
		map.put("firstDate", firstDate);
		map.put("secondDate", secondDate);
		map.put("userName", userName);
		map.put("isPass", isPass);
		int totalCount = commentService.getCommentList(map).size();
		pageBean.setTotalCount(totalCount);
		
		map.put("start", pageBean.getStart());
		map.put("size", pageSize);
		List<Comment> commentList = commentService.getCommentList(map);
		for(Comment comment : commentList){
			String content = comment.getContent();
			if (content.length() > 60) {
				comment.setContent(content.substring(0,60)+"...");
			}
		}
		model.addAttribute("pagination", pageBean);
		
		StringBuffer param=new StringBuffer(); // 分页查询参数
		param.append(StringUtil.isEmpty(firstDate) ? "" : "&firstDate=" + firstDate);
		param.append(StringUtil.isEmpty(secondDate) ? "" : "&secondDate=" + secondDate);
		param.append(StringUtil.isEmpty(userName) ? "" : "&userName=" + userName);
		param.append(isPass == null ? "" : "&isPass=" + isPass);
		
		String targetUrl = request.getContextPath() + "/comment/list.do";
		String pageCode = PageUtils.genPagination(targetUrl, totalCount, currentPage, pageSize, param.toString());
		model.addAttribute("pageCode",pageCode);
		model.addAttribute("entry",map);
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
	@RequestMapping("/review")
	public String review(Comment comment) {
		commentService.review(comment);
		return "redirect:/comment/list.do";
	}
	
	@RequestMapping("/delete")
	public String delete(Integer id){
		commentService.delete(id);
		return "redirect:/comment/list.do";
	}
	
	@RequestMapping("/deletes")
	public String deletes(String ids){
		String[] commentIds = ids.split(",");
		for(String id : commentIds){
			commentService.delete(Integer.parseInt(id));
		}
		return "redirect:/comment/list.do";
	}
}
