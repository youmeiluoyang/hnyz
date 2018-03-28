package com.dg11185.dao.da;

import com.dg11185.dao.da.sql.Sql;
import com.dg11185.dao.da.sql.builder.SqlBuilder;
import com.dg11185.util.concurrent.annotation.ThreadSafe;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class is intended to load sql xml file from config path.
 * It is not aware of the change happened in the files,it should be
 * notifyed about the change
 * @author xiesp
 * @description
 * @date 3:34 PM  10/27/2017
 * @copyright 全国邮政电子商务运营中心
 */
@ThreadSafe
public class SqlMap {
    private final static Logger log = LoggerFactory.getLogger(SqlMap.class);
    //sql container
    private static  final Map<String,Sql> map = new ConcurrentHashMap<>();
    //config-path
    private static volatile String configPath;


    /**
     *
     * @param id
     * @return
     */
    public static Sql getSql(String id){
        return map.get(id).cloneSql();
    }


    /**
     * no need for sync
     */
    public  static  void load(String path){
        configPath = path;
        reload();
    }

    /**
     * no need for sync
     */
    public static  void reload(){
        File file = new File(configPath);
        File[] files = file.listFiles();
        SAXReader saxReader = new SAXReader();
        try {
            for(File sqlFile:files){
                Document document = saxReader.read(sqlFile);
                Element element = document.getRootElement();
                List<Element> sqlElementList = element.elements("sql");
                for(Element sqlElement:sqlElementList){
                    Sql sql = SqlBuilder.buildSql(sqlElement);
                    map.put(sql.getId(),sql);
                }
                StringBuilder sb = new StringBuilder("load sql clauses from [").append(sqlFile.getName()).append("]...");
                log.info(sb.toString());

            }
        } catch (Exception e) {
            log.error("Load Sql Files Error!",e);
        }
    }
}
