package com.dg11185.hnyz.service.api;

import com.dg11185.hnyz.common.exception.AesException;

/**
 * @author xiesp
 * @description
 * @date 3:16 PM  3/28/2018
 * @copyright 全国邮政电子商务运营中心
 */
public interface WeixinService {


    /**
     * 解密微信消息
     * @param msgSignature
     * @param timeStamp
     * @param nonce
     * @param postData
     * @return
     * @throws AesException
     */
    String decryptWxMessage(String msgSignature, String timeStamp, String nonce, String postData) throws AesException;
}
