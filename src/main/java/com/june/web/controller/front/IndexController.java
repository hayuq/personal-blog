package com.june.web.controller.front;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.june.model.Blog;
import com.june.model.BlogType;
import com.june.search.BlogIndexManager;
import com.june.service.BlogService;
import com.june.service.BlogTypeService;
import com.june.service.BloggerService;
import com.june.service.LinkService;
import com.june.util.Constants;
import com.june.util.PageUtils;
import com.june.util.StringUtils;
import com.june.vo.PageBean;
import com.june.vo.Response;
import com.june.vo.ResponseStatus;

import lombok.extern.slf4j.Slf4j;

/**
 * 主页Controller
 */
@Controller
public @Slf4j class IndexController {

    @Resource
    private BlogService blogService;

    @Resource
    private BloggerService bloggerService;

    @Resource
    private BlogTypeService blogTypeService;

    @Resource
    private LinkService linkService;

    @Resource
    private BlogIndexManager blogIndex;

    @GetMapping("/index")
    public String index(@RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(name = "size",
                            defaultValue = Constants.FRONT_PAGE_SIZE + "") Integer pageSize,
                        Model model, HttpServletRequest request) {
        Map<String, Object> paramMap = new HashMap<>(2);
        int totalCount = blogService.getCount(paramMap);
        PageBean pageBean = new PageBean(totalCount, page, pageSize);
        paramMap.put("start", pageBean.getStart());
        paramMap.put("size", pageSize);
        List<Blog> blogList = blogService.getBlogList(paramMap);
        // 转义摘要中的html标签，防止浏览器解析
        blogList.forEach(blog -> blog.setSummary(StringEscapeUtils.escapeHtml4(blog.getSummary())));
        model.addAttribute("blogList", blogList);

        String pageCode = PageUtils.genPagination(request.getContextPath() + "/index.shtml", pageBean, null);
        model.addAttribute("pageCode", pageCode);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("title", "我的文章");

        List<BlogType> blogTypeList = blogTypeService.getTypeList();
        blogTypeList.forEach(blogType -> {
            Map<String, Object> map = Collections.singletonMap("typeId", blogType.getTypeId());
            blogType.setBlogCount(blogService.getCount(map));
        });

        ServletContext application = request.getServletContext();
        application.setAttribute("blogTypeList", blogTypeList);
        application.setAttribute("dateRankList", blogService.getByDate());
        application.setAttribute("readingRankList", blogService.getTopReading());
        application.setAttribute("reviewRankList", blogService.getTopReview());
        application.setAttribute("blogger", bloggerService.find());
        application.setAttribute("linkList", linkService.getLinkList());

        return "blog/list";
    }

    @GetMapping("/{category}")
    public String category(@PathVariable String category, @RequestParam(defaultValue = "1") Integer page,
                           @RequestParam(name = "size",
                               defaultValue = Constants.FRONT_PAGE_SIZE + "") Integer pageSize,
                           Model model, HttpServletRequest request, HttpServletResponse response,
                           HttpSession session) throws IOException {
        String typeName = null;
        switch (category) {
            case Constants.LIFE_CATEGORY_EN:
                typeName = Constants.LIFE_CATEGORY_CN;
                break;
            case Constants.NEWS_CATEGORY_EN:
                typeName = Constants.NEWS_CATEGORY_CN;
                break;
            default:
                if (IndexController.log.isDebugEnabled()) {
                    IndexController.log.debug("类别不存在，返回404");
                }
                // 返回404
                Response error = Response.error(ResponseStatus.NOT_FOUND).append("url",
                        request.getRequestURI());
                model.addAttribute("error", error);
                return "error";
        }
        Integer typeId = blogTypeService.getIdByName(typeName);
        Map<String, Object> paramMap = new HashMap<>(3);
        paramMap.put("typeId", typeId);
        int totalCount = blogService.getCount(paramMap);
        PageBean pageBean = new PageBean(totalCount, page, pageSize);
        paramMap.put("start", pageBean.getStart());
        paramMap.put("size", pageSize);

        List<Blog> blogList = blogService.getBlogList(paramMap);
        // 去除摘要中的html标签，防止浏览器解析
        blogList.forEach(blog -> blog.setSummary(StringEscapeUtils.escapeHtml4(blog.getSummary())));
        String pageCode = PageUtils.genPagination(request.getContextPath() + "/" + category + ".shtml",
                pageBean, null);
        model.addAttribute("blogList", blogList);
        model.addAttribute("pageCode", pageCode);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("title", "文章分类 - " + typeName);
        model.addAttribute("pageTitle", typeName + " - 文章分类 - hayuq的博客");
        return "blog/list";
    }

    /**
     * 根据关键词查询博客
     * @throws Exception
     */
    @GetMapping("/search")
    public String search(@RequestParam(defaultValue = "1") Integer page, @RequestParam String q,
                         @RequestParam(name = "size",
                             defaultValue = Constants.DEFAULT_PAGE_SIZE + "") Integer pageSize,
                         Model model, HttpServletRequest request) throws Exception {

        // 根据关键字搜索博客
        List<Blog> blogs = blogService.search(q, blogService.getCount(null));
        int totalCount = blogs.size();
        PageBean pageBean = new PageBean(totalCount, page, pageSize);
        List<Blog> blogList = Collections.emptyList();
        int fromIndex = pageBean.getStart();

        // bug fixed (IndexOutOfBoundsException：fromIndex > toIndex)
        if (fromIndex <= totalCount) {
            int toIndex = Math.min(totalCount, fromIndex + pageSize);
            blogList = blogs.subList(fromIndex, toIndex);
        }
        model.addAttribute("blogList", blogList);
        model.addAttribute("resultCount", totalCount);
        String pageCode = PageUtils.genPagination(request.getContextPath() + "/search.shtml", pageBean,
                StringUtils.isEmpty(q) ? "" : "q=" + q);
        model.addAttribute("pageCode", pageCode);
        q = StringEscapeUtils.escapeHtml4(q);
        model.addAttribute("q", q);
        model.addAttribute("pageKeywords", q + "," + Constants.DEFAULT_KEYWORDS);
        return "blog/result";
    }

}
