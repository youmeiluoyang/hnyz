package com.dg11185.hnyz.common.listener;

import com.dg11185.hnyz.common.config.SysConfig;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;

/**
 * 当前系统的根目录，在前台页面可通过${basepath}方式直接读取。
 *
 * @author xdweleven
 * @version 1.0
 * @since 2016-09-19
 * @Copyright 2016 全国邮政电子商务运营中心 All rights reserved.
 */
public class BasepathConfigListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent arg0) {

	}

	/**
	 * 系统启动的一些业务处理
	 */
	public void contextInitialized(ServletContextEvent event) {
		//设置系统根目录
		String basepath = event.getServletContext().getContextPath();
		event.getServletContext().setAttribute("basepath", basepath);
		System.setProperty("webroot", basepath);
		String realPath = event.getServletContext().getRealPath("/");
		if(StringUtils.isNotBlank(realPath) && !realPath.endsWith(File.separator)){
			realPath = realPath + File.separator;
		}
		System.setProperty("basepath", realPath);
		String env = SysConfig.getEnvironment();
		event.getServletContext().setAttribute("env", env);
	}

}
