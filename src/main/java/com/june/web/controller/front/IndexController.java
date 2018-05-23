package com.june.web.controller.front;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.june.lucene.BlogIndex;
import com.june.model.Blog;
import com.june.model.BlogType;
import com.june.model.PageBean;
import com.june.service.BlogService;
import com.june.service.BlogTypeService;
import com.june.service.BloggerService;
import com.june.service.LinkService;
import com.june.util.Constants;
import com.june.util.PageUtils;
import com.june.util.StringUtils;

/**
 * 主页Controller
 */
@Controller
public class IndexController {

	@Resource
	private BlogService blogService;
	
	@Resource
	private BloggerService bloggerService;
	
	@Resource
	private BlogTypeService blogTypeService;
	
	@Resource
	private LinkService linkService;
	
	@Resource
	private BlogIndex blogIndex;

	@RequestMapping("/index")
	public String index(@RequestParam(defaultValue = "1") Integer page,
			Model model, HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>(2);
		int totalCount = blogService.getCount(map);
		int pageSize = Constants.FRONT_PAGE_SIZE;
		PageBean pageBean = new PageBean(totalCount, page, pageSize);
		map.put("start", pageBean.getStart());
		map.put("size", pageSize);
		List<Blog> blogList = blogService.getBlogList(map);
		// 去除摘要中的html标签，防止浏览器解析
		blogList.forEach(blog -> blog.setSummary(StringUtils.escapeHtml(blog.getSummary())));
		model.addAttribute("blogList", blogList);

		String pageCode = PageUtils.genPagination(request.getContextPath() + "/index.shtml", pageBean, "");
		model.addAttribute("pageCode", pageCode);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("title", "我的文章");
		ServletContext application = request.getServletContext();
		List<BlogType> blogTypeList = blogTypeService.getTypeList();
		blogTypeList.forEach(blogType -> {
			Map<String, Object> paramMap = new HashMap<String, Object>(1);
			paramMap.put("typeId", blogType.getTypeId());
			blogType.setBlogCount(blogService.getCount(paramMap));
		});
		
		application.setAttribute("blogTypeList", blogTypeList);
		application.setAttribute("dateRankList", blogService.getByDate());
		application.setAttribute("readingRankList", blogService.getTopReading());
		application.setAttribute("reviewRankList", blogService.getTopReview());
		application.setAttribute("blogger", bloggerService.find());
		application.setAttribute("linkList", linkService.getLinkList());

		application.setAttribute("pageTitle", Constants.DEFAULT_TITLE);
		application.setAttribute("pageKeywords", Constants.DEFAULT_KEYWORDS);
		application.setAttribute("description", Constants.DEFAULT_DESCRIPTION);
		return "blog/list";
	}

	@RequestMapping("/{category}")
	public String life(@PathVariable String category, @RequestParam(defaultValue = "1") Integer page,
			Model model, HttpServletRequest request)
			throws Exception {
		String typeName = "";
		switch (category) {
			case Constants.LIFE_CATEGORY_EN:
				typeName = Constants.LIFE_CATEGORY; break;
			case Constants.NEWS_CATEGORY_EN:
				typeName = Constants.NEWS_CATEGORY; break;
			default: break;
		}
		Integer typeId = blogTypeService.getIdByName(typeName);
		Map<String, Object> map = new HashMap<String, Object>(3);
		map.put("typeId", typeId);
		int totalCount = blogService.getCount(map);
		int pageSize = Constants.FRONT_PAGE_SIZE;
		PageBean pageBean = new PageBean(totalCount, page, pageSize);
		pageBean.setTotalCount(totalCount);
		map.put("start", pageBean.getStart());
		map.put("size", pageSize);

		List<Blog> blogList = blogService.getBlogList(map);
		// 去除摘要中的html标签，防止浏览器解析
		blogList.forEach(blog -> blog.setSummary(StringUtils.escapeHtml(blog.getSummary())));
		model.addAttribute("blogList", blogList);
		String pageCode = PageUtils.genPagination(request.getContextPath() + "/" + category + ".shtml", 
				pageBean, "");
		model.addAttribute("pageCode", pageCode);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("pageTitle", typeName + " - 文章分类 - hayuq的博客");
		model.addAttribute("title", typeName);
		return "blog/list";
	}

	/**
	 * 根据关键词查询博客
	 * @throws Exception
	 */
	@RequestMapping("/search")
	public String search(@RequestParam(defaultValue = "1") Integer page, String q, 
			Model model, HttpServletRequest request) throws Exception {
		if ("POST".equals(request.getMethod())) { // 判断是否是表单提交，post方式
			// 解决表单提交中文乱码
			q = new String(q.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8.name());
		}
		if(q != null) {			
			// URL解码，防止特殊字符
			q = URLDecoder.decode(q, StandardCharsets.UTF_8.name());
		}
		// 转义特殊字符，防止lucene报异常
		String kwd = QueryParser.escape(q);
		List<Blog> blogList = blogIndex.query(kwd);
		// 分页显示
		int totalCount = blogList.size();
		int pageSize = Constants.DEFAULT_PAGE_SIZE;
		PageBean pageBean = new PageBean(totalCount, page, pageSize);
		int fromIndex = pageBean.getStart();
		int toIndex = Math.min(totalCount, fromIndex + pageSize);
		model.addAttribute("blogList", blogList.subList(fromIndex, toIndex));
		model.addAttribute("resultCount", totalCount);
		String pageCode = PageUtils.genPagination(request.getContextPath() + "/search.shtml", 
				pageBean, StringUtils.isEmpty(q) ? "" : "q=" + q);
		model.addAttribute("pageCode", pageCode);
		q = StringEscapeUtils.escapeHtml4(q);
		model.addAttribute("q", q);
		model.addAttribute("pageKeywords", q + "," + Constants.DEFAULT_KEYWORDS);
		return "blog/result";
	}

}
