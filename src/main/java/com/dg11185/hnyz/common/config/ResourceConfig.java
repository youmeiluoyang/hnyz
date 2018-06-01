package com.dg11185.hnyz.common.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 说明:读取除了各种资源文件的值现在有
 * 公众号的配置信息
 * 微信接口的配置信息
 */
public class ResourceConfig {

    private  static final Properties wxApiConfig = new Properties();
    private  static final Properties appConfig = new Properties();
    public static final String appSecrect;
    public static final String appId;
    public static final String mchId;
    public static final String key;

    static {
        InputStream inputStream = ResourceConfig.class.getResourceAsStream(SysConfig.getEnvpath() + "/wxApi.properties");
        try {
            //加载微信接口配置信息
            wxApiConfig.load(inputStream);
            //加载公众号信息
            inputStream = ResourceConfig.class.getResourceAsStream(SysConfig.getEnvpath() + "/secret.properties");
            appConfig.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        appSecrect = appConfig.getProperty("app.secret");
        appId = appConfig.getProperty("app.appId");
        key= appConfig.getProperty("app.key");
        mchId= appConfig.getProperty("app.mchId");
    }

    public static Properties getWxApiConfig(){
        return wxApiConfig;
    }

    public static Properties getAppConfig(){
        return appConfig;
    }
}

