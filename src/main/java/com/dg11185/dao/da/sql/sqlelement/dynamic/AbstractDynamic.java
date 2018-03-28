package com.dg11185.dao.da.sql.sqlelement.dynamic;

import java.util.List;

/**
 * @author xiesp
 * @description
 * @date 6:59 PM  11/30/2017
 * @copyright 全国邮政电子商务运营中心
 */
public abstract class AbstractDynamic implements SqlDynamic {

    protected final String qName;
    protected final String condition;
    protected final String passText;
    protected final boolean isChain;
    protected final List<SqlDynamic> chainList;

    protected AbstractDynamic(String qName,String condition,String passText,boolean isChain,List<SqlDynamic> chainList){
        this.qName = qName;
        this.condition = condition;
        this.passText = passText;
        this.isChain = isChain;
        if(isChain){
            this.chainList = chainList;
        }else{
            this.chainList = null;
        }
    }

/*    protected AbstractDynamic(String qName,AbstractDynamic abstractDynamic){
        this.condition = abstractDynamic.condition;
        this.getPassText = abstractDynamic.getPassText;
        this.isChain = abstractDynamic.isChain;
        this.chainList = abstractDynamic.chainList;
        this.qName = qName;
    }*/


    @Override
    public boolean isDynamic() {
        return true;
    }


    @Override
    public String getText(){
        StringBuilder sb = new StringBuilder();
        return  sb.append("<").append(qName).append(">").append(" Dynamic").toString();
    }



    public String getqName() {
        return qName;
    }

    public String getCondition() {
        return condition;
    }

    public boolean isChain() {
        return isChain;
    }

    public List<SqlDynamic> getChainList() {
        return chainList;
    }
}
