package com.june.web.listener;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;

import com.june.model.BlogType;
import com.june.service.BlogService;
import com.june.service.BlogTypeService;
import com.june.service.BloggerService;
import com.june.service.LinkService;
import com.june.util.Constants;

/**
 * 自定义Listener，在IOC容器初始化后就查询出博客类别、博主、以及链接信息
 */
@WebListener
public class BlogContextLoaderListener extends ContextLoaderListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        // 初始化IOC容器
        super.contextInitialized(servletContextEvent);

        // 获取Spring IOC容器
        ApplicationContext applicationContext = getCurrentWebApplicationContext();

        // 从IOC容器中获取Bean
        BlogService blogService = applicationContext.getBean(BlogService.class);
        BlogTypeService blogTypeService = applicationContext.getBean(BlogTypeService.class);
        BloggerService bloggerService = applicationContext.getBean(BloggerService.class);
        LinkService linkService = applicationContext.getBean(LinkService.class);

        List<BlogType> blogTypeList = blogTypeService.getTypeList();
        blogTypeList.forEach(blogType -> {
            Map<String, Object> paramMap = Collections.singletonMap("typeId", blogType.getTypeId());
            blogType.setBlogCount(blogService.getCount(paramMap));
        });

        // 获取ServletContext对象
        ServletContext application = servletContextEvent.getServletContext();

        application.setAttribute("blogTypeList", blogTypeList);
        application.setAttribute("dateRankList", blogService.getByDate());
        application.setAttribute("readingRankList", blogService.getTopReading());
        application.setAttribute("reviewRankList", blogService.getTopReview());
        application.setAttribute("blogger", bloggerService.find());
        application.setAttribute("linkList", linkService.getLinkList());

        application.setAttribute("basePath", Constants.BASE_PATH);
        application.setAttribute("ctx", application.getContextPath());
        application.setAttribute("pageTitle", Constants.DEFAULT_TITLE);
        application.setAttribute("pageKeywords", Constants.DEFAULT_KEYWORDS);
        application.setAttribute("description", Constants.DEFAULT_DESCRIPTION);
    }

}
