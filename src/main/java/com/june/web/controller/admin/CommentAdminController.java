package com.june.web.controller.admin;

import static com.june.vo.Response.success;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

import com.june.model.Comment;
import com.june.service.BlogService;
import com.june.service.CommentService;
import com.june.util.Constants;
import com.june.util.PageUtils;
import com.june.util.StringUtils;
import com.june.vo.PageBean;
import com.june.vo.Response;

@Controller
@RequestMapping("/comment")
public class CommentAdminController {

    @Resource
    private CommentService commentService;

    @Resource
    private BlogService blogService;

    @GetMapping("/list")
    public String list(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(name = "size", defaultValue = Constants.BACK_PAGE_SIZE + 1 + "") Integer pageSize,
                       String firstDate, String secondDate, String userName, Boolean isPass, Model model,
                       HttpServletRequest request) {

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
            comment.setContent(org.apache.commons.lang3.StringUtils.abbreviate(comment.getContent(), 63));
        });
        model.addAttribute("pagination", pageBean);
        StringBuilder param = new StringBuilder(); // 分页查询参数
        param.append(StringUtils.isEmpty(firstDate) ? "" : "&firstDate=" + firstDate);
        param.append(StringUtils.isEmpty(secondDate) ? "" : "&secondDate=" + secondDate);
        param.append(StringUtils.isEmpty(userName) ? "" : "&userName=" + userName);
        param.append(isPass == null ? "" : "&isPass=" + isPass);

        String pageCode = PageUtils.genPagination(request.getContextPath() + "/comment/list.do", pageBean,
                param.toString());
        model.addAttribute("pageCode", pageCode);
        model.addAttribute("entry", params);
        model.addAttribute("commentList", commentList);
        return "comment/list";
    }

    @GetMapping("/detail")
    public String detail(@RequestParam Integer id, Model model) {
        model.addAttribute("comment", commentService.findById(id));
        return "comment/detail";
    }

    // 评论审核
    @PostMapping("/audit")
    public @ResponseBody Response audit(@RequestParam Comment comment) {
        comment.setReplyDate(new Date());
        commentService.audit(comment);
        return success();
    }

    @PostMapping("/delete")
    public @ResponseBody Response delete(@RequestParam Integer id) {
        commentService.delete(id);
        return success();
    }

    @PostMapping("/deletes")
    public @ResponseBody Response deletes(@RequestBody Integer[] ids) {
        commentService.batchDelete(ids);
        return success();
    }

}
