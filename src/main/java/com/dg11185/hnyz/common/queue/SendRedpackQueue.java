package com.dg11185.hnyz.common.queue;

import com.dg11185.hnyz.bean.common.wx.RedPack;
import com.dg11185.hnyz.common.config.ResourceConfig;
import com.dg11185.hnyz.util.wx.WxMessageUtil;
import com.dg11185.util.network.HttpClientUtils;
import com.dg11185.util.queue.queueobj.AbsQueueObj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xiesp
 * @description
 * @date 8:57 AM  5/7/2018
 * @copyright 全国邮政电子商务运营中心
 */
public class SendRedpackQueue extends AbsQueueObj{

    private static Logger log = LoggerFactory.getLogger(SendRedpackQueue.class);

    private final RedPack  redPack;

    public SendRedpackQueue(RedPack redPack){
        this.redPack = redPack;
    }

    @Override
    public boolean process() {
        //微信加密
        String sign = WxMessageUtil.wxApiSing(redPack, ResourceConfig.key);
        redPack.setSign(sign);
        String url = ResourceConfig.getWxApiConfig().getProperty("address.sendRedpack");
        String params = WxMessageUtil.messageToXml(redPack);
        log.info("发送红包参数:" + params);
        String result = HttpClientUtils.postWithCert(url,params);
        log.info("发送红包结果:" + result);
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
