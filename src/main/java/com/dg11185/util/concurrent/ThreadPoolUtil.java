package com.dg11185.util.concurrent;

import com.dg11185.hnyz.common.config.SysConfig;
import com.dg11185.hnyz.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * 线程池工具类
 * Created by zouwei on 2017/2/23.
 */
public class ThreadPoolUtil {

    //alter thread count to most server cpu num
    private final static Logger log = LoggerFactory.getLogger(ThreadPoolUtil.class);
    private static final int MAX_THREAD ;
    private final static ExecutorService executor;
    public static void executeTask(Runnable runnable) {
        executor.execute(runnable);
    }

    //测试环境需要多开一点线程测试
    static {
        if(SysConfig.getEnvironment().equals("test")){
            MAX_THREAD = 4;
        }else{
            MAX_THREAD = Runtime.getRuntime().availableProcessors()+1;
        }
        executor= Executors.newFixedThreadPool(MAX_THREAD, new DaemonThreadFactory());
    }


    /**
     * 自定义的ThreadFactory，将线程设置为守护线程
     * 在关闭tomcat shutdown 的时候可以关闭
     */
    private static class  DaemonThreadFactory implements ThreadFactory {
        @Override
        public Thread newThread(Runnable r) {
            Thread s = Executors.defaultThreadFactory().newThread(r);
            s.setName("dwx-threadpool-thread");
            s.setDaemon(true);
            return s;
        }
    }


    /**
     * 关闭线程池
     */
    public static void shutdownNow(){
        boolean flag = false;
        try{
            executor.shutdown();
            flag = executor.awaitTermination(3, TimeUnit.SECONDS);
        }catch (Exception e){
            log.info("[ThreadPoolUtil]:关闭线程池失败:" + LogUtil.getTrace(e));
        }
        //强制关闭
        if(!flag){
            executor.shutdownNow();
        }
    }


    /**
     * 返回支持的最大线程数目,方便其他类协作
     * @return
     */
    public static int getPoolThreadNum(){
        return MAX_THREAD;
    }
}
