package com.june.web.controller.front;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.june.exception.ServiceException;
import com.june.model.Blog;
import com.june.model.BlogType;
import com.june.service.BlogService;
import com.june.service.BlogTypeService;
import com.june.service.CommentService;
import com.june.util.CommonUtils;
import com.june.util.Constants;
import com.june.util.ObjectUtils;
import com.june.util.PageUtils;
import com.june.util.StringUtils;
import com.june.vo.PageBean;

/**
 * 博客Controller
 */
@Controller
@RequestMapping("/blog")
public class BlogController {

    @Resource
    private BlogService blogService;

    @Resource
    private CommentService commentService;

    @Resource
    private BlogTypeService blogTypeService;

    /**
     * 获取博客详细信息
     * @param id
     * @param model
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    @GetMapping("/articles/{id}")
    public String detail(@PathVariable Integer id, Model model,
                         HttpServletRequest request) throws UnsupportedEncodingException {
        Blog blog = blogService.findById(id);
        if (ObjectUtils.isNull(blog)) {
            return "blog/detail";
        }
        Map<String, Object> map = new HashMap<>(2);
        map.put("blogId", id);
        map.put("isPass", 1);
        // 获取评论列表
        model.addAttribute("commentList", commentService.getCommentList(map));
        // 获取上一篇、下一篇博客
        model.addAttribute("prev", blogService.getPrevBlog(id));
        model.addAttribute("next", blogService.getNextBlog(id));
        // 文章浏览数加一
        blog = blogService.increaseReading(id);
        String desc = StringUtils.escapeHtml(blog.getSummary());
        model.addAttribute("blog", blog);
        model.addAttribute("description", desc.length() > 100 ? desc.substring(0, 100) : desc);
        String keyword = blog.getKeyword();
        if (StringUtils.isNotBlank(keyword)) {
            String[] keywords = keyword.split("\\s+");
            int length = keywords.length;
            Map<String, String> keywordsMap = new HashMap<>(length);
            boolean isIEBrowser = CommonUtils.isIEBrowser(request);
            for (int i = 0; i < length; i++) {
                String kwd = keywords[i];
                if (StringUtils.isNotEmpty(kwd)) {
                    String value = kwd;
                    if (isIEBrowser) {
                        // 进行URL编码，防止IE浏览器不支持中文请求参数，报400错误
                        value = URLEncoder.encode(kwd, StandardCharsets.UTF_8.name());
                    }
                    keywordsMap.put(kwd, value);
                }
            }
            model.addAttribute("keywords", keywordsMap);
            model.addAttribute("pageKeywords", String.join(",", keywords));
        }
        return "blog/detail";
    }

    /**
     * 获取分类博客列表
     * @param id
     * @param model
     * @param request
     * @return
     */
    @GetMapping
    public String typeOrMonth(@RequestParam(required = false) Integer cat,
                              @RequestParam(required = false) Integer month,
                              @RequestParam(value = "size",
                                  defaultValue = Constants.FRONT_PAGE_SIZE + "") Integer pageSize,
                              @RequestParam(defaultValue = "1") Integer page, Model model,
                              HttpServletRequest request) {
        if ((month == null && cat == null) || (month != null && cat != null)) {
            throw new ServiceException("请求参数错误");
        }
        Map<String, Object> paramMap = new HashMap<>(4);
        int totalCount = blogService.getCount(paramMap);
        PageBean pageBean = new PageBean(totalCount, page, pageSize);
        paramMap.put("typeId", cat);
        paramMap.put("releaseDate", month);
        paramMap.put("start", pageBean.getStart());
        paramMap.put("size", pageSize);
        List<Blog> blogList = blogService.getBlogList(paramMap);
        // 转义摘要中的html标签，防止浏览器解析
        blogList.forEach(blog -> blog.setSummary(StringEscapeUtils.escapeHtml4(blog.getSummary())));
        model.addAttribute("blogList", blogList);
        model.addAttribute("totalCount", totalCount);

        // 分页参数
        StringBuilder param = new StringBuilder();
        param.append(cat == null ? "" : "cat=" + cat);
        param.append(month == null ? "" : "month=" + month);
        String pageCode = PageUtils.genPagination(request.getContextPath() + "/blog.shtml", pageBean,
                param.toString());
        model.addAttribute("pageCode", pageCode);
        if (cat != null) {
            BlogType blogType = blogTypeService.findById(cat);
            if (blogType != null) {
                String typeName = blogType.getTypeName();
                model.addAttribute("title", "文章分类 - " + typeName);
                model.addAttribute("pageTitle", typeName + " - 文章分类 - hayuq的博客");
            }
        } else if (month != null) {
            String monthStr = month + "";
            monthStr = String.format("%s年%s月", monthStr.substring(0, 4), monthStr.substring(4));
            model.addAttribute("title", "文章存档 - " + monthStr);
            model.addAttribute("pageTitle", monthStr + " - 文章存档 - hayuq的博客");
        }
        return "blog/list";
    }

}
