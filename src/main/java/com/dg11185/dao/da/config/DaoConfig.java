package com.dg11185.dao.da.config;

import com.dg11185.util.concurrent.annotation.Immutable;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

/**
 * @author xiesp
 * @description
 * @date 4:50 PM  10/27/2017
 * @copyright 全国邮政电子商务运营中心
 */
public class DaoConfig {

    private final static Logger logger = LoggerFactory.getLogger(DaoConfig.class);
    private  static volatile  String configPath;
    private  static volatile BaseInfoConfig baseInfoConfig;

    //private constructor
    private DaoConfig(){
        
    }

    public static void loadDaoConfig(String path){
        configPath = path;
        try{
            boolean isDelegating = false;
            boolean isDebug = false;
            String delegatingClass = "";
            InputStream inputStream = DaoConfig.class.getResourceAsStream("/data-access.xml");
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(inputStream);
            Element root = document.getRootElement();
            Element delegatingEle = root.element("delegating");
            Element debugModeEle = root.element("debugMode");
            if(delegatingEle!=null){
                isDelegating = Boolean.parseBoolean(delegatingEle.element("isDelegating").getTextTrim());
                Element delegatingClassEle = delegatingEle.element("delegatingClass");
                if(delegatingClass!=null){
                    delegatingClass = delegatingClassEle.getTextTrim();
                }
            }
            if(debugModeEle!=null){
                isDebug = Boolean.parseBoolean(root.element("debugMode").getTextTrim());
            }
            String sqlMapDir = root.element("sql-location").getTextTrim();
            int timeOut = Integer.parseInt(root.element("sql-timeout-warn").getTextTrim());
            baseInfoConfig= new BaseInfoConfig(isDelegating,delegatingClass,sqlMapDir,timeOut,isDebug);
            SqlMapConfig.loadSqlMap();
        }
        catch (Exception e){
            logger.error("[DataAccess]:读取data-access.xml文件发生错误",e);
        }
    }





    public static boolean isDelegating() {
        return baseInfoConfig.isDelegating();
    }


    public static boolean isDebug() {
        if(configPath == null)
            return true;
        return baseInfoConfig.isDebug();
    }

    public static String getDelegatingClass() {
        return baseInfoConfig.getDelegatingClass();
    }

    public static String getSqlMapDir() {
        return baseInfoConfig.getSqlMapDir();
    }

    public static int getWarnTimeOut() {
        return baseInfoConfig.getWarnTimeOut();
    }


}


/**
 * This Inner class in intended to read basic info into immutable Class for concurrency
 */
@Immutable
class BaseInfoConfig{
    private final boolean isDelegating;
    private final String  delegatingClass;
    private final String sqlMapDir;
    private final int warnTimeOut;
    private final boolean isDebug;

    BaseInfoConfig(boolean isDelegating,String delegatingClass,String sqlMapDir,int warnTimeOut,boolean isDebug){
        this.isDelegating = isDelegating;
        this.isDebug = isDebug;
        this.delegatingClass = delegatingClass;
        this.sqlMapDir = sqlMapDir;
        this.warnTimeOut = warnTimeOut;
    }

    public boolean isDelegating() {
        return isDelegating;
    }

    public String getDelegatingClass() {
        return delegatingClass;
    }

    public String getSqlMapDir() {
        return sqlMapDir;
    }

    public int getWarnTimeOut() {
        return warnTimeOut;
    }

    public boolean isDebug() {
        return isDebug;
    }
}



