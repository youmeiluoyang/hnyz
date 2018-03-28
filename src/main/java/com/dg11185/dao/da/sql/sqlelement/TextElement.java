package com.dg11185.dao.da.sql.sqlelement;

/**
 * @author xiesp
 * @description
 * @date 8:38 AM  12/1/2017
 * @copyright 全国邮政电子商务运营中心
 */
public class TextElement implements SqlElement {

    private final String text;

    public TextElement(String text){
        this.text =text ;
    }



    @Override
    public boolean isDynamic() {
        return false;
    }


    @Override
    public String getText() {
        return this.text;
    }

    public String toString(){
        return this.text;
    }
}
