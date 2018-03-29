package com.dg11185.hnyz.common.listener;


import com.dg11185.hnyz.common.config.SysConfig;
import com.dg11185.hnyz.util.LogUtil;
import com.dg11185.util.concurrent.ThreadPoolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;

/**
 * @author xiesp
 * @description
 * @date 9:14 AM  8/30/2017
 * @copyright 全国邮政电子商务运营中心
 */
public class CommonListener implements ServletContextListener{


    private final static Logger log = LoggerFactory.getLogger(CommonListener.class);
    private ServletContextEvent context=null;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //启动生产者消费者线程
        int count =  Integer.parseInt(SysConfig.getProperty("queued.threadNum"));
        //启动线程组,判断一下是否可以启动这么多线程,不超过占用的一般
        int available = ThreadPoolUtil.getPoolThreadNum();
        if(count > available/2){
            count = available/2;
        }
        //QueueHandler.startQueuedTask(count);
    }


    @Override
    public void contextDestroyed(ServletContextEvent destoryContext) {
        try{

            log.info("[destroyListener]检测到服务器关闭,开始清理线程和资源....");
            log.info("[destroyListener]关闭注册的Jdbc...");
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()) {
                Driver driver = drivers.nextElement();
                DriverManager.deregisterDriver(driver);
            }
            //log.info("停止队列任务....");
            //QueueHandler.stop();
            log.info("[destroyListener]关闭系统线程池...");
            ThreadPoolUtil.shutdownNow();
        }catch (Exception e){
            log.error("[destroyListener]关闭工作发生异常:"+ LogUtil.getTrace(e));
        }
    }
}
