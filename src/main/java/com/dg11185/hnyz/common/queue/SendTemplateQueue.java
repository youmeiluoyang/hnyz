package com.dg11185.hnyz.common.queue;

import com.dg11185.hnyz.service.api.WxApiService;
import com.dg11185.hnyz.util.ServiceFacade;
import com.dg11185.util.queue.queueobj.AbsQueueObj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xiesp
 * @description
 * @date 16:00  2018/5/21
 * @copyright 全国邮政电子商务运营中心
 */
public class SendTemplateQueue extends AbsQueueObj {
    private static Logger log = LoggerFactory.getLogger(SendTemplateQueue.class);
    private final String info;

    public SendTemplateQueue(String info){
        this.info = info;
    }


    @Override
    public boolean process() {
        WxApiService wxApiService = ServiceFacade.getBean(WxApiService.class);
        wxApiService.sendTemplateMessage(info);
        return true;
    }


    /**
     * 顺序发送,避免超过频率
     * @return
     */
    @Override
    public boolean isSequential() {
        return true;
    }
}
