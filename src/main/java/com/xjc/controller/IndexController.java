package com.xjc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.opensymphony.oscache.util.StringUtil;
import com.xjc.lucene.BlogIndex;
import com.xjc.model.Blog;
import com.xjc.model.BlogType;
import com.xjc.model.PageBean;
import com.xjc.service.BlogService;
import com.xjc.service.BlogTypeService;
import com.xjc.service.BloggerService;
import com.xjc.service.LinkService;
import com.xjc.util.PageUtils;

/**
 * 主页Controller
 */
@Controller
@RequestMapping("/")
public class IndexController {

	@Resource
	BlogService blogService;
	
	@Resource
	BloggerService bloggerService;
	
	@Resource
	BlogTypeService blogTypeService;
	
	@Resource
	LinkService linkService;
	
	@RequestMapping("/index")
	public String index(String page,@RequestParam(value="type",required=false)String typeId, String releaseDate,
			Model model,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		int currentPage = Integer.parseInt(StringUtil.isEmpty(page) ? "1" : page);
		PageBean pageBean=new PageBean(currentPage,8);
		int totalCount = blogService.getBlogList(map).size();
		pageBean.setTotalCount(totalCount);
		int pageSize = pageBean.getPageSize();
		map.put("start", pageBean.getStart());
		map.put("size", pageSize);
		map.put("typeId", typeId);
		map.put("releaseDate", releaseDate);
		List<Blog> blogList = blogService.getBlogList(map);
		for(Blog blog : blogList){
			blog.setSummary(StringEscapeUtils.escapeHtml4(blog.getSummary()));
		}
		model.addAttribute("blogList", blogList);
		
		//分页参数
		StringBuffer param = new StringBuffer();
		param.append(StringUtil.isEmpty(typeId) ? "" : "&typeId="+typeId);
		param.append(StringUtil.isEmpty(releaseDate) ? "" : "&releaseDate="+releaseDate);
		
		String pageCode = PageUtils.genPagination(request.getContextPath()+"/index.shtml", totalCount, currentPage, pageSize, param.toString());
		model.addAttribute("pageCode",pageCode);
		model.addAttribute("title", "文章列表");
		
		ServletContext application = request.getServletContext();
		List<BlogType> blogTypeList = blogTypeService.getTypeList();
		for(BlogType blogType : blogTypeList){
			blogType.setBlogCount(blogService.getByTypeId(blogType.getTypeId()).size());
		}
		application.setAttribute("blogTypeList", blogTypeList);
		application.setAttribute("dateRankList", blogService.getByDate());
		application.setAttribute("readingRankList", blogService.getTopReading());
		application.setAttribute("reviewRankList", blogService.getTopReview());
		application.setAttribute("blogger", bloggerService.findById(1));
		application.setAttribute("linkList", linkService.getLinkList());
		return "blog/list";
	}
	
	/**
	 * 根据关键词查询博客
	 * @throws Exception
	 */
	@RequestMapping("/search")
	public String search(String page,String q, Model model,HttpServletRequest request) throws Exception{
		
		int currentPage = Integer.parseInt(StringUtil.isEmpty(page) ? "1" : page);
		BlogIndex blogIndex = new BlogIndex();
		System.out.println("======================blogIndex："+blogIndex);
		request.setCharacterEncoding("utf-8");
		System.out.println("=========================关键字："+q);
		List<Blog> blogList = blogIndex.query(q);
		model.addAttribute("q", q);
		model.addAttribute("blogList", blogList);
		int totalCount = blogList.size();
		model.addAttribute("resultCount",totalCount);
		String pageCode = PageUtils.genPagination(request.getContextPath()+"/search.shtml", 
				totalCount, currentPage, 6, StringUtil.isEmpty(q) ? "" : "q="+q);
		model.addAttribute("pageCode",pageCode);
		return "blog/result";
	}
}
