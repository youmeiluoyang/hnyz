package com.dg11185.dao.da.sql.builder;

import com.dg11185.dao.da.sql.Sql;
import com.dg11185.dao.da.sql.impl.PlainSql;
import com.dg11185.dao.da.sql.impl.XmlSql;
import com.dg11185.dao.da.sql.impl.reflect.*;
import com.dg11185.dao.da.sql.sqlelement.SqlElement;
import com.dg11185.dao.da.sql.sqlelement.TextElement;
import com.dg11185.dao.da.sql.sqlelement.dynamic.SqlDynamic;
import com.dg11185.dao.exception.SqlInitializeException;
import com.dg11185.util.common.StrUtil;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiesp
 * @description
 * @date 8:16 AM  12/1/2017
 * @copyright 全国邮政电子商务运营中心
 */
public class SqlBuilder {


    /**
     * Get Xml Sql by xml config files
     * @param domEle
     * @return
     */
    public static Sql buildSql(Element domEle){
        String id = domEle.attribute("id").getValue();
        Attribute resultClassAttribute = domEle.attribute("resultClass");
        String resultClass=null;
        if(resultClassAttribute!=null){
            resultClass = resultClassAttribute.getValue();
        }
        try {
            Class<?> tmpClass = null;
            if(!StrUtil.isEmpty(resultClass)){
                tmpClass = Class.forName(resultClass);
            }
            List<Object> contents = domEle.content();
            boolean hasText= true;
            StringBuilder textSb  = new StringBuilder();
            List<SqlElement> sqlElements = new ArrayList<>();
            for(Object con:contents){
                //normal Text
                if(con instanceof Text){
                    hasText = true;
                    Text text = (Text)con;
                    textSb.append(text.getText());
                }
                //dynamic
                else if(con instanceof Element){
                    //build normal Text
                    if(hasText){
                        SqlElement textElement = new TextElement(textSb.toString());
                        sqlElements.add(textElement);
                        hasText = false;
                        textSb.setLength(0);
                    }
                    //build dynamic
                    Element ele = (Element)con;
                    SqlDynamic sqlDynamic = DynamicBuilder.buildDynamic(ele);
                    sqlElements.add(sqlDynamic);
                }
            }
            //append the last sql
            if(hasText){
                SqlElement textElement = new TextElement(textSb.toString());
                sqlElements.add(textElement);
            }
            return new XmlSql(id,tmpClass,sqlElements);
        } catch (Exception e) {
            throw new SqlInitializeException("Error occured during building sql,id==>"  + id,e);
        }
    }

    /**
     * Get Refection Sql.<br>
     * if ReflectType is Insert,then keyColumn must be null
     * @param object
     * @param reflectType
     * @param keyColumn
     * @return
     */
    public static Sql buildSql(Object object, ReflectType reflectType,String keyColumn){
        if(reflectType.equals(ReflectType.INSERT)){
            return new InsertReflectSql(object);
        }
        else if(reflectType.equals(ReflectType.UPDATE)){
            return new UpdateReflectSql(object,keyColumn);
        }
        else if(reflectType.equals(ReflectType.DELETE)){
            return new DeleteReflectSql(object,keyColumn);
        }
        else if(reflectType.equals(ReflectType.QUERY_BY_ID)){
            return new QueryByIdReflectSql(object,keyColumn);
        }
        else if(reflectType.equals(ReflectType.NEXT_SEQ)){
            return new NextSeqReflectSql((Class<?>) object);
        }else if(reflectType.equals(ReflectType.BATCH_INSERT)){
            return new BatchInsertReflectSql((List<Object>)object);
        }else if(reflectType.equals(ReflectType.BATCH_UPDATE)){
            return new BatchUpdateReflectSql((List<Object>)object,keyColumn);
        }
        return  null;
    }


    /**
     * Get The SIMPLE PLAIN Sql
     * @param id
     * @param clazz
     * @param plainSql
     * @return
     */
    public static Sql buildSql(String id,Class<?> clazz,String plainSql){
        return new PlainSql(id,clazz,plainSql);
    }
}
