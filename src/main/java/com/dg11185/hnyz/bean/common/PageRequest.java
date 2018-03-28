package com.dg11185.hnyz.bean.common;

import com.dg11185.hnyz.dao.BaseDAO;

import java.io.Serializable;

public class PageRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    // 分页页码
    private int pageNum = 1;
    // 分页显示数
    private int pageSize = 10;

    /**
     * 是否查询所有，那么pageSize和pageNum 两个参数都是浮云，pageNum肯定会为1，pageSize肯定是totalRows总记录数。 默认值false更改请慎重再慎重。
     * {@link BaseDAO#queryForPage(String, Object[], PageRequest, Class)} 中查询中分页将取第一页，数据返回全部。
     */
    private boolean queryAll = false;

    public PageRequest() {
        super();
    }

    public PageRequest(boolean queryAll) {
        this.queryAll = queryAll;
    }

    public PageRequest(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        if (pageNum < 1) {
            pageNum = 1;
        }
        return pageNum;
    }

    public int getPageSize() {
        if (pageSize <= 0) {
            pageSize = 10;
        }
        return pageSize;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getOffset() {
        return (getPageNum() - 1) * pageSize;
    }

    public boolean isQueryAll() {
        return queryAll;
    }

    public void setQueryAll(boolean queryAll) {
        this.queryAll = queryAll;
    }

}
