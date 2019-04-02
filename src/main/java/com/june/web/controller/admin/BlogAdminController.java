package com.june.web.controller.admin;

import static com.june.vo.Response.success;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.june.model.Blog;
import com.june.model.BlogType;
import com.june.service.BlogService;
import com.june.service.BlogTypeService;
import com.june.service.CommentService;
import com.june.util.Constants;
import com.june.util.PageUtils;
import com.june.util.StringUtils;
import com.june.vo.PageBean;
import com.june.vo.Response;

/**
 * 博客Controller
 */
@Controller
@RequestMapping("/blog")
public class BlogAdminController {

    @Resource
    private BlogService blogService;

    @Resource
    private BlogTypeService blogTypeService;

    @Resource
    private CommentService commentService;

    @GetMapping("/list")
    public String list(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(name = "size",
                           defaultValue = Constants.DEFAULT_PAGE_SIZE - 1 + "") Integer pageSize,
                       String firstDate, String secondDate, Integer typeId, String title, Model model,
                       HttpServletRequest request) {

        Map<String, Object> map = new HashMap<>(6);
        map.put("typeId", typeId);
        map.put("title", title);
        map.put("firstDate", firstDate);
        map.put("secondDate", secondDate);
        int totalCount = blogService.getCount(map);
        PageBean pageBean = new PageBean(totalCount, page, pageSize);
        map.put("start", pageBean.getStart());
        map.put("size", pageSize);
        model.addAttribute("pagination", pageBean);
        model.addAttribute("blogTypeList", blogTypeService.getTypeList());

        StringBuilder param = new StringBuilder(); // 分页查询参数
        param.append(StringUtils.isEmpty(title) ? "" : "title=" + title);
        param.append(StringUtils.isEmpty(firstDate) ? "" : "&firstDate=" + firstDate);
        param.append(StringUtils.isEmpty(secondDate) ? "" : "&secondDate=" + secondDate);
        param.append(typeId == null ? "" : "&typeId=" + typeId);

        String pageCode = PageUtils.genPagination(request.getContextPath() + "/blog/list.do", pageBean,
                param.toString());
        model.addAttribute("pageCode", pageCode);
        model.addAttribute("entry", map);
        model.addAttribute("blogList", blogService.getBlogList(map));
        return "blog/list";
    }

    @GetMapping("/toAdd")
    public String toAdd(Model model) {
        model.addAttribute("blogTypeList", blogTypeService.getTypeList());
        return "blog/add";
    }

    @GetMapping("/toUpdate")
    public String toUpdate(@RequestParam Integer id, Model model) {
        Blog blog = blogService.findById(id);
        model.addAttribute("blog", blog);
        model.addAttribute("blogTypeList", blogTypeService.getTypeList());
        if (blog != null) {
            BlogType blogType = blog.getBlogType();
            if (blogType != null) {
                model.addAttribute("typeId", blogType.getTypeId());
            }
        }
        return "blog/update";
    }

    @PostMapping("/add")
    public void add(Blog blog, @RequestParam MultipartFile img, Model model) {
        boolean success = blogService.add(blog, img);
        model.addAttribute("msg", success ? "保存成功" : "保存失败");
    }

    @PostMapping("/update")
    public void update(Blog blog, MultipartFile img, Model model) {
        boolean success = blogService.update(blog, img);
        model.addAttribute("msg", success ? "保存成功" : "保存失败");
        // 数据回显
        blog = blogService.findById(blog.getId());
        model.addAttribute("blog", blog);
        model.addAttribute("blogTypeList", blogTypeService.getTypeList());
        BlogType blogType = blog.getBlogType();
        if (blogType != null) {
            model.addAttribute("typeId", blogType.getTypeId());
        }
    }

    @PostMapping("/delete")
    public @ResponseBody Response delete(@RequestParam Integer id) {
        // 删除博客、索引及评论
        blogService.delete(id);
        return success();
    }

    @PostMapping("/deletes")
    public @ResponseBody Response deletes(@RequestBody Integer[] ids) {
        blogService.batchDelete(ids);
        return success();
    }

}
