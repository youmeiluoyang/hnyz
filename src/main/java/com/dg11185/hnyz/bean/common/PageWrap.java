package com.dg11185.hnyz.bean.common;

import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * 分页Bean 对数据pageRequest不进行修正，比如：请求10页，每页记录为10，总数为20条，那么查出的数据为空。不会修正到请求页到最后一页
 * 
 * @param <T>
 */
public class PageWrap<T> implements Iterable<T>, Serializable {

    private static final long serialVersionUID = 1L;

    // 总页数
    private int totalPage = 1;
    // 总数
    private int totalNum;

    private PageRequest pageRequest;
    // 查询列表
    private List<T> list;

    public PageWrap(List<T> list, PageRequest pageRequest, int totalNum) {
        this(pageRequest, totalNum);
        this.list = list;
    }

    public PageWrap(PageRequest pageRequest, int totalNum) {
        Assert.notNull(pageRequest, "pageRequest not be null");
        this.pageRequest = pageRequest;
        this.totalNum = totalNum;
        if (totalNum > 0) {
            if (pageRequest.isQueryAll()) {
                pageRequest.setPageNum(1);
                pageRequest.setPageSize(totalNum);
                this.totalPage = 1;
            } else {
                this.totalPage = (int) Math.ceil((double) totalNum / (double) getPageSize());
            }
        }
    }

    public PageWrap(PageWrap<?> page) {
        super();
        this.totalPage = page.totalPage;
        this.totalNum = page.totalNum;
        this.pageRequest = page.pageRequest;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public int getTotalNum() {
        return totalNum;
    }


    public int getPageNum() {
        return pageRequest.getPageNum();
    }

    public int getPageSize() {
        return pageRequest.getPageSize();
    }

    public List<T> getList() {
        if (list == null) {
            list = Collections.emptyList();
        }
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public Iterator<T> iterator() {
        return getList().iterator();
    }

    public boolean isEmpty() {
        return getList().isEmpty();
    }

}
