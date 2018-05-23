package com.june.web.listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.june.model.BlogType;
import com.june.service.BlogService;
import com.june.service.BlogTypeService;
import com.june.service.BloggerService;
import com.june.service.LinkService;
import com.june.util.Constants;

/**
 * 自定义Listener，在web容器初始化时就查询出博客、博客类别、博主、以及链接信息
 */
@Component
@WebListener
public class BlogContextListener implements ServletContextListener, ApplicationContextAware {

	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		applicationContext = context;
	}

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent)  { 
    	
		//获取ServletContext对象
		ServletContext application = servletContextEvent.getServletContext();
		
		//从Spring的IOC容器中获取Bean
		BlogService blogService = applicationContext.getBean(BlogService.class);
		BlogTypeService blogTypeService = applicationContext.getBean(BlogTypeService.class);
		BloggerService bloggerService = applicationContext.getBean(BloggerService.class);
		LinkService linkService = applicationContext.getBean(LinkService.class);
		
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

		application.setAttribute("basePath", Constants.BASE_PATH);
		application.setAttribute("pageTitle", Constants.DEFAULT_TITLE);
		application.setAttribute("pageKeywords", Constants.DEFAULT_KEYWORDS);
		application.setAttribute("description", Constants.DEFAULT_DESCRIPTION);
    }

	@Override
    public void contextDestroyed(ServletContextEvent servletContextEvent)  {}

}
