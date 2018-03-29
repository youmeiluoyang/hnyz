package com.dg11185.hnyz.bean.common;

import java.io.Serializable;

/**
 * @author xiesp
 * @description
 * @date 3:12 PM  3/29/2018
 * @copyright 全国邮政电子商务运营中心
 */
public class Menu implements Serializable{

    private int ids;
    private Integer parentId;
    private String url;
    private String names;
    private int sortNum;
    private int level;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String name) {
        this.names = name;
    }

    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    public int getIds() {
        return ids;
    }

    public void setIds(int ids) {
        this.ids = ids;
    }
}
