package com.xjc.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xjc.model.Blog;
import com.xjc.service.BlogService;
import com.xjc.service.CommentService;

/**
 * 博客Controller
 */
@Controller
@RequestMapping("/blog")
public class BlogController {

	@Resource
	BlogService blogService;
	
	@Resource
	CommentService commentService;
	
	/**
	 * 获取博客详细信息
	 * @param id
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/article/{id}")
	public String detail(@PathVariable Integer id,Model model,HttpServletRequest request){
		
		Blog blog = blogService.findById(id);
		blog.setReading(blog.getReading()+1); //浏览数加一
		model.addAttribute("blog", blog);
		blogService.update(blog);
		if(blog.getKeyword() != null){
			String keywords[] = blog.getKeyword().split(" ");
			model.addAttribute("keywords",Arrays.asList(keywords));
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("blogId", id);
		map.put("isPass", 1);
		//获取评论列表
		model.addAttribute("commentList", commentService.getCommentList(map));
		model.addAttribute("pageCode", this.genUpAndDownPageCode(blogService.getLastBlog(id),
				blogService.getNextBlog(id),request.getServletContext().getContextPath()));
		return "blog/detail";
	}
	
	/**
	 * 获取下一篇博客和下一篇博客代码
	 * @param lastBlog
	 * @param nextBlog
	 * @return
	 */
	private String genUpAndDownPageCode(Blog lastBlog,Blog nextBlog,String projectContext){
		StringBuffer pageCode=new StringBuffer();
		if(lastBlog==null || lastBlog.getId()==null){
			pageCode.append("<p>上一篇：没有了</p>");
		}else{
			pageCode.append("<p>上一篇：<a href='"+projectContext+"/blog/article/"+lastBlog.getId()+".shtml'>"+lastBlog.getTitle()+"</a></p>");
		}
		if(nextBlog==null || nextBlog.getId()==null){
			pageCode.append("<p>下一篇：没有了</p>");
		}else{
			pageCode.append("<p>下一篇：<a href='"+projectContext+"/blog/article/"+nextBlog.getId()+".shtml'>"+nextBlog.getTitle()+"</a></p>");
		}
		return pageCode.toString();
	}
	
}
