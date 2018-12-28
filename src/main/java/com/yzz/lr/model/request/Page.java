package com.yzz.lr.model.request;

import java.io.Serializable;

/**
 * @author zhizhuang.yang
 * @version 1.0.0
 * @date 2017年9月8日
 * @description 分页
 */
public class Page implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * 当前页码
     */
    private Integer pageIndex;

    /**
     * 当前页面长度
     */
    private Integer pageSize;

    /**
     * 总条数
     */
    private Integer pageTotal;


    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(Integer pageTotal) {
        this.pageTotal = pageTotal;
    }

    public Page() {
    }

    public Page(Integer pageIndex, Integer pageSize, Integer pageTotal) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.pageTotal = pageTotal;
    }
}
