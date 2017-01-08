package com.xjc.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.opensymphony.oscache.util.StringUtil;
import com.xjc.model.Blog;
import com.xjc.model.PageBean;
import com.xjc.service.BlogService;
import com.xjc.service.BlogTypeService;
import com.xjc.service.CommentService;
import com.xjc.util.Constants;
import com.xjc.util.PageUtils;
import com.xjc.util.StringUtils;

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

	@Resource
	BlogTypeService blogTypeService;
	
	/**
	 * 获取博客详细信息
	 * @param id
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/articles/{id}")
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
		model.addAttribute("pageCode", this.genLastandNextBlogCode(blogService.getLastBlog(id),
				blogService.getNextBlog(id),request.getServletContext().getContextPath()));
		return "blog/detail";
	}
	
	/**
	 * 获取分类博客列表
	 * @param id
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping
	public String typeOrMonth(@RequestParam(value="type",required=false) String typeName,
			@RequestParam(required=false) String month,
			String page, Model model,HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		int currentPage = Integer.parseInt(StringUtil.isEmpty(page) ? "1" : page);
		PageBean pageBean=new PageBean(currentPage,Constants.FRONT_PAGE_SIZE);
		Integer id = blogTypeService.getIdByName(typeName);
		//获取不带条件时分页的数据总条数
		int pageSize = pageBean.getPageSize();
		map.put("typeId", id);
		map.put("releaseDate", month);
		List<Blog> blogList = blogService.getBlogList(map);
		int totalCount = blogList.size();
		map.put("start", pageBean.getStart());
		map.put("size", pageSize);
		pageBean.setTotalCount(totalCount);
		blogList = blogService.getBlogList(map);
		//去除摘要中的html标签，防止浏览器解析
		blogList.forEach(blog -> blog.setSummary(StringUtils.escapeHtml(blog.getSummary())));
		model.addAttribute("blogList", blogList);

		new Thread(() -> System.out.println("run...")).start();
		// 分页参数
		StringBuffer param = new StringBuffer();
		param.append(StringUtil.isEmpty(typeName) ? "" : "type=" + typeName);
		param.append(StringUtil.isEmpty(month) ? "" : "month=" + month);

		String pageCode = PageUtils.genPagination(request.getContextPath() + "/blog.shtml", totalCount, currentPage,
				pageSize, param.toString());
		model.addAttribute("pageCode", pageCode);
		model.addAttribute("totalCount", totalCount);
		if (!StringUtil.isEmpty(typeName)) {			
			model.addAttribute("title", "文章分类 - " + typeName);
		}
		if (!StringUtil.isEmpty(month)) {
			model.addAttribute("title", "文章存档 - " + month);
		}
		return "blog/list";
	}
	
	/**
	 * 获取下一篇博客和下一篇博客代码
	 * @param lastBlog
	 * @param nextBlog
	 * @return
	 */
	private String genLastandNextBlogCode(Blog lastBlog,Blog nextBlog,String projectContext){
		StringBuffer pageCode=new StringBuffer();
		if(lastBlog==null || lastBlog.getId()==null){
			pageCode.append("<p>上一篇：没有了</p>");
		}else{
			pageCode.append("<p>上一篇：<a href='"+projectContext+"/blog/articles/"+lastBlog.getId()+".shtml'>"+lastBlog.getTitle()+"</a></p>");
		}
		if(nextBlog==null || nextBlog.getId()==null){
			pageCode.append("<p>下一篇：没有了</p>");
		}else{
			pageCode.append("<p>下一篇：<a href='"+projectContext+"/blog/articles/"+nextBlog.getId()+".shtml'>"+nextBlog.getTitle()+"</a></p>");
		}
		return pageCode.toString();
	}
	
}
