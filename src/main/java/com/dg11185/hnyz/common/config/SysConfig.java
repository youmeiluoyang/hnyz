package com.dg11185.hnyz.common.config;


import com.dg11185.util.filemonitor.callback.FileChangeCallback;
import com.dg11185.util.filemonitor.vo.FileChangeObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * 说明：读取公共的配置文件.
 *
 * @author xdweleven
 * @version 1.0
 * @since 2016-09-18
 * @Copyright 2016 全国邮政电子商务运营中心 All rights reserved.
 */
public class SysConfig implements FileChangeCallback{

    private static final Properties baseConfig;
    private static final Properties sysConfig;
    private static final SysConfig instance;
    public  static final String ip;
    public static final  String domain;
    //对外无实例
    private SysConfig(){
    }
    static {
        instance = new SysConfig();
        baseConfig = new Properties();
        sysConfig = new Properties();
        InputStream baseInputStream = null;
        InputStream inputStream = null;
        try {
            baseInputStream = SysConfig.class.getResourceAsStream("/baseConfig.properties");
            baseConfig.load(baseInputStream);
            String path = "/env/" + baseConfig.getProperty("env.default");
            instance.load(path);
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            try {
                baseInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ip=sysConfig.getProperty("project.ip");
        domain=sysConfig.getProperty("project.domain");
    }

    //加载
    private void load(String path){
        InputStream inputStream = null;
        try{
            inputStream = SysConfig.class.getResourceAsStream(path + "/sysConfig.properties");
            sysConfig.load(inputStream);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 根据属性读取配置文件
    public static String getProperty(String key){
        try {
            return new String(sysConfig.getProperty(key).getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    //获取当前环境 dev/test/prod
    public static String getEnvironment() {
        return baseConfig.getProperty("env.default");
    }

    //获取当前环境 配置文件地址
    public static String getEnvpath() {
        return "/env/"  + baseConfig.getProperty("env.default");
    }


    @Override
    public void fileChanged(FileChangeObject fco) {

        File file = fco.getFile();
        if(file.getName().equals("sysConfig.properties")){
            String path = "/env/" + baseConfig.getProperty("env.default");
            instance.load(path);
        }
    }
}
