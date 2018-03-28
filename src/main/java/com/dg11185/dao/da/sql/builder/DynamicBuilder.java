package com.dg11185.dao.da.sql.builder;

import com.dg11185.dao.da.sql.sqlelement.dynamic.SqlDynamic;
import com.dg11185.dao.da.sql.sqlelement.dynamic.impl.ChooseDynamic;
import com.dg11185.dao.da.sql.sqlelement.dynamic.impl.IfDynamic;
import com.dg11185.dao.da.sql.sqlelement.dynamic.impl.OtherwiseDynamic;
import com.dg11185.dao.da.sql.sqlelement.dynamic.impl.WhenDynamic;
import com.dg11185.dao.exception.SqlInitializeException;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiesp
 * @description
 * @date 8:49 AM  12/1/2017
 * @copyright 全国邮政电子商务运营中心
 */
public class DynamicBuilder {


    public static SqlDynamic buildDynamic(Element element){

        String qName = element.getQualifiedName();
        if(qName.equals("if")){
            String condition = element.attributeValue("test");
            String passText = element.getText();
            return new IfDynamic(condition,passText);
        }else if(qName.equals("choose")){
            List<Element> subEles =element.elements("when");
            if(subEles.size() == 0){
                throw new SqlInitializeException("Sql dynamic " +qName +" required when Dynamic!");
            }
            List<SqlDynamic> subDynamics = new ArrayList<>();
            //first append WhenDynamic
            for(Element whenEle:subEles){
                String condition = whenEle.attributeValue("test");
                String passText = whenEle.getText();
                WhenDynamic whenDynamic = new WhenDynamic(condition,passText);
                subDynamics.add(whenDynamic);
            }
            //then otherwiseDynamic
            subEles = element.elements("otherwise");
            if(subEles.size()>1){
                throw new SqlInitializeException("Sql dynamic " +qName +"'s could not has more than one OtherwiseDynamic !");
            }
            if(subEles.size() > 0){
                Element otherwiseEle= subEles.get(0);
                OtherwiseDynamic otherwiseDynamic = new OtherwiseDynamic(otherwiseEle.getText());
                subDynamics.add(otherwiseDynamic);
            }
            return new ChooseDynamic(subDynamics);
        }
        throw new SqlInitializeException("Sql dynamic " +qName +" not found!");
    }
}
