package com.dg11185.hnyz.util;

import org.springframework.web.context.ContextLoader;

/**
 * 系统服务门面类，系统非注入service由此定义被调用
 *
 * @author xdweleven
 * @version 1.0
 * @since 2016-09-18
 * @Copyright 2016 全国邮政电子商务运营中心 All rights reserved.
 */
public class ServiceFacade {

    /**
     * 从spring容器里获bean
     *
     * @author zengxiangtao
     * @param <T>
     * */
    public static <T> T getBean(String beanName, Class<T> requiredType) {
        return ContextLoader.getCurrentWebApplicationContext().getBean(
                beanName, requiredType);
    }

    /**
     * 从spring容器里获bean
     *
     * @author zengxiangtao
     * @param <T>
     * */
    public static <T> T getBean(Class<T> requiredType) {
        return ContextLoader.getCurrentWebApplicationContext().getBean(
                requiredType);
    }
}
