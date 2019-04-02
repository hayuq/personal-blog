package com.june.web.controller.admin;

import static com.june.vo.Response.error;
import static com.june.vo.Response.success;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.june.model.Blogger;
import com.june.service.BloggerService;
import com.june.service.CommentService;
import com.june.util.CommonUtils;
import com.june.util.Constants;
import com.june.vo.Response;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/blogger")
public @Slf4j class BloggerAdminController {

    @Resource
    private CommentService commentService;

    @Resource
    private BloggerService bloggerService;

    @GetMapping("/toModifyInfo")
    public String toModifyInfo(Model model) {
        model.addAttribute("blogger", bloggerService.find());
        return "blogger/modifyInfo";
    }

    @GetMapping("/toModifyPassword")
    public String toModifyPassword() {
        return "blogger/modifyPassword";
    }

    @PostMapping("/modifyInfo")
    public void modifyInfo(@RequestParam Blogger blogger, MultipartFile img, Model model) {
        boolean success = bloggerService.update(blogger, img);
        model.addAttribute("msg", success ? "修改成功" : "修改失败");
    }

    @PostMapping("/modifyPassword")
    public @ResponseBody Response modifyPassword(@RequestParam String newpwd, @RequestParam String oldpwd,
                                                 @RequestParam String userName, HttpSession session) {
        Blogger blogger = bloggerService.findByName(userName);
        Blogger currentUser = (Blogger) session.getAttribute(Constants.CURRENT_USER);
        if (blogger == null) {
            BloggerAdminController.log.info("用户{}不存在", userName);
            return error("用户不存在");
        }
        if (!currentUser.equals(blogger)) {
            return error("只能修改自己的密码");
        }
        if (!blogger.getPassword().equals(CommonUtils.encryptPassword(oldpwd))) {
            return error("原密码输入不正确");
        }
        blogger.setPassword(CommonUtils.encryptPassword(newpwd));
        bloggerService.update(blogger, null);
        return success();
    }

}
