package com.june.web.controller.front;

import static com.june.vo.Response.error;
import static com.june.vo.Response.success;

import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.june.dao.UserMapper;
import com.june.model.Comment;
import com.june.model.User;
import com.june.service.CommentService;
import com.june.util.Constants;
import com.june.util.StringUtils;
import com.june.vo.Response;

@Controller
@RequestMapping("/comment")
public class CommentController {

    private static final String EMAIL_REGEX = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$";

    @Resource
    private UserMapper userMapper;

    @Resource
    private CommentService commentService;

    /**
     * 用户评论
     * @param comment
     * @param user
     * @param response
     */
    @PostMapping("/save")
    public @ResponseBody Response save(@RequestParam String vCode, @RequestParam Integer blogId, Comment comment,
                                       User user, HttpSession session) {
        String validateCode = (String) session.getAttribute(Constants.VALIDATE_CODE_KEY);
        // 删除Session中的验证码
        session.removeAttribute(Constants.VALIDATE_CODE_KEY);
        if (StringUtils.isBlank(comment.getContent())) {
            return error("评论内容不能为空");
        }
        if (!Pattern.matches(CommentController.EMAIL_REGEX, user.getEmail())) {
            return error("邮箱格式不正确");
        }
        if (StringUtils.isBlank(user.getUserName())) {
            return error("昵称不能为空");
        }
        if (StringUtils.isEmpty(validateCode)) {
            return error("验证码已失效");
        }
        if (!validateCode.equalsIgnoreCase(vCode)) {
            return error("验证码错误");
        }
        Integer userId = user.getId();
        if (userId == null) {
            user.setWebsite(StringUtils.escapeHtml(user.getWebsite()));
            userId = userMapper.insertSelective(user);
        }
        comment.setBlogId(blogId);
        comment.setUserId(userId);
        comment.setUserName(StringEscapeUtils.escapeHtml4(user.getUserName()));
        comment.setContent(StringEscapeUtils.escapeHtml4(comment.getContent()));
        commentService.add(comment);
        return success();
    }

}
