package com.tonvchong.core.util.context;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 
 * Copyright © 2016全球蜂.
 * 
 * @Title: ContextLoaderListener 
 * @Project: RBM
 * @date: 2016年3月1日 下午3:36:31
 * @author: tonvchong
 * @Description: 实现一个简单的Singleton，扩展ContextLoaderListener类，在Web系统启动时压入Singleton。
 * 新的ContextLoaderListener类重载如下，ContextUtil中包含一个静态的ApplicationContext变量
 */
public class ContextLoaderListener extends org.springframework.web.context.ContextLoaderListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        super.contextInitialized(event);
        ServletContext context = event.getServletContext();
        ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
        
        //配合自定义ContextLoaderListener使用的工具类
        ServiceLocator.setContext(ctx);
    }
}
