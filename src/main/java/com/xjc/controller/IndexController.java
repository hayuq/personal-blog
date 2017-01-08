package com.xjc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.opensymphony.oscache.util.StringUtil;
import com.xjc.lucene.BlogIndex;
import com.xjc.model.Blog;
import com.xjc.model.BlogType;
import com.xjc.model.PageBean;
import com.xjc.service.BlogService;
import com.xjc.service.BlogTypeService;
import com.xjc.service.BloggerService;
import com.xjc.service.LinkService;
import com.xjc.util.Constants;
import com.xjc.util.PageUtils;
import com.xjc.util.StringUtils;

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
	public String index(String page, Model model, HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int currentPage = Integer.parseInt(StringUtil.isEmpty(page) ? "1" : page);
		PageBean pageBean=new PageBean(currentPage,Constants.FRONT_PAGE_SIZE);
		
		int totalCount = 0;
		int pageSize = pageBean.getPageSize();
		List<Blog> blogList = blogService.getBlogList(map);
		totalCount = blogList.size();
		map.put("start", pageBean.getStart());
		map.put("size", pageSize);
		pageBean.setTotalCount(totalCount);
		blogList = blogService.getBlogList(map);
		//去除摘要中的html标签，防止浏览器解析
		blogList.forEach(blog -> blog.setSummary(StringUtils.escapeHtml(blog.getSummary())));
		model.addAttribute("blogList", blogList);

		String pageCode = PageUtils.genPagination(request.getContextPath() + "/index.shtml", totalCount, currentPage,
				pageSize, "");
		model.addAttribute("pageCode", pageCode);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("title", "我的文章");
		ServletContext application = request.getServletContext();
		List<BlogType> blogTypeList = blogTypeService.getTypeList();
		blogTypeList.forEach(blogType -> blogType.setBlogCount(blogService.getByTypeId(blogType.getTypeId()).size()));
		application.setAttribute("blogTypeList", blogTypeList);
		application.setAttribute("dateRankList", blogService.getByDate());
		application.setAttribute("readingRankList", blogService.getTopReading());
		application.setAttribute("reviewRankList", blogService.getTopReview());
		application.setAttribute("blogger", bloggerService.find());
		application.setAttribute("linkList", linkService.getLinkList());
		return "blog/list";
	}
	
	@RequestMapping("/life")
	public String life(String page,Model model,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		int currentPage = Integer.parseInt(StringUtil.isEmpty(page) ? "1" : page);
		PageBean pageBean=new PageBean(currentPage,Constants.FRONT_PAGE_SIZE);
		
		String typeName = Constants.LIFE_NAME;
		Integer typeId = blogTypeService.getIdByName(typeName);
		map.put("typeId", typeId);
		int pageSize = pageBean.getPageSize();
		map.put("start", pageBean.getStart());
		map.put("size", pageSize);
		
		List<Blog> blogList = blogService.getBlogList(map);
		int totalCount = blogList.size();
		pageBean.setTotalCount(totalCount);
		
		// 去除摘要中的html标签，防止浏览器解析
		blogList.forEach(blog -> blog.setSummary(StringUtils.escapeHtml(blog.getSummary())));
		model.addAttribute("blogList", blogList);
		String pageCode = PageUtils.genPagination(request.getContextPath() + "/life.shtml", totalCount, currentPage,
				pageSize, "");
		model.addAttribute("pageCode", pageCode);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("title", "文章分类 - "+ typeName);
		return "blog/list";
	}
	
	/**
	 * 根据关键词查询博客
	 * @throws Exception
	 */
	@RequestMapping("/search")
	public String search(String page,String q, Model model,HttpServletRequest request) throws Exception{
		int currentPage = Integer.parseInt(StringUtil.isEmpty(page) ? "1" : page);
		int pageSize = Constants.DEFAULT_PAGE_SIZE;
		BlogIndex blogIndex = new BlogIndex();
		if (request.getMethod().equals("POST")) {// 判断是否是表单提交，post方式
			// 解决表单提交中文乱码
			q = new String(q.getBytes("ISO-8859-1"), "utf-8");
		}
		 
		//转义特殊字符，防止lucene报异常
		StringBuffer sb = new StringBuffer();
		String regex = "[+\\-&|!(){}\\[\\]^\"~*?:(\\)(AND)(OR)(NOT)]";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(q);
		while (matcher.find()) {
			matcher.appendReplacement(sb, "\\\\" + matcher.group());
		}
		matcher.appendTail(sb);
	        
		List<Blog> blogList = blogIndex.query(sb.toString());
		model.addAttribute("q", q);		
		int totalCount = blogList.size();
		//分页显示
		Integer toIndex = totalCount >= currentPage * pageSize ? currentPage * pageSize : totalCount;
		model.addAttribute("blogList", blogList.subList((currentPage - 1) * pageSize, toIndex));
		model.addAttribute("resultCount",totalCount);
		String pageCode = PageUtils.genPagination(request.getContextPath()+"/search.shtml", 
				totalCount, currentPage, pageSize, StringUtil.isEmpty(q) ? "" : "q="+q);
		model.addAttribute("pageCode",pageCode);
		return "blog/result";
	}
	
}
