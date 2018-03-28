package com.dg11185.hnyz.common.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 说明:读取除了各种资源文件的值现在有
 * 1 微信第三方平台的配置信息
 * Created by anaesme on 2016/11/25.
 */
public class ResourceConfig {

    private  static final Properties wxApiConfig = new Properties();

    static {
        InputStream inputStream = ResourceConfig.class.getResourceAsStream(SysConfig.getEnvpath() + "/wxApi.properties");
        try {
            wxApiConfig.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Properties getWxApiConfig(){
        return wxApiConfig;
    }
}

