package com.june.web.controller.admin;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.june.model.Blogger;
import com.june.service.BloggerService;
import com.june.util.CommonUtils;
import com.june.util.Constants;
import com.june.util.MD5EncodeUtils;
import com.june.util.ObjectUtils;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/admin")
public @Slf4j class SysLoginOrOutController {

    private static final String INDEX_VIEW = "index";
    private static final String LOGIN_VIEW = "login";
    @Resource
    private BloggerService bloggerService;

    @GetMapping("/{page}")
    public String page(@PathVariable String page, Model model, HttpSession session) {
        if (SysLoginOrOutController.LOGIN_VIEW.equals(page)) {
            // 登录页面，生成csrfToken
            model.addAttribute(Constants.CSRF_TOKEN, createCsrfToken(session));
        }
        return page;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, HttpServletResponse response,
                         @CookieValue(name = Constants.COOKIE_NAME) Cookie cookie) {
        session.removeAttribute(Constants.CURRENT_USER);
        session.invalidate();
        // 删除Cookie
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return SysLoginOrOutController.LOGIN_VIEW;
    }

    // 登录
    @PostMapping("/userLogin")
    public String login(@RequestParam String username, @RequestParam String password,
                        @RequestParam String csrfToken,
                        @RequestParam(defaultValue = "false") Boolean rememberMe,
                        @CookieValue(name = Constants.COOKIE_NAME, required = false) String cookieValue,
                        HttpSession session, Model model, HttpServletRequest request,
                        HttpServletResponse response) {
        String token = (String) session.getAttribute(Constants.CSRF_TOKEN);
        session.removeAttribute(Constants.CSRF_TOKEN);

        SysLoginOrOutController.log.info("csrfToken: {}, token in session: {}", csrfToken, token);

        if (!csrfToken.equals(token)) {
            model.addAttribute("error", "非法请求");
            return SysLoginOrOutController.LOGIN_VIEW;
        }
        Blogger blogger = bloggerService.findByName(username);
        String encryptedPassword = CommonUtils.encryptPassword(password);
        if (ObjectUtils.isNull(blogger) || !blogger.getPassword().equals(encryptedPassword)) {
            model.addAttribute("error", "用户名或密码错误").addAttribute(Constants.CSRF_TOKEN, createCsrfToken(session));
            return SysLoginOrOutController.LOGIN_VIEW;
        }
        session.setAttribute(Constants.CURRENT_USER, blogger);
        // 验证Session共享是否成功
        session.setAttribute("sessionPort", request.getServerPort());

        // TODO 记住用户名密码，7天内自动登录

        if (!StringUtils.isEmpty(cookieValue)) {
            String decodedValue = new String(Base64.getDecoder().decode(cookieValue), StandardCharsets.UTF_8);
            String[] decodedValueArr = StringUtils.split(decodedValue, ":");
            if (ArrayUtils.isNotEmpty(decodedValueArr)) {
                boolean isValid = username.equals(decodedValueArr[0]) && encryptedPassword.equals(decodedValueArr[1]);
                if (isValid) {
                    // 记住密码的用户
                    SysLoginOrOutController.log.info("记住用户名密码");
                    return SysLoginOrOutController.INDEX_VIEW;
                }
            }
        }
        byte[] data = (username + ":" + encryptedPassword + ":" + CommonUtils.getUUID()).getBytes(StandardCharsets.UTF_8);
        // 第一次登录
        Cookie cookie = new Cookie(Constants.COOKIE_NAME, Base64.getEncoder().encodeToString(data));
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        // 勾选了记住密码，则保存7天，否则只保存30分钟
        cookie.setMaxAge(rememberMe ? 7 * 24 * 3600 : 30 * 60);
        response.addCookie(cookie);
        return SysLoginOrOutController.INDEX_VIEW;
    }

    private String createCsrfToken(HttpSession session) {
        String token = MD5EncodeUtils.encrypt(CommonUtils.getUUID(true), "token");
        session.setAttribute(Constants.CSRF_TOKEN, token);
        return token;
    }

}
