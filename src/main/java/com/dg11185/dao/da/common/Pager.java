package com.dg11185.dao.da.common;

import com.dg11185.util.concurrent.annotation.ThreadConfined;

import java.io.Serializable;

/**
 * @author xiesp
 * @description
 * @date 4:27 PM  10/27/2017
 * @copyright 全国邮政电子商务运营中心
 */
@ThreadConfined
public class Pager implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;
    private int curPage;
    private int pageSize;
    private int totalPage;
    private int totalCount;

    public Pager() {
    }

    public Pager(int curPage, int pageSize) {
        this.curPage = curPage;
        this.pageSize = pageSize;
    }

    public Pager(int curPage, int pageSize, int totalCount) {
        this.curPage = curPage;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
    }

    public int getCurPage() {
        return this.curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        int ps = Math.max(1, this.pageSize);
        this.totalPage = (this.totalCount % ps == 0 ? this.totalCount / ps : this.totalCount / ps + 1);
        return this.totalPage;
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public Pager clone() {
        Pager p = null;
        try {
            p = (Pager) super.clone();
            p.curPage = this.curPage;
            p.pageSize = this.pageSize;
            p.totalCount = this.totalCount;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return p;
    }

    public boolean equals(Object obj) {
        if ((obj instanceof Pager)) {
            Pager p = (Pager) obj;
            this.curPage = p.curPage;
            this.pageSize = p.pageSize;
            this.totalCount = p.totalCount;
        }
        return false;
    }
}